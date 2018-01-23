package com.sentia.android.base.vis.evaluation.photos

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.sentia.android.base.vis.data.room.entity.Image
import com.sentia.android.base.vis.databinding.ItemListPhotosBinding
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotoHolder>() {
    private var images = mutableListOf<Image>()
    private val itemViewClickSubject: PublishSubject<Image> = PublishSubject.create()
    val itemViewClickObservable: Observable<Image> = itemViewClickSubject.toFlowable(BackpressureStrategy.LATEST).toObservable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val binding = ItemListPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val image = images[position]
        holder.bind(image)
        holder.itemView.clicks()
                .map { image }
                .subscribe(itemViewClickSubject)
    }

    override fun getItemCount() = images.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        itemViewClickSubject.onComplete()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    fun setData(newPhotos: List<Image>) {
        images.clear()
        images.addAll(newPhotos)
        notifyDataSetChanged()
    }

    inner class PhotoHolder(val binding: ItemListPhotosBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.image = image
            binding.executePendingBindings()
        }
    }
}