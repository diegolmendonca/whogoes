package goes.who.whogoes

/**
 * Created by doma on 31.08.2017.
 */


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import goes.who.whogoes.R
import goes.who.whogoes.model.Example
import okhttp3.*
import java.io.IOException

class EventActivity : AppCompatActivity() {

    lateinit  var client : OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        client = OkHttpClient()

        var token = intent.getStringExtra("FACEBOOK_TOKEN")

        val URL = "https://graph.facebook.com/v2.10/1928591074051643/attending/?fields=name&limit=5000&access_token=$token"

        run(URL)
    }

    fun run(url: String) {
        val request = Request.Builder()
                .url(url)
                .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response)  {

                val jsonData = response.body()?.string()
                val gson = Gson()
                val topic = gson.fromJson(jsonData, Example::class.java)
                //     Rutha Monatan
                val res =   topic.data.filter { x-> x.name.equals("Rutha Monatan") }

                println(response.body()?.string())
            }
        })
    }

}