package goes.who.whogoes.model

import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.DatumEvent
import goes.who.whogoes.model.Example

/**
 * Created by Diego Mendonca on 08.09.2017.
 */


data class RequestModel(val eventID: String, val name: String,val token: String)
data class ResponseModel(val datum: List<Datum>, val nextURL: String?)
data class ResponseModel2(val datum: List<DatumEvent>)







