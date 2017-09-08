package goes.who.whogoes

import dagger.Component
import goes.who.whogoes.service.HttpService
import javax.inject.Singleton

/**
 * Created by doma on 01.09.2017.
 */

@Singleton
@Component(modules = arrayOf(AndroidModule::class))
interface ApplicationComponent {
    fun inject(application: MyApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(eventActivity: EventActivity)
    fun inject(httpService: HttpService)
}
