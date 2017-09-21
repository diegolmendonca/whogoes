package goes.who.whogoes.service.request

import goes.who.whogoes.di.MyApplication
import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.DatumEvent
import goes.who.whogoes.model.RequestModel
import goes.who.whogoes.model.UserEventStatus
import goes.who.whogoes.service.response.AttendeeResponseService
import goes.who.whogoes.service.response.EventResponseService
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by Diego Mendonca on 08.09.2017.
 */

interface HttpService<T> {
    fun performCall(request : RequestModel): T



}