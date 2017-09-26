package goes.who.whogoes.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.facebook.login.LoginManager
import goes.who.whogoes.R

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
            eventId.setError(getString(R.string.facebook_event_name_error))
            return false
        }
        return true

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.exit -> LoginManager.getInstance().logOut()
            R.id.reset -> LoginManager.getInstance().logOut()
            R.id.about -> Log.d("Testind", "Testing")
        }
        return true
    }

}