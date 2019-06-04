package conrado.gabriel.ragdoll.data

import java.util.*

data class Towel(var type: String = "") {

    var id: String = UUID.randomUUID().toString()

     var amount: Int = 0
        set(value) {
            field = if (value < 0) 0 else value
        }

    var available: Int = 0
        set(value) {
            field = if (value > amount) amount else (if (value < 0) 0 else value)
        }

    val isInvalid
        get() = (amount == 0 || type == "")

}