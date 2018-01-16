package com.sentia.android.base.vis.evaluation.photos

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.data.room.entity.Image
import com.sentia.android.base.vis.databinding.ItemListPhotosBinding

class PhotosAdapter(private val context: Context) : RecyclerView.Adapter<PhotosAdapter.PhotoHolder>() {
    private var images = mutableListOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val binding = ItemListPhotosBinding.inflate(LayoutInflater.from(context), parent, false)
        return PhotoHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val image = images[position]
        holder.bind(image)
    }

    override fun getItemCount() = images.size

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