package goes.who.whogoes.di

import android.app.Application

/**
 * Created by Diego Mendonca on 01.09.2017.
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