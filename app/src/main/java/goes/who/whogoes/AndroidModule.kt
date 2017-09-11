package goes.who.whogoes

/**
 * Created by doma on 01.09.2017.
 */


import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import goes.who.whogoes.service.HttpService
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
    fun provideHttpService(): HttpService {
        return  HttpService()
    }


    @Provides @Named("baseURI")
    @Singleton
    fun baseURI(): String {
      //  return "https://graph.facebook.com/v2.10/1928591074051643/attending/?fields=name&limit=5000&access_token="

        return "https://graph.facebook.com/v2.10/"
    }

    @Provides @Named("attending")
    @Singleton
    fun attending(): String {
        return "attending/"
    }

    @Provides @Named("interested")
    @Singleton
    fun interested(): String {
        return "interested/"
    }

    @Provides @Named("remainingURI")
    @Singleton
    fun remainingURI(): String {
        return "?fields=name&limit=500&access_token="
    }

    @Provides @Named("after")
    @Singleton
    fun after(): String {
        return "?after="
    }

}