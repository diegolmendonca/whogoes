package goes.who.whogoes

/**
 * Created by doma on 31.08.2017.
 */


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import goes.who.whogoes.service.HttpService
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.CommonPool
import java.io.IOException
import javax.inject.Inject

class EventActivity : AppCompatActivity()  {
    @Inject
    lateinit var httpService: HttpService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        MyApplication.graph.inject(this)

        var token = intent.getStringExtra("FACEBOOK_TOKEN")
        var eventId = "476707972696392"



        launch(CommonPool) {
            try {
                val result = httpService.performCall(token, eventId)
               val r =  result.await()

                r.get(0)
            } catch (exception: IOException){
                Toast.makeText(this@EventActivity, "Phone not connected or service down", Toast.LENGTH_SHORT).show()
            }

        }



    }


}