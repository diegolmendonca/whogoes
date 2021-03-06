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

        eventName = findViewById<EditText>(R.id.eventName)
        fire = findViewById<Button>(R.id.fire)

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

    private fun isValidInput(eventId: EditText): Boolean{
        if(eventId.text.isNullOrEmpty()){
            eventId.error = getString(R.string.facebook_event_name_error)
            return false
        }
        return true

    }

}