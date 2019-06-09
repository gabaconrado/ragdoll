package conrado.gabriel.ragdoll.data

import java.util.*

data class Transaction(var description: String, var cost: Double, var category: String) {

    var id: String = UUID.randomUUID().toString()

    val isInvalid
        get() = (description == "" || category == "")

}