package com.example.musicwiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.models.artisttoptracks.Track
import kotlinx.android.synthetic.main.square_item.view.*

class TopTracksAdapter : RecyclerView.Adapter<TopTracksAdapter.TracksViewHolder>() {

    inner class TracksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        return TracksViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.square_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val trackDetails = differ.currentList[position]

        holder.itemView.apply {
            tv_main_square.text = trackDetails.name
            tv_sub_square.text = trackDetails.artist.name
            val imageUrl = trackDetails.image[2].text
            Glide.with(context)
                .load(imageUrl)
                .error(R.drawable.empty)
                .thumbnail(
                    Glide.with(context)
                        .load(R.drawable.empty)
                )
                .into(iv_square)
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size
}