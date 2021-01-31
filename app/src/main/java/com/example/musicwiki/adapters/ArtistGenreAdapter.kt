package com.example.musicwiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicwiki.R
import com.example.musicwiki.models.artistdetails.Tag
import kotlinx.android.synthetic.main.genre_item.view.*

class ArtistGenreAdapter : RecyclerView.Adapter<ArtistGenreAdapter.GenreViewHolder>() {

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.genre_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genreDetails = differ.currentList[position]

        holder.itemView.apply {
            tv_item_genre_name.text = genreDetails.name

            setOnClickListener {
                onItemClickListener?.let { it(genreDetails.name) }
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