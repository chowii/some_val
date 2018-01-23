package com.sentia.android.base.vis.evaluation.inspection.vehicle

import android.app.Activity
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.jakewharton.rxbinding2.widget.editorActions
import com.jakewharton.rxbinding2.widget.textChanges
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.EvaluationBaseFragment
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.databinding.FragmentInspectionVehicleBinding
import com.sentia.android.base.vis.util.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import io.reactivex.Observable
import io.reactivex.functions.Function8
import kotlinx.android.synthetic.main.fragment_inspection_vehicle.*
import java.util.*

/**
 * Created by mariolopez on 9/1/18.
 */
class VehicleInspectFragment : EvaluationBaseFragment() {

    private lateinit var binding: FragmentInspectionVehicleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inspection_vehicle, container, false)
//        initUi(savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

        v_overlay_til_reg_exp_date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog.newInstance(
                    { _, year, monthOfYear, dayOfMonth ->
                        val endDate = DateUtils.toTimeInMillis(year, monthOfYear, dayOfMonth)
                        et_reg_exp_date.setText(endDate.toStringWithDisplayFormat())
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            )
            val activity = context as Activity
            datePicker.show(activity.fragmentManager, TAG_DATE_PICKER_DIALOG)
        }

        Observable.combineLatest(
                et_registration.textChanges().toObsString(),
                et_reg_state.textChanges().toObsString(),
                et_reg_exp_date.textChanges().toObsString(),
                et_vin.textChanges().toObsString(),
                et_odometer.textChanges().toObsString(),
                swi_spare_key.checkedChanges(),
                swi_master_key.checkedChanges(),
                inspectionDbObs,
                Function8 { registration: String,
                            registrationState: String,
                            registrationExpDate: String,
                            vin: String,
                            odometer: String,
                            spareKeyAndRemote: Boolean,
                            masterKeyAndRemote: Boolean,
                            inspection: Inspection ->
                    Pair(inspection, inspection.copyWithList().apply {
                        this.vehicle.rego = registration
                        this.vehicle.registeredState = registrationState
                        this.vehicle.registrationExpireDate = registrationExpDate
                        this.vehicle.vin = vin
                        this.odometer = odometer
                        this.masterKeyAndRemote = masterKeyAndRemote
                        this.spareKeyAndRemote = spareKeyAndRemote
                    })
                })
                .subscribe { (old, new) ->
                    inspectionViewModel?.saveTempChanges(old) {
                        it.synced = false
                        it.spareKeyAndRemote = new.spareKeyAndRemote
                    }
                }


        et_odometer.editorActions().subscribe {
            when (it) {EditorInfo.IME_ACTION_NEXT -> hideKeyboard()
            }
        }
        et_registration.requestFocus()
    }

    companion object {
        fun newInstance(vehicleId: Long) = VehicleInspectFragment().apply {
            arguments = Bundle().apply {

                putLong(KEY_INSPECTION_ID, vehicleId)
            }
        }
    }
}

private fun Observable<CharSequence>.toObsString(): Observable<String> = this.map { it.toString() }
