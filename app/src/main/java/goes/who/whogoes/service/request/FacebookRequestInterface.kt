package goes.who.whogoes.service.request

import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.Example
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by doma on 25.09.2017.
 */
interface FacebookRequestInterface {

    @GET()
    abstract fun getDatum(@Url  url:String): Observable<Example>
}