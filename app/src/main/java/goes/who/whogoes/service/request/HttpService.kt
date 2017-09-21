package goes.who.whogoes.service.request

import goes.who.whogoes.model.RequestModel


/**
 * Created by Diego Mendonca on 08.09.2017.
 */

interface HttpService<T> {
    fun performCall(request : RequestModel): T
}