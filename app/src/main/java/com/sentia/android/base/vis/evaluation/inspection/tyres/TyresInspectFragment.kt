package com.sentia.android.base.vis.evaluation.inspection.tyres

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.EvaluationBaseFragment
import com.sentia.android.base.vis.databinding.FragmentInspectionTyresBinding
import com.sentia.android.base.vis.evaluation.inspection.EvaluationViewModel
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID

/**
 * Created by mariolopez on 9/1/18.
 */
class TyresInspectFragment : EvaluationBaseFragment() {
    private var tyresViewModel: EvaluationViewModel? = null

    override fun initViewModel() {
        tyresViewModel = ViewModelProviders.of(this).get(EvaluationViewModel::class.java)
        tyresViewModel?.let { lifecycle.addObserver(it) }
    }

    private lateinit var binding: FragmentInspectionTyresBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inspection_tyres, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)
    }

    private fun initUi(savedInstanceState: Bundle?) {
        tyresViewModel?.repository?.getLookups()?.observe(this, Observer {
            binding.tyre = it?.data
            binding.view = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //todo add action
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    //todo add action
                }
            }
        })
    }

    companion object {
        fun newInstance(vehicleId: Long) = TyresInspectFragment().apply {
            arguments = Bundle().apply {
                putLong(KEY_INSPECTION_ID, vehicleId)
            }
        }
    }
}