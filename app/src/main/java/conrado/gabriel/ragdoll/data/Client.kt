package conrado.gabriel.ragdoll.data

data class Client(var name: String, var address: String, var phone: String, var email: String, var towelPrice: Double) {
    var towels: Collection<Towel>? = null
    var balance: Double = 0.0
}