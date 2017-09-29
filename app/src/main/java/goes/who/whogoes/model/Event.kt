package goes.who.whogoes.model

/**
 * Created by Diego Mendonca on 08.09.2017.
 */


data class EventRequest(val eventID: String, val name: String, val token: String)
data class EventResponse(val datum: List<DatumEvent>)







