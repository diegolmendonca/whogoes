package goes.who.whogoes.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import goes.who.whogoes.R

class InputActivity : BasicActivity() {

    lateinit private var eventName: TextView
    lateinit private var name: EditText
    lateinit private var name2: EditText
    lateinit private var name3: EditText
    lateinit private var fire: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        eventName = findViewById<TextView>(R.id.eventName)
        name = findViewById<EditText>(R.id.name)
        name2 = findViewById<EditText>(R.id.name2)
        name3 = findViewById<EditText>(R.id.name3)
        fire = findViewById<Button>(R.id.fire)

        val chosenEventName = intent.getStringExtra("datumEventName")
        eventName.text = chosenEventName
    }

    override fun onResume() {
        super.onResume()

        val token = intent.getStringExtra("FACEBOOK_TOKEN")
        val eventId = intent.getStringExtra("datumEventId")

        fire.setOnClickListener() { _ ->

            if (isValidInput(name)) {
                val intent = Intent(applicationContext, AttendeeActivity::class.java)
                intent.putExtra("FACEBOOK_TOKEN", token)
                intent.putExtra("name", name.text?.toString())
                intent.putExtra("name2", name2.text?.toString())
                intent.putExtra("name3", name3.text?.toString())
                intent.putExtra("datumEventId", eventId)
                startActivity(intent)
            }
        }
    }

    private fun isValidInput(name: EditText): Boolean{
         if(name.text.isNullOrEmpty() && name2.text.isNullOrEmpty() && name3.text.isNullOrEmpty() ){
            name.error = getString(R.string.user_name_error)
            return false
        }
        return true

    }

}
