package com.sentia.android.base.vis.evaluation.inspection.vehicle

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.BaseFragment
import com.sentia.android.base.vis.data.room.entity.Vehicle
import com.sentia.android.base.vis.databinding.FragmentInspectionVehicleBinding
import com.sentia.android.base.vis.evaluation.inspection.EvaluationViewModel
import com.sentia.android.base.vis.util.KEY_VEHICLE_ID
import com.sentia.android.base.vis.util.Resource
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_inspection_vehicle.*
import org.jetbrains.anko.info
import org.reactivestreams.Publisher

/**
 * Created by mariolopez on 9/1/18.
 */
class VehicleInspectFragment : BaseFragment() {
    private var inspectionViewModel: EvaluationViewModel? = null
    private val vehicleId: Long by lazy { arguments?.getLong(KEY_VEHICLE_ID, 0) ?: 0 }

    override fun initViewModel() {
        inspectionViewModel = ViewModelProviders.of(this.activity).get(EvaluationViewModel::class.java)
        inspectionViewModel?.let { lifecycle.addObserver(it) }
    }

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

        inspectionViewModel?.findVehicle(vehicleId)

        inspectionViewModel?.vehicleUnderEvaluation
                ?.observe(this, Observer<Resource<Vehicle>?> {
                    info { it?.data?.id }
                    binding.vehicle = it?.data
                    binding.executePendingBindings()
                })


        val toPublisher: Publisher<Resource<Vehicle>> = LiveDataReactiveStreams.toPublisher(this, inspectionViewModel?.vehicleUnderEvaluation)
        val obsFromPublisher: Observable<Vehicle> = Observable.fromPublisher(toPublisher).map { it.data!! }.firstOrError().toObservable()

        Observable.combineLatest(
                swi_spare_key.checkedChanges(),
                obsFromPublisher,
                BiFunction { spareKeyAndRemote: Boolean, vehicle: Vehicle ->
                    Pair(vehicle, vehicle.copy().apply {
                        this.spareKeyAndRemote = spareKeyAndRemote
                    })

                })
                .subscribe { (old, new) ->
                    info { old.spareKeyAndRemote.toString() + "cazzo" + new.spareKeyAndRemote }
                    inspectionViewModel?.saveTempChanges(old) {
                        it.spareKeyAndRemote = new.spareKeyAndRemote
                    }
                }
    }

    companion object {
        fun newInstance(vehicleId: Long) = VehicleInspectFragment().apply {
            arguments = Bundle().apply {

                putLong(KEY_VEHICLE_ID, vehicleId)
            }
        }
    }
}