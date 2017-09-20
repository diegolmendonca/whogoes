package goes.who.whogoes.service

import goes.who.whogoes.MyApplication
import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.DatumEvent
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by doma on 08.09.2017.
 */

@Singleton
class HttpService {

    @Inject
    lateinit var httpClient: OkHttpClient

    @Inject
    lateinit var responseService: ResponseService

    // todo: try to inject it
    lateinit var userEventStatus: List<UserEventStatus>

    @Inject
    @field:Named("baseURI") lateinit var baseURI: String

    @Inject
    @field:Named("remainingURI") lateinit var remainingURI: String

    constructor() {
        MyApplication.graph.inject(this)
    }

    fun performCall(request : RequestModel): List<Deferred<List<Datum>>> {
        return userEventStatus.map { stat ->
            async(CommonPool) {
                call(baseURI + request.eventID + "/" + stat.status() + remainingURI + request.token, stat.status(), request.name)
            }
        }
    }

    fun performCall2(request : RequestModel): Deferred<List<DatumEvent>> {

      return  async(CommonPool) {
        call2(baseURI + "search?q=" +
                request.eventID +
                "&type=event&limit=500&fields=name%2Ccover%2Cattending_count%2Cinterested_count%2Cdeclined_count%2Cstart_time&access_token=" +
                request.token)
        }

    }

    private fun call2(url: String?): List<DatumEvent> {
        val request = Request.Builder().url(url).build()
        val httpResponse = httpClient.newCall(request).execute()
        val formattedResponse = responseService.transform2(httpResponse)
        return formattedResponse.datum
    }

    private fun call(url: String?, stat: String, name:String): List<Datum> {
        println("CALLING:" + stat)
        val request = Request.Builder().url(url).build()
        val httpResponse = httpClient.newCall(request).execute()
        val formattedResponse = responseService.transform(httpResponse, stat, name)

        if (formattedResponse.nextURL.isNullOrEmpty()){
            println("ACABANDO: " + stat)
            return formattedResponse.datum
        }
        return formattedResponse.datum.plus(call(formattedResponse.nextURL, stat, name))
    }

}