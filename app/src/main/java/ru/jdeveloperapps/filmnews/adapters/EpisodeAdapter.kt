package ru.jdeveloperapps.filmnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.jdeveloperapps.filmnews.R
import ru.jdeveloperapps.filmnews.databinding.ItemContainerTvShowsBinding
import ru.jdeveloperapps.filmnews.databinding.ItemConteanerEpisodesBinding
import ru.jdeveloperapps.filmnews.models.all.TvShowX
import ru.jdeveloperapps.filmnews.models.detail.Episode

class EpisodeAdapter(val episodeList: List<Episode>) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemConteanerEpisodesBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_conteaner_episodes, parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val currentItem = episodeList[position]
        holder.binding.apply {
            var title = "S"
            var season = currentItem.season
            if (season.length == 1) {
                season = "0" + season
            }
            var episodeNumber = currentItem.episode
            if (episodeNumber.length == 1) {
                episodeNumber = "0" + episodeNumber
            }
            episodeNumber = "E" + episodeNumber
            title = title + season + episodeNumber
            episodeTitle = title
            episode = currentItem
        }
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

    class EpisodeViewHolder(val binding: ItemConteanerEpisodesBinding) :
        RecyclerView.ViewHolder(binding.root)

}