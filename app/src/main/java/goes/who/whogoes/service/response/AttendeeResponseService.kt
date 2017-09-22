package goes.who.whogoes.service.response

import com.google.gson.Gson
import goes.who.whogoes.di.MyApplication
import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.Example
import goes.who.whogoes.model.ResponseModel
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Diego Mendonca on 21.09.2017.
 */

@Singleton
class AttendeeResponseService: ResponseService<Example> {

    constructor() {
        MyApplication.graph.inject(this)
    }

    @Inject
    lateinit var gson: Gson

    override fun transform(response: Response): Example {
        val topic = gson.fromJson(response.body()?.string(), Example::class.java)
        return topic
    }

    fun processResponse(response: Response, stat: String, name: String): ResponseModel {
        val topic = transform(response)
        response.body()?.close()
        val res: List<Datum> = topic.data.filter { x -> x.name.contains(name) }
        val next = if (topic.paging != null)  topic.paging.next else null
        val categorizedStatus = res.map { x -> x.copy(status = stat) }
        return ResponseModel(categorizedStatus, next)
    }

}
