package goes.who.whogoes.activity

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import goes.who.whogoes.BuildConfig
import goes.who.whogoes.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutActivity : BasicActivity() {

    private val VERSION =  BuildConfig.VERSION_NAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        simulateDayNight(/* DAY */0)

        val aboutPage = AboutPage(this)
                .setDescription(description())
                .isRTL(false)
                .setImage(R.drawable.ic_launcher)
                .addItem(Element().setTitle(getString(R.string.version,VERSION)))
                .addGroup(getString(R.string.connect))
                .addEmail("whogoesapp@gmail.com")
                .addWebsite("http://github.com/diegolmendonca/whogoes")
                .addPlayStore("goes.who.whogoes")
                .addGitHub("diegolmendonca/whogoes")
                .create()

        setContentView(aboutPage)
    }

    private fun description(): String {
        return getString(R.string.description)
    }


    private fun simulateDayNight(currentSetting: Int) {
        val DAY = 0
        val NIGHT = 1
        val FOLLOW_SYSTEM = 3

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO)
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES)
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
