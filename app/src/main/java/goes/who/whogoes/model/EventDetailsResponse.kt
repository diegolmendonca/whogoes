package goes.who.whogoes.model

/**
 * Created by Diego Mendonca on 20.09.2017.
 */


data class CoverEvent(val offsetX: Integer, val offsetY: Integer, val source: String, val id: String)

data class CursorsEvent(val before: String, val after: String)
data class DatumEvent(val name: String,
                      val description: String,
                      val cover: CoverEvent,
                      val place : Place,
                      val attending_count: Int,
                      val interested_count: Int,
                      val declined_count: Int,
                      val start_time: String,
                      val id: String)

data class Location(val city: String,
                      val country: String,
                      val street: String)



data class Place(val location: Location,
                    val name: String)


data class ExampleEvent(val data: List<DatumEvent>, val paging: PagingEvent)
data class PagingEvent(val cursors: CursorsEvent, val next: String)

