package goes.who.whogoes.service.response

import goes.who.whogoes.model.*
import javax.inject.Singleton

/**
 * Created by Diego Mendonca on 21.09.2017.
 */

@Singleton
class AttendeeResponseService {

    fun processResponse(status: String, name: String, rawResponse: Example): List<Datum> {

        val filtered = rawResponse.data.filter { x -> x.name.toUpperCase().contains(name.trim().toUpperCase()) }

        val result = when (status) {
            "attending/" -> Attending()
            "declined/" -> Declined()
            else -> Interested()
        }

        return filtered.map { x -> x.copy(status = result) }
    }

}
