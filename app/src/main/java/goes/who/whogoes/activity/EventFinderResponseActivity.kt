package goes.who.whogoes.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import goes.who.whogoes.MyApplication
import goes.who.whogoes.R
import goes.who.whogoes.adapter.EventResponseAdapter
import goes.who.whogoes.service.HttpService
import goes.who.whogoes.service.RequestModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.io.IOException
import javax.inject.Inject

class EventFinderResponseActivity : AppCompatActivity() {
    @Inject
    lateinit var httpService: HttpService

    private lateinit var responseList: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var eventResponseAdapter: EventResponseAdapter
    private lateinit var postsLayoutManager: RecyclerView.LayoutManager
    private lateinit var facebookToken : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        MyApplication.graph.inject(this)

        facebookToken = intent.getStringExtra("FACEBOOK_TOKEN")

        responseList = findViewById<RecyclerView>(R.id.response)
        mProgressBar  = findViewById<ProgressBar>(R.id.progress_bar)
        postsLayoutManager = LinearLayoutManager(this)

        eventResponseAdapter = EventResponseAdapter(this, emptyList(), facebookToken)

        responseList.apply {
            setHasFixedSize(true)
            layoutManager = postsLayoutManager
            adapter = eventResponseAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        val request = RequestModel(
                intent.getStringExtra("eventName"),
                "",
                facebookToken
        )

        launch(UI) {
            try {
                val result = httpService.performCall2(request).await()
                eventResponseAdapter.setElements(result)
                eventResponseAdapter.notifyDataSetChanged()
                responseList.setVisibility(View.VISIBLE)
                mProgressBar.setVisibility(View.GONE)

            } catch (exception: IOException) {
                Toast.makeText(this@EventFinderResponseActivity, "Service timed out. Please check your internet connection and try again", Toast.LENGTH_SHORT).show()
            }

        }
    }
}