package goes.who.whogoes.di

import dagger.Module
import dagger.Provides
import goes.who.whogoes.model.Attending
import goes.who.whogoes.model.Declined
import goes.who.whogoes.model.Interested
import goes.who.whogoes.model.UserEventStatus
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Diego Mendonca on 21.09.2017.
 */

@Module
class RequestStringModule {

    val limit = 5000
    val field = "fields"
    val attendeeField = "=name,picture"
    val eventField = "=name%2Ccover%2Cattending_count%2Cinterested_count%2Cdeclined_count%2Cstart_time"


    @Provides
    @Named("baseURI")
    @Singleton
    fun baseURI(): String {
        return "https://graph.facebook.com/v2.10/"
    }

    @Provides
    @Named("remainingURI")
    @Singleton
    fun remainingURI(): String {
        return "?$field$attendeeField&limit=$limit&access_token="
    }

    @Provides
    @Named("remainingEventURI")
    @Singleton
    fun remainingEventURI(): String {
        return "&type=event&$field$eventField&limit=$limit&access_token="
    }

    @Provides
    @Named("query")
    @Singleton
    fun query(): String {
        return "search?q="
    }

}
