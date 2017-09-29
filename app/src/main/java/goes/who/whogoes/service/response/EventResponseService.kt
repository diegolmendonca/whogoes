package goes.who.whogoes.service.response

import com.google.gson.Gson
import goes.who.whogoes.di.MyApplication
import goes.who.whogoes.model.ExampleEvent
import goes.who.whogoes.model.EventResponse
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Diego Mendonca on 21.09.2017.
 */
@Singleton
class EventResponseService: ResponseService<ExampleEvent> {

    constructor() {
        MyApplication.graph.inject(this)
    }

    @Inject
    lateinit var gson: Gson

    override fun transform(response: Response): ExampleEvent {
        return gson.fromJson(response.body()?.string(), ExampleEvent::class.java)

    }

    fun processResponse(response: Response): EventResponse {
        val topic = transform(response)
        response.body()?.close()
        return EventResponse(topic.data)
    }


}