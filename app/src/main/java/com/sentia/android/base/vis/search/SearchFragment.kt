package com.sentia.android.base.vis.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.textChanges
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseFragment
import com.sentia.android.base.vis.databinding.FragmentSearchBinding
import com.sentia.android.base.vis.evaluation.EvaluationActivity
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID
import com.sentia.android.base.vis.util.Resource.Status.*
import com.sentia.android.base.vis.util.intentFor
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.error

/**
 * Created by mariolopez on 27/12/17.
 */
class SearchFragment : BaseFragment() {
    private var searchViewModel: SearchViewModel? = null

    override fun initViewModel() {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        searchViewModel?.let { lifecycle.addObserver(it) }
        searchViewModel?.initLocalInspections()
    }

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)

        //todo remove
        bt_go_to_evaluation.setOnClickListener { startActivity(intentFor<EvaluationActivity>(KEY_INSPECTION_ID to 123)) }
    }


    private fun initUi(savedInstanceState: Bundle?) {
        et_search.textChanges().subscribe { searchViewModel?.search(it.toString()) }

        //todo init recycler view
        searchViewModel?.loadInspections()?.observe(this, Observer {
            val size = it?.data?.size
            when (it?.status) {
                SUCCESS -> TODO()
                ERROR -> TODO()
                LOADING -> error { it.exception }
            }
            //todo-init recycler view
        })
        searchViewModel?.searchResult?.observe(this, Observer {
            //todo
        })
//        searchViewModel?.search()
        //restore simple Ui
        val keywords = savedInstanceState?.getString(KEY_SEARCH_KEY_WORD).orEmpty()
        binding.searchKeyWord = keywords
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        val bundle: Bundle = outState ?: Bundle()
        bundle.putString(KEY_SEARCH_KEY_WORD, et_search.text.toString())
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val KEY_SEARCH_KEY_WORD: String = "Key Search key word"
    }

}