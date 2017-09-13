package goes.who.whogoes

/**
 * Created by doma on 31.08.2017.
 */

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import goes.who.whogoes.adapter.ResponseAdapter
import goes.who.whogoes.service.HttpService
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.io.IOException
import javax.inject.Inject


class EventActivity : AppCompatActivity() {
    @Inject
    lateinit var httpService: HttpService

    private lateinit var responseList: RecyclerView
    private lateinit var responseAdapter: ResponseAdapter
    private lateinit var postsLayoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        MyApplication.graph.inject(this)

        responseList = findViewById<RecyclerView>(R.id.response)
        responseAdapter = ResponseAdapter(emptyList())
        postsLayoutManager = LinearLayoutManager(this)

        responseList.apply {
            setHasFixedSize(true)
            layoutManager = postsLayoutManager
            adapter = responseAdapter
        }

    }

    override fun onResume() {
        super.onResume()

        var token = intent.getStringExtra("FACEBOOK_TOKEN")
        var eventId = "476707972696392"

        launch(UI) {
            try {
                val result = httpService.performCall(token, eventId).flatMap { statusCall ->
                    statusCall.await()
                }

                responseAdapter.setElements(result)
                responseAdapter.notifyDataSetChanged()

            } catch (exception: IOException) {
                Toast.makeText(this@EventActivity, "Phone not connected or service down", Toast.LENGTH_SHORT).show()
            }

        }
    }
}