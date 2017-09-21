package goes.who.whogoes.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import goes.who.whogoes.MyApplication
import goes.who.whogoes.R


class MainActivity : AppCompatActivity() {

    lateinit  var button : Button
    lateinit  var callbackManager : CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_main)
        MyApplication.graph.inject(this)

        button = findViewById(R.id.login_button)
        callbackManager = CallbackManager.Factory.create()


        val accessToken : AccessToken? = AccessToken.getCurrentAccessToken()

        if (accessToken != null && !accessToken.isExpired) {
            startEventFinderActivity(accessToken)
        } else {

            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                    startEventFinderActivity(loginResult.accessToken)
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

    private fun startEventFinderActivity(accessToken: AccessToken) {
        val intent = Intent(applicationContext, EventFinderActivity::class.java)
        intent.putExtra("FACEBOOK_TOKEN", accessToken.token);
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }

}