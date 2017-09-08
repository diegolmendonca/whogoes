package goes.who.whogoes

import android.app.Application
import android.location.LocationManager
import javax.inject.Inject

/**
 * Created by doma on 01.09.2017.
 */


class MyApplication : Application() {

    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerApplicationComponent.builder().androidModule(AndroidModule(this)).build()
        graph.inject(this)

        //TODO do some other cool stuff here
    }
}