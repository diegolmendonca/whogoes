package goes.who.whogoes.service.response

import okhttp3.Response

/**
 * Created by Diego Mendonca on 12.09.2017.
 */

interface ResponseService<T> {
    fun transform(response: Response) : T
}
