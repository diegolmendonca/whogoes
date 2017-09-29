package goes.who.whogoes.service.request

import goes.who.whogoes.model.EventRequest


/**
 * Created by Diego Mendonca on 08.09.2017.
 */

interface HttpService<T> {
    fun performCall(eventRequest: EventRequest): T
}