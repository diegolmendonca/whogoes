package goes.who.whogoes.model

import android.graphics.Color

/**
 * Created by Diego Mendonca on 12.09.2017.
 */

interface UserEventStatus {
    fun status(): String
    fun color(): Int

}

class Interested : UserEventStatus {
    override fun status(): String {
        return "interested/"
    }

    override fun color(): Int {
        return Color.BLUE
    }

}

class Declined : UserEventStatus {
    override fun status(): String {
        return "declined/"
    }

    override fun color(): Int {
        return Color.RED
    }

}

class Attending : UserEventStatus {
    override fun status(): String {
        return "attending/"
    }

    override fun color(): Int {
        return Color.parseColor("#006400")  // dark green
    }

}


