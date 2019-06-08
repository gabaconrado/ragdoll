package conrado.gabriel.ragdoll.data

import java.util.*

data class Client(var name: String, var address: String, var phone: String, var email: String, var towelPrice: Double) {

    var id: String = UUID.randomUUID().toString()

    var towels: Collection<Towel>? = null

    var balance: Double = 0.0

}