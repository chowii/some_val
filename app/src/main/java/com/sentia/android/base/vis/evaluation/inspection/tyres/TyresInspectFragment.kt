package com.sentia.android.base.vis.evaluation.inspection.tyres

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseFragment
import com.sentia.android.base.vis.databinding.FragmentInspectionTyresBinding
import com.sentia.android.base.vis.search.SearchViewModel

/**
 * Created by mariolopez on 9/1/18.
 */
class TyresInspectFragment : BaseFragment(){
    private var tyresViewModel: SearchViewModel? = null

    override fun initViewModel() {
        tyresViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        tyresViewModel?.let { lifecycle.addObserver(it) }
    }

    private lateinit var binding: FragmentInspectionTyresBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inspection_tyres, container, false)
//        initUi(savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}