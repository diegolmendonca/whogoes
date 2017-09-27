package goes.who.whogoes.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import goes.who.whogoes.R


open class BasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isNetworkAvailable()) {

            val builder = AlertDialog.Builder(this)
            builder.setMessage(getString(R.string.internet))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), DialogInterface.OnClickListener { _, _ ->
                        this.finishAffinity()
                    })
            val alert = builder.create()
            alert.show()
        } else {

            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()?.setHomeButtonEnabled(true)


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.exit -> logout()
            R.id.about -> {
                val intent = Intent(applicationContext, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    fun logout() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.exit))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener { _, _ ->
                    //    LoginManager.getInstance().logOut() // should I logout from facebook?
                    this.finishAffinity()
                })
                .setNegativeButton(getString(R.string.no), DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
        val alert = builder.create()
        alert.show()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
