package goes.who.whogoes.model

/**
 * Created by Diego Mendonca on 12.09.2017.
 */

interface UserEventStatus {
    fun status(): String
}

class Interested : UserEventStatus {
    override fun status(): String {
        return "interested/"
    }
}

class Declined : UserEventStatus {
    override fun status(): String {
        return "declined/"
    }
}

class Attending : UserEventStatus {
    override fun status(): String {
        return "attending/"
    }
}


