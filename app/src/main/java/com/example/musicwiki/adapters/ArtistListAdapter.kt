package com.example.musicwiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.models.topartists.Artist
import kotlinx.android.synthetic.main.list_item.view.*

class ArtistListAdapter : RecyclerView.Adapter<ArtistListAdapter.ArtistViewHolder>() {

    inner class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        return ArtistViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artistDetails = differ.currentList[position]

        holder.itemView.apply {
            tv_list_item_title.text = artistDetails.name

            val imageUrl = artistDetails.image[1].text
            Glide.with(context)
                .load(imageUrl)
                .error(R.drawable.empty)
                .thumbnail(
                    Glide.with(context)
                        .load(R.drawable.empty)
                )
                .into(iv_list_item)

            setOnClickListener {
                onItemClickListener?.let { it(artistDetails.name) }
            }
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}