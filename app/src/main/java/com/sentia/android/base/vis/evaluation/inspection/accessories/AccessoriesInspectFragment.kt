package com.sentia.android.base.vis.evaluation.inspection.accessories

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.EvaluationBaseFragment
import com.sentia.android.base.vis.data.room.entity.Accessory
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID
import com.sentia.android.base.vis.util.Resource
import com.sentia.android.base.vis.views.GridDividerItemDecoration
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_inspection_accessories.*

/**
 * Created by mariolopez on 9/1/18.
 */
class AccessoriesInspectFragment : EvaluationBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inspection_accessories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)
    }

    private fun initUi(savedInstanceState: Bundle?) {
        val accessoriesAdapter = AccessoriesAdapter()
        with(rv_accessories) {
            setHasFixedSize(true)
            adapter = accessoriesAdapter
            layoutManager = GridLayoutManager(this.context, NUMBER_OF_COLUMNS)
            addItemDecoration(GridDividerItemDecoration(
                    ContextCompat.getDrawable(context, R.drawable.item_decoration_grid_seperator),
                    ContextCompat.getDrawable(context, R.drawable.item_decoration_grid_seperator),
                    AccessoriesInspectFragment.NUMBER_OF_COLUMNS
            ))
        }
        Observable.combineLatest(
                accessoriesAdapter.itemClicks,
                inspectionDbObs,
                BiFunction { accessoriesList: MutableList<Accessory>,
                             inspection: Inspection ->
                    Pair(inspection, inspection.copyWithList().apply {
                        this.accessories = accessoriesList
                    })
                })
                .subscribe { (old, new) ->
                    inspectionViewModel?.saveTempChanges(old) {
                        it.synced = false
                        it.accessories.clear()
                        it.accessories.addAll(new.accessories)
                    }
                    accessoriesAdapter.updateAccessories(new.accessories)
                }
        inspectionViewModel?.findInspection(inspectionId)
        inspectionViewModel?.currentInspection
                ?.observe(this, Observer<Resource<Inspection>?> {
                    it?.data?.accessories?.let { accessories ->
                        accessoriesAdapter.updateAccessories(accessories)
                    }
                })
    }

    companion object {

        private const val NUMBER_OF_COLUMNS = 2

        fun newInstance(vehicleId: Long) = AccessoriesInspectFragment().apply {
            arguments = Bundle().apply {
                putLong(KEY_INSPECTION_ID, vehicleId)
            }
        }
    }
}

