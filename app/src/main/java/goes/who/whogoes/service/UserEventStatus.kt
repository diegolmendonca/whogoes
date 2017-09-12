package goes.who.whogoes.service

/**
 * Created by doma on 12.09.2017.
 */

interface UserEventStatus{
    fun status() : String
}

class Interested : UserEventStatus{
    override fun status(): String {
        return "interested/"
    }
}

class Attending : UserEventStatus{
    override fun status(): String {
        return "attending/"
    }
}


