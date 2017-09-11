package goes.who.whogoes.service

import goes.who.whogoes.model.Datum
import goes.who.whogoes.model.Example

/**
 * Created by doma on 08.09.2017.
 */

class MyInterface {
     var res : List<Datum> = listOf()
     var after : String = ""

    fun setResponse(example: Example) {
        res = example.data.filter { x -> x.name.equals("Rutha Monatan") }
        after = example.paging.next
    }

   // fun getRes() : List<Datum> { return res }
}
