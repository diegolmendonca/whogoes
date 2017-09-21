package goes.who.whogoes.di

/**
 * Created by Diego Mendonca on 01.09.2017.
 */


import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import goes.who.whogoes.model.Attending
import goes.who.whogoes.model.Declined
import goes.who.whogoes.model.Interested
import goes.who.whogoes.service.request.AttendeeRequestService
import goes.who.whogoes.service.request.EventRequestService
import goes.who.whogoes.service.response.AttendeeResponseService
import goes.who.whogoes.service.response.EventResponseService
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AndroidModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideGSON(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideAttendeeResponseService(): AttendeeResponseService {
        return AttendeeResponseService()
    }

    @Provides
    @Singleton
    fun provideEventResponseService(): EventResponseService {
        return EventResponseService()
    }

    @Provides
    @Singleton
    fun provideEventRequestService(): EventRequestService {
        val eventRequestService = EventRequestService()
        return  eventRequestService
    }

    @Provides
    @Singleton
    fun provideAttendeeRequestService(): AttendeeRequestService {
        val attendeeRequestService = AttendeeRequestService()
        // Try to inject it
        attendeeRequestService.userEventStatus = listOf(Attending(), Interested(), Declined())
        return  attendeeRequestService
    }

}