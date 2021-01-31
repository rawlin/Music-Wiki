package com.example.musicwiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.models.artisttopalbums.Album
import kotlinx.android.synthetic.main.square_item.view.*

class TopAlbumsAdapter : RecyclerView.Adapter<TopAlbumsAdapter.AlbumsViewHolder>() {

    inner class AlbumsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        return AlbumsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.square_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        val albumDetails = differ.currentList[position]

        holder.itemView.apply {
            tv_main_square.text = albumDetails.name
            tv_sub_square.text = albumDetails.artist.name
            val imageUrl = albumDetails.image[2].text
            Glide.with(context)
                .load(imageUrl)
                .error(R.drawable.empty)
                .thumbnail(
                    Glide.with(context)
                        .load(R.drawable.empty)
                )
                .into(iv_square)

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