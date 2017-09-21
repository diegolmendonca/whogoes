package goes.who.whogoes.model

import goes.who.whogoes.service.UserEventStatus

/**
 * Created by doma on 30.08.2017.
 */

data class Cursors(val before: String, val after: String)
data class Data (val is_silhouette : Boolean, val url :String)
data class Picture(val data :Data)
data class Datum(val name: String, val id: String, val status : String, val picture: Picture)
data class Paging(val cursors: Cursors , val next: String )
data class Example(val data : List<Datum>, val paging: Paging? )

