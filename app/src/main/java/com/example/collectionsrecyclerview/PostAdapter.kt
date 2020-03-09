package com.example.collectionsrecyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_card.*
import kotlinx.android.synthetic.main.post_card.view.*
import kotlinx.android.synthetic.main.post_card.view.imageButtonComment
import kotlinx.android.synthetic.main.post_card.view.imageButtonLike
import kotlinx.android.synthetic.main.post_card.view.imageButtonLink
import kotlinx.android.synthetic.main.post_card.view.imageButtonLocation
import kotlinx.android.synthetic.main.post_card.view.imageButtonShare
import kotlinx.android.synthetic.main.post_card.view.imageViewAvatar
import kotlinx.android.synthetic.main.post_card.view.textViewAuthor
import kotlinx.android.synthetic.main.post_card.view.textViewCommentCount
import kotlinx.android.synthetic.main.post_card.view.textViewDate
import kotlinx.android.synthetic.main.post_card.view.textViewLikeCount
import kotlinx.android.synthetic.main.post_card.view.textViewPost
import kotlinx.android.synthetic.main.post_card.view.textViewShareCount

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
    private val adapter: PostAdapter, private val view: View, var list: MutableList<Post>) :
    RecyclerView.ViewHolder(view) {
    init {
        with(view) {
            imageButtonLike.setOnClickListener {
                it as ImageButton
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[adapterPosition]
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
                    val item = adapter.list[adapterPosition]
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
            imageButtonLocation.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[adapterPosition]
                    val lat = item.coordinates?.first
                    val lng = item.coordinates?.second
                    val dataOfCoordinates = Uri.parse("geo:$lat,$lng")
                    transitionToApp(context, dataOfCoordinates)
                }
            }
            imageButtonLink.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[adapterPosition]
                    if (item.postType == PostType.YOUTUBE) {
                        val dataOfYoutube = Uri.parse(
                            "vnd.youtube:" +
                                    item.sourceVideo
                        )
                        transitionToApp(context, dataOfYoutube)
                    }
                    if (item.postType == PostType.AD_POST) {
                        val dataOfAd = Uri.parse(item.sourceAd)
                        transitionToApp(context, dataOfAd)
                    }
                }
            }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View,
                    position: Int, id: Long
                ) {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        val notInterested = 1
                        if (position == notInterested) {
                            list.removeAt(adapterPosition)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    return
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(post: Post) {
        with(view) {
            when (post.postType) {
                PostType.REPOST -> {
                    textViewPost.text = post.source?.textOfPost ?: ""
                    textViewSnippet.visibility = View.VISIBLE
                    textViewSnippet.text = context.getString(R.string.repost_from) +
                            post.source?.nameAuthor
                }
                PostType.EVENT -> {
                    textViewPost.text = post.textOfPost
                    textViewSnippet.visibility = View.VISIBLE
                    textViewSnippet.text = post.address
                    imageButtonLocation.visibility = View.VISIBLE
                }
                PostType.YOUTUBE -> {
                    textViewPost.text = post.textOfPost
                    imageButtonLink.visibility = View.VISIBLE
                    imageButtonLink.setImageResource(R.drawable.photo_youtube)
                }
                PostType.AD_POST -> {
                    textViewPost.text = post.textOfPost
                    textViewSnippet.visibility = View.VISIBLE
                    textViewSnippet.text = context.getString(R.string.ad_post)
                    imageButtonLink.visibility = View.VISIBLE
                    imageButtonLink.setImageResource(R.drawable.photo_ad)
                }
                else -> {
                    textViewPost.text = post.textOfPost
                }
            }
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

            ArrayAdapter.createFromResource(
                context,
                R.array.spinner_array,
                R.layout.spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }
    }

    private fun fillCount(view: TextView, count: Int) {
        if (count == 0) {
            view.visibility = View.INVISIBLE
        } else {
            view.visibility = View.VISIBLE
            view.text = count.toString()
        }
    }

    private fun isCheckedByUser(
        imageView: ImageButton,
        textView: TextView,
        isChecked: Boolean,
        imageRed: Int,
        context: Context
    ) {
        if (isChecked) {
            imageView.setImageResource(imageRed)
            textView.setTextColor(getColor(context, R.color.colorRed))
        }
    }

    private fun isCheckedByUser(
        imageView: ImageButton,
        textView: TextView,
        isChecked: Boolean,
        imageRed: Int,
        imageGray: Int,
        context: Context
    ) {
        if (isChecked) {
            imageView.setImageResource(imageRed)
            textView.setTextColor(getColor(context, R.color.colorRed))
        } else {
            imageView.setImageResource(imageGray)
            textView.setTextColor(getColor(context, android.R.color.tab_indicator_text))
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


}

