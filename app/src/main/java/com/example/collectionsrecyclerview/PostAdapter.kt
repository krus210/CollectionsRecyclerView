package com.example.collectionsrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collectionsrecyclerview.postviewholder.*

class PostAdapter(var list: MutableList<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val typeItemPost = 0
    private val typeItemEvent = 1
    private val typeItemRepost = 2
    private val typeItemYoutube = 3
    private val typeItemAd = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.post_card, parent, false)
        return when (viewType) {
            typeItemEvent -> EventViewHolder(this, view, list)
            typeItemRepost -> RepostViewHolder(this, view, list)
            typeItemYoutube -> YoutubeViewHolder(this, view, list)
            typeItemAd -> AdViewHolder(this, view, list)
            else -> PostViewHolder(this, view, list)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val post = list[position]
        return when (post.postType) {
            PostType.EVENT -> typeItemEvent
            PostType.REPOST -> typeItemRepost
            PostType.YOUTUBE -> typeItemYoutube
            PostType.AD_POST -> typeItemAd
            else -> typeItemPost
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = list[holder.adapterPosition]
        when (post.postType) {
            PostType.EVENT -> {
                with (holder as EventViewHolder) {
                    bind(post)
                }
            }
            PostType.REPOST -> {
                with(holder as RepostViewHolder) {
                    bind(post)
                }
            }
            PostType.YOUTUBE -> {
                with(holder as YoutubeViewHolder) {
                    bind(post)
                }
            }
            PostType.AD_POST -> {
                with(holder as AdViewHolder) {
                    bind(post)
                }
            }
            else -> {
                with(holder as PostViewHolder) {
                    bind(post)
                }
            }
        }
    }
}

