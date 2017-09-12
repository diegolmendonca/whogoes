package goes.who.whogoes

/**
 * Created by doma on 31.08.2017.
 */


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import goes.who.whogoes.adapter.PostsAdapter
import goes.who.whogoes.service.HttpService
import kotlinx.coroutines.experimental.launch
import java.io.IOException
import javax.inject.Inject

class EventActivity : AppCompatActivity() {
    @Inject
    lateinit var httpService: HttpService

    private lateinit var posts: RecyclerView
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var postsLayoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        MyApplication.graph.inject(this)


        posts = findViewById<RecyclerView>(R.id.posts_list)
        postsAdapter = PostsAdapter()
        postsLayoutManager = LinearLayoutManager(this)

        posts.apply {
            setHasFixedSize(true)
            layoutManager = postsLayoutManager
            adapter = postsAdapter
        }

    }


    override fun onResume() {
        super.onResume()

        var token = intent.getStringExtra("FACEBOOK_TOKEN")
        var eventId = "476707972696392"

        launch(Android) {
            try {
                val result = httpService.performCall(token, eventId).flatMap { statusCall ->
                    statusCall.await()

                }

                postsAdapter.setElements(result)
                postsAdapter.notifyDataSetChanged()

            } catch (exception: IOException) {
                Toast.makeText(this@EventActivity, "Phone not connected or service down", Toast.LENGTH_SHORT).show()
            }


        }


    }
}