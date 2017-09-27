package goes.who.whogoes.activity

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import goes.who.whogoes.R


open class BasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setHomeButtonEnabled(true);

        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.exit -> logout()
            R.id.about -> Log.d("Testing", "Testing")  // implement
        }
        return true
    }

    fun logout() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.exit))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener { dialog, id ->
                //    LoginManager.getInstance().logOut() // should I logout from facebook?
                    this.finishAffinity()
                })
                .setNegativeButton(getString(R.string.no), DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert = builder.create()
        alert.show()
    }
}
