package goes.who.whogoes.service

import com.google.gson.Gson
import goes.who.whogoes.MyApplication
import goes.who.whogoes.model.Datum
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
    lateinit var gson: Gson

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

    fun performCall(token: String, eventId: String): List<Deferred<List<Datum>>> {
        return userEventStatus.map { stat ->
            async(CommonPool) {
                call(baseURI + eventId + "/" + stat.status() + remainingURI + token, stat.status())
            }
        }
    }

    private fun call(url: String?, stat: String): List<Datum> {
        val request = Request.Builder().url(url).build()
        val httpResponse = httpClient.newCall(request).execute()
        val formattedResponse = responseService.transform(httpResponse, stat)

        if (formattedResponse.nextURL.isNullOrEmpty()) return formattedResponse.datum
        return formattedResponse.datum.plus(call(formattedResponse.nextURL, stat))
    }

}