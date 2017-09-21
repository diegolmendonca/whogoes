package goes.who.whogoes.service

import com.google.gson.Gson
import goes.who.whogoes.di.MyApplication
import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.Example
import goes.who.whogoes.model.ExampleEvent
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Diego Mendonca on 12.09.2017.
 */



@Singleton
class ResponseService {

    constructor() {
        MyApplication.graph.inject(this)
    }

    @Inject
    lateinit var gson: Gson

    fun transform(response: Response, stat: String, name: String): ResponseModel {
        val topic = gson.fromJson(response.body()?.string(), Example::class.java)
        val res: List<Datum> = topic.data.filter { x -> x.name.contains(name) }

        val next = if (topic.paging != null)  topic.paging.next else null

        val categorizedStatus = res.map { x -> x.copy(status = stat) }
        return ResponseModel(categorizedStatus, next)
    }

    fun transform2(response: Response): ResponseModel2 {
        val topic = gson.fromJson(response.body()?.string(), ExampleEvent::class.java)
        return ResponseModel2(topic.data)
    }
}