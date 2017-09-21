package goes.who.whogoes.service.response

import com.google.gson.Gson
import goes.who.whogoes.di.MyApplication
import goes.who.whogoes.model.*
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Diego Mendonca on 12.09.2017.
 */

interface ResponseService<T> {
    fun transform(response: Response) : T
}
