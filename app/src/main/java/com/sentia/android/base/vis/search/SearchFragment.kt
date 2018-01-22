package com.sentia.android.base.vis.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.textChanges
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseFragment
import com.sentia.android.base.vis.databinding.FragmentSearchBinding
import com.sentia.android.base.vis.evaluation.EvaluationActivity
import com.sentia.android.base.vis.search.SearchFragment.SearchResultView
import com.sentia.android.base.vis.util.*
import com.sentia.android.base.vis.util.Resource.Status.*
import kotlinx.android.synthetic.main.fragment_search.*


/**
 * Created by mariolopez on 27/12/17.
 */
class SearchFragment : BaseFragment() {
    private var searchViewModel: SearchViewModel? = null
    private lateinit var inspectionsAdapter: SearchInspectionsAdapter

    override fun initViewModel() {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        searchViewModel?.let { lifecycle.addObserver(it) }
//        searchViewModel?.initLocalInspections()
    }

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)
    }

    private fun initUi(savedInstanceState: Bundle?) {
        //Search
        et_search.textChanges().filter { it.isNotBlank() }
                .subscribe { searchViewModel?.search("%" + it.toString() + "%") /* by putting % the search works like contains*/ }
        //initial values for search
        et_search.textChanges().filter { it.isEmpty() }.subscribe {
            searchViewModel?.loadInspections()?.observe(this, Observer {

                //Views state
                binding.searchResult = it?.toSearchResultView()
                binding.executePendingBindings()

                when (it?.status) {
                    SUCCESS -> inspectionsAdapter.setInspections(it.data)
                    ERROR -> snackBarX(it.exception?.message)
                    LOADING -> {/*do nothing */
                    }
                }
            })
        }
        //Restore search key
        binding.searchKeyWord = savedInstanceState?.getString(KEY_SEARCH_KEY_WORD).orEmpty()
        //set inspections from search result
        searchViewModel?.searchResult?.observe(this, Observer {
            inspectionsAdapter.setInspections(it?.data)
        })

        //RecyclerViewSetup
        rv_inspections.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        inspectionsAdapter = SearchInspectionsAdapter()
        rv_inspections.adapter = inspectionsAdapter

        //clicks
        inspectionsAdapter.itemClicks.subscribe {
            startActivity(intentFor<EvaluationActivity>(KEY_INSPECTION_ID to it))
            hideKeyboard()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val bundle: Bundle = outState
        bundle.putString(KEY_SEARCH_KEY_WORD, et_search.text.toString())
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val KEY_SEARCH_KEY_WORD: String = "Key Search key word"
    }

    data class SearchResultView(val isLoading: Boolean = false, val isEmpty: Boolean = false,
                                val isLoaded: Boolean = !isLoading && !isEmpty)

}

private fun <T> Resource<List<T>>.toSearchResultView() =
        when (this.status) {
            Resource.Status.SUCCESS -> SearchResultView(false, this.data?.isEmpty().orFalse())
            Resource.Status.ERROR -> SearchResultView(false, false)
            Resource.Status.LOADING -> SearchResultView(true, false)
        }