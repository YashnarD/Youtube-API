package com.example.youtubeapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapi.databinding.ItemYoutubeBinding
import com.example.youtubeapi.models.Item
import com.squareup.picasso.Picasso


class YoutubeAdapter(var listener: OnItemClickListener) :
    ListAdapter<Item, YoutubeAdapter.Vh>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id.videoId == newItem.id.videoId
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    inner class Vh(var itemYoutubeBinding: ItemYoutubeBinding) :
        RecyclerView.ViewHolder(itemYoutubeBinding.root) {
        fun onBind(item: Item) {
            itemYoutubeBinding.apply {
                Picasso.get().load(item.snippet.thumbnails.default.url).into(img)
                videoTitle.text = item.snippet.title
            }
            itemView.setOnClickListener {
                listener.onItemClick(item.id.videoId)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(videoId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemYoutubeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}