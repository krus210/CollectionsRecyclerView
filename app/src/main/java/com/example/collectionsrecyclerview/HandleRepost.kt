package com.example.collectionsrecyclerview

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.post_card.view.*

object HandleRepost: HandleSimplePost {
    @SuppressLint("SetTextI18n")
    override fun fillPostCard(view: View, post: Post) {
        super.fillPostCard(view, post)
        with (view) {
            textViewPost.text = post.source?.textOfPost ?: ""
            textViewSnippet.visibility = View.VISIBLE
            textViewSnippet.text = context.getString(R.string.repost_from) +
                    post.source?.nameAuthor
        }
    }

    override fun clickButtonListener(
        view: View,
        adapter: PostAdapter,
        list: MutableList<Post>,
        adapterPosition: Int
    ) {
        super.clickButtonListener(view, adapter, list, adapterPosition)
    }
}