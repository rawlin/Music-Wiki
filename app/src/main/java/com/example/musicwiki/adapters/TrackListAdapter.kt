package com.example.musicwiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.models.toptracks.Track
import kotlinx.android.synthetic.main.list_item.view.*

class TrackListAdapter : RecyclerView.Adapter<TrackListAdapter.TrackViewHolder>() {

    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val trackDetails = differ.currentList[position]

        holder.itemView.apply {
            tv_list_item_title.text = trackDetails.name
            tv_list_item_subtitle.text = trackDetails.artist.name
            val imageUrl = trackDetails.image[1].text
            Glide.with(context)
                .load(imageUrl)
                .error(R.drawable.empty)
                .thumbnail(
                    Glide.with(context)
                        .load(R.drawable.empty)
                )
                .into(iv_list_item)
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size
}