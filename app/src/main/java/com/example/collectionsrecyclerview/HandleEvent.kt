package com.example.collectionsrecyclerview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import kotlinx.android.synthetic.main.post_card.view.*

object HandleEvent: HandleSimplePost {
    override fun fillPostCard(view: View, post: Post) {
        super.fillPostCard(view, post)
        with (view) {
            textViewPost.text = post.textOfPost
            textViewSnippet.visibility = View.VISIBLE
            textViewSnippet.text = post.address
            imageButtonLocation.visibility = View.VISIBLE
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

fun transitionToApp(context: Context, dataTransition: Uri) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = dataTransition
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}