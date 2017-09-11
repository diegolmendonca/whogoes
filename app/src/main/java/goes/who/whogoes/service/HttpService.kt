package goes.who.whogoes.service

import com.google.gson.Gson
import goes.who.whogoes.MyApplication
import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.Example
import okhttp3.*
import java.io.IOException
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

    @Inject @field:Named("baseURI") lateinit var baseURI : String
    @Inject @field:Named("attending") lateinit var attending : String
    @Inject @field:Named("interested") lateinit var interested : String
    @Inject @field:Named("remainingURI") lateinit var remainingURI : String
    @Inject @field:Named("after") lateinit var after : String

    constructor()  {
        MyApplication.graph.inject(this)
    }

    fun run(token: String, eventId :String , afterToken:String?, response2: MyInterface ) {
        val base = baseURI + eventId + "/" + interested + remainingURI + token
        var url = ""

        if (afterToken.isNullOrEmpty()){
             url = base
        } else {
            url = afterToken.toString()

        }
        performCall(url,response2, token, eventId)

    }



    private fun teste(response2: MyInterface, token: String, eventId :String, acc: List<Datum> ) : List<Datum>{

        val firstIt = response2.res.plus(acc)
        val hasAfter = response2.after


        if (hasAfter.isNotEmpty()) {
             run(token, eventId, hasAfter, response2)
        }


            return firstIt



    }

    private fun performCall(url: String?,response2: MyInterface, token: String, eventId :String) {

        val request = Request.Builder()
                .url(url)
                .build()

      httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {

                val jsonData = response.body()?.string()
                val gson = Gson()
                val topic = gson.fromJson(jsonData, Example::class.java)
                response2.setResponse(topic)
                teste(response2,token, eventId,emptyList())

            }
        })
    }




}