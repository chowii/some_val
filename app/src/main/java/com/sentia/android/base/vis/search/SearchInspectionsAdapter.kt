package com.sentia.android.base.vis.search

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.databinding.ItemListSearchInspectionsBinding
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by mariolopez on 19/1/18.
 */
class SearchInspectionsAdapter(private val inspections: MutableList<Inspection> = mutableListOf()) : RecyclerView.Adapter<SearchInspectionsAdapter.InspectionHolder>() {

    private var itemClicksPublisher: PublishSubject<Long> = PublishSubject.create()
    var itemClicks: Observable<Long> = itemClicksPublisher.toFlowable(BackpressureStrategy.LATEST).toObservable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InspectionHolder {
        val binding = ItemListSearchInspectionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InspectionHolder(binding)
    }

    override fun onBindViewHolder(holder: InspectionHolder, position: Int) {
        val inspection = inspections[position]
        val inspectionItemView = inspection.toInspectionItemView(holder.itemView.context.resources,
                position == inspections.size)
        holder.itemView.clicks().map { inspection.id }.subscribe { itemClicksPublisher.onNext(it) }
        holder.bind(inspectionItemView)
    }

    override fun getItemCount() = inspections.size

    inner class InspectionHolder(val binding: ItemListSearchInspectionsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(inspection: InspectionItemView) {
            binding.inspection = inspection
        }
    }

    data class InspectionItemView(val rego: String, val modelDerivative: String, val vin: String,
                                  val uploaded: String, val isUploading: Boolean,
                                  val isDividerVisible: Boolean = true)

    fun setInspections(newInspections: List<Inspection>?) {
        newInspections?.let {
            inspections.clear()
            inspections.addAll(it)
            notifyDataSetChanged()
        }
    }
}

private fun Inspection.toInspectionItemView(resources: Resources, isLastElement: Boolean): SearchInspectionsAdapter.InspectionItemView =
        SearchInspectionsAdapter.InspectionItemView(this.vehicle.rego,
                "${this.vehicle.model} ${this.vehicle.derivative.orEmpty()}",
                String.format(resources.getString(R.string.inspection_item_vin_label), this.vehicle.vin), "", false,
                !isLastElement)