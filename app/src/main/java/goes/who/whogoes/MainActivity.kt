package goes.who.whogoes

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

class MainActivity : AppCompatActivity() {

    lateinit  var button : Button
    lateinit  var callbackManager : CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.login_button)
        callbackManager = CallbackManager.Factory.create()

       val accessToken = AccessToken.getCurrentAccessToken()

       if (!accessToken.isExpired) {
            val intent = Intent(applicationContext, EventActivity::class.java)
            intent.putExtra("FACEBOOK_TOKEN", accessToken.token);
            startActivity(intent)
        } else {


            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)

                    val intent = Intent(applicationContext, EventActivity::class.java)
                    intent.putExtra("FACEBOOK_TOKEN", loginResult.accessToken.token);
                    startActivity(intent)
                }

                override fun onCancel() {
                    Log.d("MainActivity", "Facebook onCancel.")

                }

                override fun onError(error: FacebookException) {
                    Log.d("MainActivity", "Facebook onError.")


                }
            })

       }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }

}


//https://github.com/betranthanh/android-kotlin-loginfacebook/blob/master/app/src/main/java/com/bett/kotlinloginfacebook/AuthenticatedActivity.kt