package com.example.collectionsrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post_card.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var list = createList()

        with(container) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostAdapter(list)
        }

    }

    private fun createList() = mutableListOf(
            Post(
                1,
                getString(R.string.post_text1),
                getString(R.string.post_date1),
                getString(R.string.name_author),
                R.drawable.photo_author,
                commentsCount = 5,
                sharesCount = 1,
                likesCount = 3,
                isLikedByUser = true
            ),
            Post(
                2,
                getString(R.string.post_text2),
                getString(R.string.post_date2),
                getString(R.string.name_author),
                R.drawable.photo_author,
                commentsCount = 1,
                address = "Россия, Москва, улица Покровка, 29с1, подъезд 2",
                coordinates = Pair("55.760029", "37.648548"),
                postType = PostType.EVENT
            ),
            Post(
                3,
                getString(R.string.post_text3),
                getString(R.string.post_date3),
                getString(R.string.name_author),
                R.drawable.photo_author,
                sharesCount = 3,
                likesCount = 11,
                isLikedByUser = true,
                sourceVideo = "Qjf6kBmLilo",
                postType = PostType.YOUTUBE
            ),
            Post(
                4,
                dateOfPost = getString(R.string.post_date4),
                nameAuthor = getString(R.string.name_author),
                photoAuthor = R.drawable.photo_author,
                source = Post(
                    10,
                    getString(R.string.post_text10),
                    getString(R.string.post_date10),
                    getString(R.string.name_author2)
                ),
                postType = PostType.REPOST
            ),
            Post(
                5,
                getString(R.string.post_text5),
                getString(R.string.post_date5),
                getString(R.string.name_author),
                R.drawable.photo_author,
                likesCount = 10,
                sourceAd = "https://weather.com/weather/today/l/55.79,37.36?par=google&temp=c",
                postType = PostType.AD_POST
            )
        )
}
