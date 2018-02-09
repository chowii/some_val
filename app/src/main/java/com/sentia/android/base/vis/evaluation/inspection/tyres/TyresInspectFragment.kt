package com.sentia.android.base.vis.evaluation.inspection.tyres

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.EvaluationBaseFragment
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.data.room.entity.Lookups
import com.sentia.android.base.vis.databinding.FragmentInspectionTyresBinding
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID
import com.sentia.android.base.vis.util.Resource
import io.reactivex.Observable
import io.reactivex.functions.Function6
import kotlinx.android.synthetic.main.fragment_inspection_tyres.*

/**
 * Created by mariolopez on 9/1/18.
 */
class TyresInspectFragment : EvaluationBaseFragment() {

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
        inspectionViewModel.loadLookUps().observe(this@TyresInspectFragment, Observer<Resource<Lookups>?> {
            binding.tyre = it?.data
        })

        Observable.combineLatest(
                RxAdapterView.itemSelections(spinner_lsf),
                RxAdapterView.itemSelections(spinner_lsr),
                RxAdapterView.itemSelections(spinner_rsf),
                RxAdapterView.itemSelections(spinner_rsr),
                RxAdapterView.itemSelections(spinner_spare),
                inspectionDbObs, //Todo resolve emission error
                Function6 { lsf: Int,
                            lsr: Int,
                            rsf: Int,
                            rsr: Int,
                            spare: Int,
                            inspection: Inspection //Todo resolve emission error
                    ->
                    //Todo return correct type
                    rsf
                })
                .subscribe { new ->
                    //Todo execute correct operation
                    Log.d("LOG_TAG---", "initUiTest(): $new")
                }
    }

    companion object {
        fun newInstance(vehicleId: Long) = TyresInspectFragment().apply {
            arguments = Bundle().apply {
                putLong(KEY_INSPECTION_ID, vehicleId)
            }
        }
    }
}