package goes.who.whogoes.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import goes.who.whogoes.R

class InputActivity : BasicActivity() {

    lateinit var eventName: TextView
    lateinit var name: EditText
    lateinit var fire: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        eventName = findViewById<TextView>(R.id.eventName)
        name = findViewById<EditText>(R.id.name)
        fire = findViewById<Button>(R.id.fire)

        val chosenEventName = intent.getStringExtra("datumEventName")
        eventName.text = chosenEventName
    }

    override fun onResume() {
        super.onResume()

        val token = intent.getStringExtra("FACEBOOK_TOKEN")
        val eventId = intent.getStringExtra("datumEventId")

        fire.setOnClickListener() { v ->

            if (isValidInput(name)) {
                val intent = Intent(applicationContext, AttendeeActivity::class.java)
                intent.putExtra("FACEBOOK_TOKEN", token)
                intent.putExtra("name", name.text.toString())
                intent.putExtra("datumEventId", eventId)
                startActivity(intent)
            }
        }
    }

    fun isValidInput(name: EditText): Boolean{
         if(name.text.isNullOrEmpty()){
            name.setError(getString(R.string.user_name_error))
            return false
        }
        return true

    }

}
