package goes.who.whogoes.di

/**
 * Created by Diego Mendonca on 01.09.2017.
 */


import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import goes.who.whogoes.service.request.EventRequestService
import goes.who.whogoes.service.request.FacebookRequestInterface
import goes.who.whogoes.service.response.AttendeeResponseService
import goes.who.whogoes.service.response.EventResponseService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideFacebookRequestInterface(): FacebookRequestInterface {
        return Retrofit.Builder()
                .baseUrl("https://abc.com")  // dummy, I dont use it, as the URL are dynamically generated
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideClient())
                .build().create(FacebookRequestInterface::class.java)
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



}