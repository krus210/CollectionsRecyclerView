package com.example.collectionsrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.util.KtorExperimentalAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post_card.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(),CoroutineScope by MainScope() {

    private val url =
        "https://raw.githubusercontent.com/krus210/GsonSerialization/master/posts.json"

    @KtorExperimentalAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = HttpClient {
            install(JsonFeature) {
                acceptContentTypes = listOf(
                    ContentType.Text.Plain,
                    ContentType.Application.Json
                )
                serializer = GsonSerializer()
            }
        }

        launch {
            val list = withContext(Dispatchers.IO) {
                client.get<MutableList<Post>>(url)
            }
            client.close()
            with(container) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = PostAdapter(list)
            }
            determinateBar.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
