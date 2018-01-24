package com.sentia.android.base.vis.evaluation.inspection.accessories

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.sentia.android.base.vis.data.room.entity.Accessory
import com.sentia.android.base.vis.databinding.ItemSwitchBinding
import com.sentia.android.base.vis.views.SwitchViewHolder
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_switch.view.*

class AccessoriesAdapter : RecyclerView.Adapter<AccessoriesAdapter.AccessoryViewHolder>() {

    private var accessories = mutableListOf<Accessory>()

    private var itemClicksPublisher: PublishSubject<MutableList<Accessory>> = PublishSubject.create()
    var itemClicks: Observable<MutableList<Accessory>> = itemClicksPublisher.toFlowable(BackpressureStrategy.LATEST).toObservable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessoryViewHolder {
        val binding = ItemSwitchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccessoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccessoryViewHolder, position: Int) {
        val accessory = accessories[position]
        holder.bind(accessory)
        holder.itemView.sw_item.clicks().map {
            accessory.isChecked = holder.itemView.sw_item.isChecked
            accessories
        }.subscribe {
            itemClicksPublisher.onNext(it)
        }
    }

    override fun getItemCount() = accessories.size

    fun updateAccessories(newAccessories: MutableList<Accessory>) {
        // This is important to create a clone and avoid reference, as .clear() would clear the newAccessories as well
        val newAccessoriesClone = newAccessories.toMutableList()
        this.accessories.clear()
        this.accessories.addAll(newAccessoriesClone)
        notifyDataSetChanged()
    }

    inner class AccessoryViewHolder(binding: ItemSwitchBinding) : SwitchViewHolder(binding) {
        fun bind(accessory: Accessory) {
            binding.switchTitle = accessory.name
            binding.switchValue = accessory.isChecked
            binding.executePendingBindings()
        }
    }
}