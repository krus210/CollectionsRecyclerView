package com.example.collectionsrecyclerview

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_card.view.*

class PostAdapter(var list: MutableList<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.post_card, parent, false)
        return PostViewHolder(this, view, list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as PostViewHolder) {
            bind(list[position])
        }
    }
}

class PostViewHolder(
    private val adapter: PostAdapter, private val view: View, var list: MutableList<Post>
) : RecyclerView.ViewHolder(view) {
    init {
//        val handlePost = HandlePost
//        handlePost.clickButtonListener(view, adapter, list, adapterPosition)
        with(view) {
            imageButtonLike.setOnClickListener {
                it as ImageButton
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = list[adapterPosition]
                    item.isLikedByUser = !item.isLikedByUser
                    isCheckedByUser(
                        it,
                        textViewLikeCount,
                        item.isLikedByUser,
                        R.drawable.ic_like_red,
                        R.drawable.ic_like_gray,
                        context
                    )
                    if (item.isLikedByUser) {
                        item.likesCount++
                    } else {
                        item.likesCount--
                    }
                    fillCount(textViewLikeCount, item.likesCount)
                    adapter.notifyItemChanged(adapterPosition)
                }
            }
            imageButtonShare.setOnClickListener {
                it as ImageButton
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = list[adapterPosition]
                    item.isSharedByUser = true
                    isCheckedByUser(
                        it,
                        textViewShareCount,
                        item.isSharedByUser,
                        R.drawable.ic_share_red,
                        R.drawable.ic_share_gray,
                        context
                    )
                    item.sharesCount++
                    fillCount(textViewShareCount, item.sharesCount)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT, """
                            ${item.nameAuthor} (${item.dateOfPost})
                            
                            ${item.textOfPost}
                        """.trimIndent()
                        )
                        type = "text/plain"
                    }
                    context.startActivity(intent)
                }
            }
            imageButtonComment.setOnClickListener {
                it as ImageButton
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = list[adapterPosition]
                    item.isCommentedByUser = true
                    isCheckedByUser(
                        it,
                        textViewCommentCount,
                        item.isCommentedByUser,
                        R.drawable.ic_comment_red,
                        R.drawable.ic_comment_gray,
                        context
                    )
                    item.commentsCount++
                    fillCount(textViewCommentCount, item.commentsCount)
                    Toast.makeText(
                        context,
                        context.getString(R.string.put_comment),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            imageButtonDelete.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    list.removeAt(adapterPosition)
                    adapter.notifyDataSetChanged()
                }
            }
            imageButtonLocation.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = list[adapterPosition]
                    val lat = item.coordinates?.first
                    val lng = item.coordinates?.second
                    val dataOfCoordinates = Uri.parse("geo:$lat,$lng")
                    transitionToApp(context, dataOfCoordinates)
                }
            }
            imageButtonLink.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = list[adapterPosition]
                    if (item.postType == PostType.YOUTUBE) {
                        val data = Uri.parse(
                            "vnd.youtube:" +
                                    item.sourceVideo
                        )
                        transitionToApp(context, data)
                    }
                    if (item.postType == PostType.AD_POST) {
                        val dataOfAd = Uri.parse(item.sourceAd)
                        transitionToApp(context, dataOfAd)
                    }
                }
            }
        }
    }

    fun bind(post: Post) {
        val handlePost = getHandlePostFromType(post)
        handlePost.fillPostCard(view, post)
    }

    private fun getHandlePostFromType(item: Post) = when (item.postType) {
        PostType.EVENT -> HandleEvent
        PostType.REPOST -> HandleRepost
        PostType.YOUTUBE -> HandleYoutube
        PostType.AD_POST -> HandleAd
        else -> HandlePost
    }

}

