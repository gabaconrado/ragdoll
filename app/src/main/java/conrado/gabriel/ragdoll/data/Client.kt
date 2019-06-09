package conrado.gabriel.ragdoll.data

import java.util.*

data class Client(var name: String, var address: String, var phone: String, var email: String, var towelPrice: Double) {

    var id: String = UUID.randomUUID().toString()

    var towels: Map<Towel, Int>? = null

    var balance: Double = 0.0

    val isInvalid
        get() = (name == "" || address == "" || phone == "" || email == "" || towelPrice < 0.0)

}