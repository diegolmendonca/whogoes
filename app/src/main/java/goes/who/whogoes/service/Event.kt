package goes.who.whogoes.service

import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.Example

/**
 * Created by doma on 08.09.2017.
 */


data class RequestModel(val eventID: String, val name: String,val token: String)
data class ResponseModel(val datum: List<Datum>, val nextURL: String?)







