package goes.who.whogoes

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class InputActivity : AppCompatActivity() {

    lateinit var eventId: EditText
    lateinit var name: EditText
    lateinit var fire: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        eventId = findViewById<EditText>(R.id.eventId)
        name = findViewById<EditText>(R.id.name)
        fire = findViewById<Button>(R.id.fire)

    }


    override fun onResume() {
        super.onResume()

        val token = intent.getStringExtra("FACEBOOK_TOKEN")

        fire.setOnClickListener() { v ->


            if (isValidInput(eventId, name)) {

                val intent = Intent(applicationContext, EventActivity::class.java)
                intent.putExtra("FACEBOOK_TOKEN", token)
                intent.putExtra("eventId", eventId.text.toString())
                intent.putExtra("name", name.text.toString())
                startActivity(intent)

            }
        }

    }


    fun isValidInput(eventId: EditText, name: EditText): Boolean{
        if(eventId.text.isNullOrEmpty()){
            eventId.setError("Please input a Facebook event Id")
            return false
        }
         if(name.text.isNullOrEmpty()){
            name.setError("Please input a person name")
            return false
        }

        return true

    }

}
