package com.example.collectionsrecyclerview.postviewholder

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.collectionsrecyclerview.Post
import com.example.collectionsrecyclerview.PostAdapter
import kotlinx.android.synthetic.main.post_card.view.*

class EventViewHolder(
    adapter: PostAdapter,
    view: View,
    list: MutableList<Post>): PostViewHolder(adapter, view, list) {

    init {
        this.clickEventListener()
    }


    override fun bind(post: Post) {
        super.bind(post)
        with (view) {
            textViewPost.text = post.textOfPost
            textViewSnippet.visibility = View.VISIBLE
            textViewSnippet.text = post.address
            imageButtonLocation.visibility = View.VISIBLE
        }
    }

    private fun clickEventListener() {
        with (view) {
            imageButtonLocation.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = list[adapterPosition]
                    val lat = item.coordinates?.first
                    val lng = item.coordinates?.second
                    val dataOfCoordinates = Uri.parse("geo:$lat,$lng")
                    transitionToApp(context, dataOfCoordinates)
                }
            }
        }
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