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
    @field:Named("baseURI") lateinit var baseURI: String
    @Inject
    @field:Named("attending") lateinit var attending: String
    @Inject
    @field:Named("interested") lateinit var interested: String
    @Inject
    @field:Named("remainingURI") lateinit var remainingURI: String
    @Inject
    @field:Named("after") lateinit var after: String

    constructor() {
        MyApplication.graph.inject(this)
    }

    fun performCall(token: String, eventId: String): Deferred<List<Datum>> {
        return async(CommonPool) {
            call(baseURI + eventId + "/" + interested + remainingURI + token)
        }
    }

    private fun call (url: String?): List<Datum> {
        val request = Request.Builder().url(url).build()
        val response = httpClient.newCall(request).execute()
        val topic = gson.fromJson(response.body()?.string(), Example::class.java)
        val res: List<Datum> = topic.data.filter { x -> x.name.equals("Eva Maria") || x.name.equals("Sven Schmidt") || x.name.equals("Mahnaz Rezai") }
        val nextPageURI = topic.paging.next

        if (nextPageURI.isNullOrEmpty()) {
            return res
        } else {
            return res.plus(call(nextPageURI))
        }
    }

}