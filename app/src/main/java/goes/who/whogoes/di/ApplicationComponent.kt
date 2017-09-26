package goes.who.whogoes.di

import dagger.Component
import goes.who.whogoes.activity.AttendeeActivity
import goes.who.whogoes.activity.EventFinderResponseActivity
import goes.who.whogoes.activity.MainActivity
import goes.who.whogoes.service.request.AttendeeRequestService
import goes.who.whogoes.service.request.EventRequestService
import goes.who.whogoes.service.response.AttendeeResponseService
import goes.who.whogoes.service.response.EventResponseService
import javax.inject.Singleton

/**
 * Created by Diego Mendonca on 01.09.2017.
 */

@Singleton
@Component(modules = arrayOf(AndroidModule::class, RequestStringModule::class))
interface ApplicationComponent {
    fun inject(application: MyApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(attendeeResponseService: AttendeeResponseService)
    fun inject(eventResponseService: EventResponseService)
    fun inject(eventRequestService: EventRequestService)
    fun inject(attendeeRequestService: AttendeeRequestService)
    fun inject(attendeeActivity: AttendeeActivity)
    fun inject(eventFinderResponseActivity: EventFinderResponseActivity)
}

