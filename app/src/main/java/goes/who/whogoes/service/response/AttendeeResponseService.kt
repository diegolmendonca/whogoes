package goes.who.whogoes.service.response

import goes.who.whogoes.model.*
import javax.inject.Singleton

/**
 * Created by Diego Mendonca on 21.09.2017.
 */

@Singleton
class AttendeeResponseService {

    fun processResponse(status: String, names: Set<String>, rawResponse: Example): List<Datum> {

        val formattedSet = names.map { it.trim().toUpperCase() }.toSet()


        val filtered = rawResponse.data.filter { constainsTheName(formattedSet,it.name) }

        val userStatus = when (status) {
            "attending/" -> Attending()
            "declined/" -> Declined()
            else -> Interested()
        }

        return filtered.map { x -> x.copy(status = userStatus) }
    }

    private fun constainsTheName(formattedSet: Set<String>, name:String): Boolean {
      return formattedSet.map { name.toUpperCase().contains(it)}.any{it.equals(true)}
    }

}
