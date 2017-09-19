package goes.who.whogoes.service

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.HttpMethod
import goes.who.whogoes.MyApplication
import goes.who.whogoes.model.Datum
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import com.facebook.Profile.getCurrentProfile
import com.google.gson.Gson
import goes.who.whogoes.R.id.response
import goes.who.whogoes.model.Example
import org.json.JSONObject


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
    lateinit var gson: Gson

    @Inject
    @field:Named("baseURI") lateinit var baseURI: String

    @Inject
    @field:Named("remainingURI") lateinit var remainingURI: String


    lateinit var request: GraphRequest

    constructor() {
        MyApplication.graph.inject(this)
    }

    fun performCall(request: RequestModel): List<Deferred<List<Datum>>> {
        return userEventStatus.map { stat ->
            async(CommonPool) {
                call(baseURI + request.eventID + "/" + stat.status() + remainingURI + request.token, stat.status(), request.name, 0)
            }
        }
    }

    private fun call(url: String?, stat: String, name: String, i: Int): List<Datum> {

        println("EXECUTANDO CHAMADA: " + i + "  DO:  " + stat)
     //   val request = Request.Builder().url(url).build()

        return  doInBackground(stat)
//            val httpResponse = httpClient.newCall(request).execute()
//            val formattedResponse = responseService.transform(httpResponse, stat, name)
//
//            if (formattedResponse.nextURL.isNullOrEmpty())  response = formattedResponse.datum
//
//            val a = i + 1
//            response = formattedResponse.datum.plus(call(formattedResponse.nextURL, stat, name, a))

    }


    protected fun doInBackground(stat: String): List<Datum> {

        val parameters = Bundle()
        parameters.putString("fields", "name")
        parameters.putString("limit", "2000")

        var faceCallback = FaceCallback(listOf(),0, gson)

          GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "1933183363569999/$stat",
                parameters,
                HttpMethod.GET,
                faceCallback)
                .executeAndWait()

        return faceCallback.res2
    }



    class FaceCallback(resultList: List<Datum>, i: Int, gson: Gson) : GraphRequest.Callback {
        var res2 = resultList
        var numr = i
        var gso = gson
        override fun onCompleted(response: GraphResponse?) {

            println("Executando chamada :" + numr)
            val json = response?.jsonObject
            val jarray = json?.getJSONArray("data")

            val topic = gso.fromJson(response?.rawResponse, Example::class.java)
            val res: List<Datum> = topic.data.filter { x -> x.name.contains("Ramón") }


            res2 = res.plus(res2)
            numr = numr + 1


            val nextRequest = response?.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT)
            if (nextRequest != null) {
                val parameters = Bundle()
                nextRequest.parameters = parameters
                nextRequest.callback = this
                nextRequest.executeAndWait()
            }

        }
    }


}