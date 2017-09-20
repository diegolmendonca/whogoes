package goes.who.whogoes

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class EventFinderActivity : AppCompatActivity() {

    lateinit var eventName: EditText
    lateinit var fire: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_finder)

        eventName = findViewById<EditText>(R.id.eventName)
        fire = findViewById<Button>(R.id.fire)

    }

    override fun onResume() {
        super.onResume()

        val token = intent.getStringExtra("FACEBOOK_TOKEN")

        fire.setOnClickListener() { v ->

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
            eventId.setError("Please input a Facebook event Id")
            return false
        }
        return true

    }

}