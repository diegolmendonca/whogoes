package goes.who.whogoes

/**
 * Created by doma on 01.09.2017.
 */


import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import goes.who.whogoes.service.Attending
import goes.who.whogoes.service.HttpService
import goes.who.whogoes.service.Interested
import goes.who.whogoes.service.ResponseService
import okhttp3.OkHttpClient
import javax.inject.Named
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
        return okHttpClientBuilder.build()

    }

    @Provides
    @Singleton
    fun provideGSON(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideResponseService(): ResponseService {
        return  ResponseService()
    }


    @Provides
    @Singleton
    fun provideHttpService(): HttpService {
        val httpService = HttpService()
        // Try to inject it
        httpService.userEventStatus = listOf(Attending(), Interested())
        return  httpService
    }


    @Provides @Named("baseURI")
    @Singleton
    fun baseURI(): String {
        return "https://graph.facebook.com/v2.10/"
    }

    @Provides @Named("remainingURI")
    @Singleton
    fun remainingURI(): String {
        return "?fields=name&limit=5000&access_token="
    }

}