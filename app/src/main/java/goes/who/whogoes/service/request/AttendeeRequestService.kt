package goes.who.whogoes.service.request

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import goes.who.whogoes.di.MyApplication
import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.RequestModel
import goes.who.whogoes.model.UserEventStatus
import goes.who.whogoes.service.response.AttendeeResponseService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Diego Mendonca on 21.09.2017.
 */
@Singleton
class AttendeeRequestService  {

    @Inject
    lateinit var httpClient: OkHttpClient

    @Inject
    lateinit var attendeeResponseService: AttendeeResponseService

    // todo: try to inject it
    lateinit var userEventStatus: List<UserEventStatus>

    @Inject
    @field:Named("baseURI") lateinit var baseURI: String

    @Inject
    @field:Named("remainingURI") lateinit var remainingURI: String

    constructor() {
        MyApplication.graph.inject(this)
    }

//
//    override fun performCall(request: RequestModel): List<Deferred<List<Datum>>> {
//        return userEventStatus.map { stat ->
//
//            async(CommonPool) {
//                call(baseURI + request.eventID + "/" + stat.status() + remainingURI + request.token, stat.status(), request.name, emptyList())
//            }
//        }
//    }
//
//    private fun call(url: String?, stat: String, name: String, acc : List<Datum>): List<Datum> {
//        Log.d("CALLING:" ,   "$stat ---> $url")
//        val request = Request.Builder().url(url).build()
//
//        val httpResponse = httpClient.newCall(request).execute()
//
//        // retrying failed request
//        // improve it, otherwise we can end up with an infinite loop
//        if (httpResponse.code() == 500) {
//            Log.d("REQUEST FAILED", "retrying the following request $url")
//            return call(url, stat, name,acc)
//        }
//
//        val formattedResponse = attendeeResponseService.processResponse(httpResponse, stat, name)
//
//        if (formattedResponse?.nextURL.isNullOrEmpty()) {
//            Log.d("FINISHING ITERATION", "iteration for $stat done")
//            return acc
//        }
//
//        return call(formattedResponse.nextURL, stat, name, formattedResponse.datum.plus(acc))
//    }
}