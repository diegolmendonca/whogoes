package goes.who.whogoes.service

import com.google.gson.Gson
import goes.who.whogoes.MyApplication
import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.Example
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
    lateinit var responseService : ResponseService

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

      val res =  responseService.transform(httpResponse,stat)

//        val topic = gson.fromJson(httpResponse.body()?.string(), Example::class.java)
//        val res: List<Datum> = topic.data.filter { x ->
//            x.name.equals("Rutha Monatan") ||
//                    x.name.equals("Eva Maria") ||
//                    x.name.equals("Sven Schmidt") ||
//                    x.name.equals("Mahnaz Rezai")
//        }


        if (res.nextURL.isNullOrEmpty())
            return res.datum
        return res.datum.plus(call(res.nextURL, stat))

    }

}