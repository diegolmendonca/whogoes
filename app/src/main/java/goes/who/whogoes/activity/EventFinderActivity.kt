package goes.who.whogoes.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import goes.who.whogoes.R

class EventFinderActivity : BasicActivity() {

    lateinit var eventName: EditText
    lateinit var fire: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_finder)

        eventName = findViewById(R.id.eventName) as EditText
        fire = findViewById(R.id.fire) as Button

    }

    override fun onBackPressed() {
        logout()
    }

    override fun onResume() {
        super.onResume()

        val token = intent.getStringExtra("FACEBOOK_TOKEN")

        fire.setOnClickListener() { _ ->

            if (isValidInput(eventName)) {

                val intent = Intent(applicationContext, EventFinderResponseActivity::class.java)
                intent.putExtra("FACEBOOK_TOKEN", token)
                intent.putExtra("eventName", eventName.text.toString())
                startActivity(intent)
            }
        }
    }

    fun isValidInput(eventId: EditText): Boolean{
        if(eventId.text.isNullOrEmpty()){
            eventId.setError(getString(R.string.facebook_event_name_error))
            return false
        }
        return true

    }

}