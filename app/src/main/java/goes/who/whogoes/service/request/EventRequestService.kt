package goes.who.whogoes.service.request

import android.util.Log
import goes.who.whogoes.di.MyApplication
import goes.who.whogoes.model.DatumEvent
import goes.who.whogoes.model.EventRequest
import goes.who.whogoes.service.response.EventResponseService
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Diego Mendonca on 21.09.2017.
 */

@Singleton
class EventRequestService : HttpService<Deferred<List<DatumEvent>>> {

    @Inject
    lateinit var httpClient: OkHttpClient

    @Inject
    lateinit var eventResponseService: EventResponseService

    @Inject
    @field:Named("baseURI") lateinit var baseURI: String

    @Inject
    @field:Named("remainingEventURI") lateinit var remainingEventURI: String

    @Inject
    @field:Named("query") lateinit var query: String


    constructor() {
        MyApplication.graph.inject(this)
    }

   override fun performCall(eventRequest: EventRequest): Deferred<List<DatumEvent>> {

       Log.d("EVENT" , baseURI + query +
               eventRequest.eventID +
               remainingEventURI +
               eventRequest.token)

        return  async(CommonPool) {
            call(baseURI + query +
                    eventRequest.eventID +
                    remainingEventURI +
                    eventRequest.token)
        }

    }

    private fun call(url: String?): List<DatumEvent> {
        val request = Request.Builder().url(url).build()
        val httpResponse = httpClient.newCall(request).execute()
        val formattedResponse = eventResponseService.processResponse(httpResponse)
        return formattedResponse.datum
    }


}