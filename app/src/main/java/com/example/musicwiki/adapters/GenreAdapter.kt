package com.example.musicwiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicwiki.R
import com.example.musicwiki.models.topgenres.Tag
import kotlinx.android.synthetic.main.genre_item.view.*

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

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
        val genreNames = differ.currentList[position]

        holder.itemView.apply {
            tv_item_genre_name.text = genreNames.name
            setOnClickListener {
                onItemClickListener?.let { it(genreNames) }
            }
        }


    }

    override fun getItemCount(): Int =
        differ.currentList.size

    private var onItemClickListener: ((Tag) -> Unit)? = null


    fun setOnItemClickListener(listener: (Tag) -> Unit) {
        onItemClickListener = listener
    }
}