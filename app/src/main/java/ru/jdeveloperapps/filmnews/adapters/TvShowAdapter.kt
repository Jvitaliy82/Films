package ru.jdeveloperapps.filmnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.jdeveloperapps.filmnews.R
import ru.jdeveloperapps.filmnews.databinding.ItemContainerTvShowsBinding
import ru.jdeveloperapps.filmnews.models.all.TvShowX

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<TvShowX>() {
        override fun areItemsTheSame(oldItem: TvShowX, newItem: TvShowX): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: TvShowX, newItem: TvShowX): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemContainerTvShowsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_container_tv_shows, parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            root.setOnClickListener {
                onItemClickListener?.let {
                    it(currentItem.id)
                }
            }
            tvShow = currentItem
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class TvShowViewHolder(val binding: ItemContainerTvShowsBinding) :
        RecyclerView.ViewHolder(binding.root)

}