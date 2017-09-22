package goes.who.whogoes.activity

/**
 * Created by Diego Mendonca on 31.08.2017.
 */

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import goes.who.whogoes.R
import goes.who.whogoes.adapter.AttendeesResponseAdapter
import goes.who.whogoes.di.MyApplication
import goes.who.whogoes.model.RequestModel
import goes.who.whogoes.service.request.AttendeeRequestService
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.io.IOException
import javax.inject.Inject


class EventActivity : AppCompatActivity() {
    @Inject
    lateinit var attendeeRequestService: AttendeeRequestService

    private lateinit var responseList: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var responseAdapter: AttendeesResponseAdapter
    private lateinit var postsLayoutManager: RecyclerView.LayoutManager
    private lateinit var title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        MyApplication.graph.inject(this)

        responseList = findViewById<RecyclerView>(R.id.response)
        responseAdapter = AttendeesResponseAdapter(this, emptyList())
        postsLayoutManager = LinearLayoutManager(this)
        mProgressBar = findViewById<ProgressBar>(R.id.progress_bar)
        title = findViewById<TextView>(R.id.title)
        title.text = "RESULTS:"

        responseList.apply {
            setHasFixedSize(true)
            layoutManager = postsLayoutManager
            adapter = responseAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        val userName = intent.getStringExtra("name")

        val request = RequestModel(
                intent.getStringExtra("datumEventId"),
                userName,
                intent.getStringExtra("FACEBOOK_TOKEN")
        )

        launch(UI) {
            try {
                val result = attendeeRequestService.performCall(request).flatMap { statusCall ->
                    statusCall.await()
                }
                responseAdapter.setElements(result)
                responseAdapter.notifyDataSetChanged()
                responseList.setVisibility(View.VISIBLE)
                mProgressBar.setVisibility(View.GONE)

                if (result.size == 0)
                    Toast.makeText(this@EventActivity, "No user with name:$userName found for this event ", Toast.LENGTH_SHORT).show()



            } catch (exception: IOException) {
                Toast.makeText(this@EventActivity, "Service timed out. Please check your internet connection and try again", Toast.LENGTH_SHORT).show()
            }
        }
    }
}