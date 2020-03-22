package com.example.collectionsrecyclerview

import android.view.View
import kotlinx.android.synthetic.main.post_card.view.*

object HandlePost: HandleSimplePost {
    override fun fillPostCard(view: View, post: Post) {
        super.fillPostCard(view, post)
        with (view) {
            textViewPost.text = post.textOfPost
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