package com.example.collectionsrecyclerview

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_card.view.*

object HandleAd: HandleSimplePost {
    override fun fillPostCard(view: View, post: Post) {
        super.fillPostCard(view, post)
        with (view) {
            textViewPost.text = post.textOfPost
            textViewSnippet.visibility = View.VISIBLE
            textViewSnippet.text = context.getString(R.string.ad_post)
            imageButtonLink.visibility = View.VISIBLE
            imageButtonLink.setImageResource(R.drawable.photo_ad)
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