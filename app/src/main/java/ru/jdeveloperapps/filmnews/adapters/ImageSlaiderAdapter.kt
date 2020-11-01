package ru.jdeveloperapps.filmnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.jdeveloperapps.filmnews.R
import ru.jdeveloperapps.filmnews.databinding.ItemContainerSliderImageBinding

class ImageSliderAdapter(val listURL: List<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {

    class ImageSliderViewHolder(val binding: ItemContainerSliderImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindSliderImage(imageURL: String) {
            binding.imageURL = imageURL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemContainerSliderImageBinding = DataBindingUtil.inflate(
            inflater, R.layout.item_container_slider_image, parent, false,
        )
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.bindSliderImage(listURL[position])
    }

    override fun getItemCount(): Int =
        listURL.size

}