package goes.who.whogoes

/**
 * Created by doma on 31.08.2017.
 */


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import goes.who.whogoes.service.HttpService
import goes.who.whogoes.service.MyInterface
import javax.inject.Inject

class EventActivity : AppCompatActivity() {
    @Inject
    lateinit var httpService: HttpService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        MyApplication.graph.inject(this)

        var token = intent.getStringExtra("FACEBOOK_TOKEN")
        var eventId = "1928591074051643"

        httpService.run(token, eventId, "", MyInterface())

    }


}