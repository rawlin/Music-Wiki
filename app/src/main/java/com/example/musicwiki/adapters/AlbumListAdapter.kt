package com.example.musicwiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.models.topalbums.Album
import kotlinx.android.synthetic.main.list_item.view.*

class AlbumListAdapter : RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val albumDetails = differ.currentList[position]

        holder.itemView.apply {
            tv_list_item_title.text = albumDetails.name
            tv_list_item_subtitle.text = albumDetails.artist.name
            val imageUrl = albumDetails.image[1].text
            Glide.with(context)
                .load(imageUrl)
                .error(R.drawable.empty)
                .into(iv_list_item)

            setOnClickListener {
                onItemClickListener?.let { it(albumDetails) }
            }
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size

    private var onItemClickListener: ((Album) -> Unit)? = null

    fun setOnItemClickListener(listener: (Album) -> Unit) {
        onItemClickListener = listener
    }
}