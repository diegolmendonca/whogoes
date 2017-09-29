package goes.who.whogoes.activity

/**
 * Created by Diego Mendonca on 31.08.2017.
 */

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import goes.who.whogoes.R
import goes.who.whogoes.adapter.AttendeesResponseAdapter
import goes.who.whogoes.di.MyApplication
import goes.who.whogoes.model.*
import goes.who.whogoes.service.request.FacebookRequestInterface
import goes.who.whogoes.service.response.AttendeeResponseService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named


/** TODO: Remove service from this activity. However, service needs
* to return results to activity, in order to update progress bar and list adapter.
* Consider using broadcast or something similar
 */

class AttendeeActivity : BasicActivity() {

    @Inject
    lateinit var facebookRequestInterface: FacebookRequestInterface

    private val userEventStatus  = listOf(Attending(), Interested(), Declined())

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


        userEventStatus.map { stat ->
            val url = baseURI + intent.getStringExtra("datumEventId") + "/" + stat.status() + remainingURI + intent.getStringExtra("FACEBOOK_TOKEN")
            loadJSON(url, stat.status())
        }


    }

    private fun loadJSON(url: String, status: String) {
        Log.d("calling " , "  status: $status ----->    $url")
        mCompositeDisposable.add(facebookRequestInterface.getDatum(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resultList -> handleResponse(resultList, status) },
                        {error -> handleError(error,url,status) }))

    }

    private fun handleResponse(androidList: Example, status: String) {
        val userName = intent.getStringExtra("name")

        mAndroidArrayList = attendeeResponseService.processResponse(status, userName, androidList)
        val partialResultString = getString(R.string.partial_result, responseAdapter.getElements().size + mAndroidArrayList.size)
        title.text = partialResultString
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
                val finalResultString = getString(R.string.final_result, responseAdapter.getElements().size)
                title.text = finalResultString
                Toast.makeText(this, getString(R.string.search_finished), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleError(error: Throwable, url: String,status: String) {
        val stack = error.printStackTrace()
        Log.e("error calling " , "  msg: $stack")
        loadJSON(url, status)
    }

    public override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }
}