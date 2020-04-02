package com.example.collectionsrecyclerview.postviewholder

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.collectionsrecyclerview.Post
import com.example.collectionsrecyclerview.PostAdapter
import com.example.collectionsrecyclerview.R
import kotlinx.android.synthetic.main.post_card.view.*

class AdViewHolder(
    adapter: PostAdapter,
    view: View,
    list: MutableList<Post>): PostViewHolder(adapter, view, list)  {

    init {
        this.clickAdListener()
    }

    override fun bind(post: Post) {
        super.bind(post)
        with (view) {
            textViewPost.text = post.textOfPost
            textViewSnippet.visibility = View.VISIBLE
            textViewSnippet.text = context.getString(R.string.ad_post)
            imageButtonLink.visibility = View.VISIBLE
            imageButtonLink.setImageResource(R.drawable.photo_ad)
        }
    }

    private fun clickAdListener() {
        with (view) {
            imageButtonLink.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = list[adapterPosition]
                    val data = Uri.parse(item.sourceAd)
                    transitionToApp(context, data)
                }
            }
        }
    }
}