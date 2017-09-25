package goes.who.whogoes.activity

/**
 * Created by Diego Mendonca on 31.08.2017.
 */

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import goes.who.whogoes.R
import goes.who.whogoes.adapter.AttendeesResponseAdapter
import goes.who.whogoes.di.MyApplication
import goes.who.whogoes.model.*
import goes.who.whogoes.service.request.AttendeeRequestService
import goes.who.whogoes.service.request.FacebookRequestInterface
import goes.who.whogoes.service.response.AttendeeResponseService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named

class EventActivity : AppCompatActivity() {
    @Inject
    lateinit var attendeeRequestService: AttendeeRequestService

    private lateinit var responseList: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var responseAdapter: AttendeesResponseAdapter
    private lateinit var postsLayoutManager: RecyclerView.LayoutManager
    private lateinit var title: TextView
    private lateinit var mCompositeDisposable: CompositeDisposable
    private lateinit var mAndroidArrayList: List<Datum>
    private var latch = 3

    @Inject
    lateinit var attendeeResponseService: AttendeeResponseService


    @Inject
    @field:Named("baseURI") lateinit var baseURI: String

    @Inject
    @field:Named("remainingURI") lateinit var remainingURI: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        MyApplication.graph.inject(this)

        responseList = findViewById<RecyclerView>(R.id.response)
        responseAdapter = AttendeesResponseAdapter(this, emptyList())
        postsLayoutManager = LinearLayoutManager(this)
        mProgressBar = findViewById<ProgressBar>(R.id.progress_bar)


        mCompositeDisposable = CompositeDisposable()

        title = findViewById<TextView>(R.id.title)

        responseList.apply {
            setHasFixedSize(true)
            layoutManager = postsLayoutManager
            adapter = responseAdapter
        }

        title.text = "RESULTS: "

        val userEventStatus = listOf(Attending(), Interested(), Declined())

        userEventStatus.map { stat ->
            val url = baseURI + intent.getStringExtra("datumEventId") + "/" + stat.status() + remainingURI + intent.getStringExtra("FACEBOOK_TOKEN")
            loadJSON(url, stat.status())
        }


    }

    private fun loadJSON(url: String, status: String) {
        val requestInterface = Retrofit.Builder()
                .baseUrl(baseURI)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(FacebookRequestInterface::class.java)

        mCompositeDisposable.add(requestInterface.getDatum(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resultList -> handleResponse(resultList, status) },
                        {error -> handleError(error,url,status) }))

    }

    private fun handleResponse(androidList: Example, status: String) {


        val userName = intent.getStringExtra("name")

        val filtered = androidList.data.filter { x -> x.name.contains(userName) }
        val categorizedStatus = filtered.map { x -> x.copy(status = status) }

        mAndroidArrayList = categorizedStatus
        title.text = "PARTIAL RESULT: " + (responseAdapter.getElements().size + mAndroidArrayList.size) + " people found...."
        title.setTextColor(Color.RED)
        responseAdapter.setElements(responseAdapter.getElements().plus(mAndroidArrayList))
        responseAdapter.notifyDataSetChanged()

        val nextUri = androidList.paging?.next

        if (!nextUri.isNullOrEmpty()) {
            loadJSON(nextUri.orEmpty(), status)
        } else {
            latch = latch - 1
            if (latch == 0) {
                responseList.setVisibility(View.VISIBLE)
                mProgressBar.setVisibility(View.GONE)
                title.text = "FINAL RESULT: " + (responseAdapter.getElements().size + mAndroidArrayList.size) + " people found"
                Toast.makeText(this, "SEARCH FINISHED ", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun handleError(error: Throwable, url: String,status: String) {
        loadJSON(url, status)
        Toast.makeText(this, "Error " + error.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        val userName = intent.getStringExtra("name")

        val request = RequestModel(
                intent.getStringExtra("datumEventId"),
                userName,
                intent.getStringExtra("FACEBOOK_TOKEN")
        )


//        launch(UI) {
//            try {
//                val result = attendeeRequestService.performCall(request).flatMap { statusCall ->
//                    statusCall.await()
//                }
//                responseAdapter.setElements(result)
//                responseAdapter.notifyDataSetChanged()
//                responseList.setVisibility(View.VISIBLE)
//                mProgressBar.setVisibility(View.GONE)
//
//                if (result.size == 0)
//                    Toast.makeText(this@EventActivity, "No user with name:$userName found for this event ", Toast.LENGTH_SHORT).show()
//
//
//
//            } catch (exception: IOException) {
//                Toast.makeText(this@EventActivity, "Service timed out. Please check your internet connection and try again", Toast.LENGTH_SHORT).show()
//            }
//        }
    }


    public override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }
}