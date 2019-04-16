package conrado.gabriel.ragdoll.model

import java.util.*

data class Delivery(
    var date: Date, var value: Double, var client: Client, var towelsBorrowed: Int, var towelsCollected: Int
)