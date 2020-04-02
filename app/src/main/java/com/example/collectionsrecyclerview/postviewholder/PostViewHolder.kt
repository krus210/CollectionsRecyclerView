package com.example.collectionsrecyclerview.postviewholder

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.collectionsrecyclerview.*
import kotlinx.android.synthetic.main.post_card.view.*

open class PostViewHolder(
    val adapter: PostAdapter, val view: View, var list: MutableList<Post>
) : RecyclerView.ViewHolder(view)  {

    init {
        this.clickButtonListener()
    }

    open fun bind(post: Post) {
        with(view) {
            textViewDate.text = post.dateOfPost
            textViewAuthor.text = post.nameAuthor
            if (post.photoAuthor != null) {
                imageViewAvatar.setImageResource(post.photoAuthor)
            }
            fillCount(textViewShareCount, post.sharesCount)
            fillCount(textViewCommentCount, post.commentsCount)
            fillCount(textViewLikeCount, post.likesCount)

            isCheckedByUser(
                imageButtonShare, textViewShareCount,
                post.isSharedByUser, R.drawable.ic_share_red, context
            )
            isCheckedByUser(
                imageButtonComment, textViewCommentCount,
                post.isCommentedByUser, R.drawable.ic_comment_red, context
            )
            isCheckedByUser(
                imageButtonLike, textViewLikeCount,
                post.isLikedByUser, R.drawable.ic_like_red, context
            )
        }
    }

    private fun clickButtonListener() {
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
        }
    }

}

fun fillCount(view: TextView, count: Int) {
    if (count == 0) {
        view.visibility = View.INVISIBLE
    } else {
        view.visibility = View.VISIBLE
        view.text = count.toString()
    }
}

fun isCheckedByUser(
    imageView: ImageButton,
    textView: TextView,
    isChecked: Boolean,
    imageRed: Int,
    context: Context
) {
    if (isChecked) {
        imageView.setImageResource(imageRed)
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
    }
}

fun isCheckedByUser(
    imageView: ImageButton,
    textView: TextView,
    isChecked: Boolean,
    imageRed: Int,
    imageGray: Int,
    context: Context
) {
    if (isChecked) {
        imageView.setImageResource(imageRed)
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
    } else {
        imageView.setImageResource(imageGray)
        textView.setTextColor(ContextCompat.getColor(context, android.R.color.tab_indicator_text))
    }
}
