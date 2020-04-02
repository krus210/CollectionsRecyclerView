package com.example.collectionsrecyclerview.postviewholder

import android.annotation.SuppressLint
import android.view.View
import com.example.collectionsrecyclerview.Post
import com.example.collectionsrecyclerview.PostAdapter
import com.example.collectionsrecyclerview.R
import kotlinx.android.synthetic.main.post_card.view.*

class RepostViewHolder(
    adapter: PostAdapter,
    view: View,
    list: MutableList<Post>): PostViewHolder(adapter, view, list)  {

    @SuppressLint("SetTextI18n")
    override fun bind(post: Post) {
        super.bind(post)
        with (view) {
            textViewPost.text = post.source?.textOfPost ?: ""
            textViewSnippet.visibility = View.VISIBLE
            textViewSnippet.text = context.getString(R.string.repost_from) +
                    post.source?.nameAuthor
        }
    }
}