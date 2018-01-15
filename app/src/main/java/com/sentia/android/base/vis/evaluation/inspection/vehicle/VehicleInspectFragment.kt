package com.sentia.android.base.vis.evaluation.inspection.vehicle

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.EvaluationBaseFragment
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.databinding.FragmentInspectionVehicleBinding
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID
import com.sentia.android.base.vis.util.Resource
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_inspection_vehicle.*
import org.reactivestreams.Publisher

/**
 * Created by mariolopez on 9/1/18.
 */
class VehicleInspectFragment : EvaluationBaseFragment() {

    private lateinit var binding: FragmentInspectionVehicleBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inspection_vehicle, container, false)
//        initUi(savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)
    }

    private fun initUi(savedInstanceState: Bundle?) {

        inspectionViewModel?.findInspection(inspectionId)

        inspectionViewModel?.currentInspection
                ?.observe(this, Observer<Resource<Inspection>?> {
                    binding.inspection = it?.data
                    binding.executePendingBindings()
                })

        val toPublisher: Publisher<Resource<Inspection>>? = LiveDataReactiveStreams.toPublisher(this, inspectionViewModel?.currentInspection)
        val obsFromPublisher: Observable<Inspection> = Observable.fromPublisher(toPublisher).map { it.data!! }.firstOrError().toObservable()

        Observable.combineLatest(
                swi_spare_key.checkedChanges(),
                obsFromPublisher,
                BiFunction { spareKeyAndRemote: Boolean, inspection: Inspection ->
                    Pair(inspection, inspection.copy().apply {
                        this.spareKeyAndRemote = spareKeyAndRemote
                    })

                })
                .subscribe { (old, new) ->
                    inspectionViewModel?.saveTempChanges(old) {
                        it.spareKeyAndRemote = new.spareKeyAndRemote
                    }
                }
    }

    companion object {
        fun newInstance(vehicleId: Long) = VehicleInspectFragment().apply {
            arguments = Bundle().apply {

                putLong(KEY_INSPECTION_ID, vehicleId)
            }
        }
    }
}