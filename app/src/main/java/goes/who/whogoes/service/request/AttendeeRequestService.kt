package goes.who.whogoes.service.request

import android.util.Log
import goes.who.whogoes.di.MyApplication
import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.RequestModel
import goes.who.whogoes.model.UserEventStatus
import goes.who.whogoes.service.response.AttendeeResponseService
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Diego Mendonca on 21.09.2017.
 */
@Singleton
class AttendeeRequestService : HttpService<List<Deferred<List<Datum>>>> {

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

    override fun performCall(request : RequestModel): List<Deferred<List<Datum>>> {
        return userEventStatus.map { stat ->

            async(CommonPool) {
                call(baseURI + request.eventID + "/" + stat.status() + remainingURI + request.token, stat.status(), request.name)
            }
        }
    }

    private fun call(url: String?, stat: String, name:String): List<Datum> {
            println("CALLING:" + stat)
            val request = Request.Builder().url(url).build()

            val httpResponse = httpClient.newCall(request).execute()

            // retrying failed request
            if(httpResponse.code() == 500){
                call(url,stat,name)
                Log.i("repetindo", "repetindo isso $url" )
            }

            val formattedResponse = attendeeResponseService.processResponse(httpResponse, stat, name)

            if (formattedResponse?.nextURL.isNullOrEmpty()) {
                println("ACABANDO: " + stat)
                return formattedResponse.datum
            }
            return formattedResponse.datum.plus(call(formattedResponse.nextURL, stat, name))
    }
}