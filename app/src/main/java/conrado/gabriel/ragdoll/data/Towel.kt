package conrado.gabriel.ragdoll.data

data class Towel(var type: String) {

    private var amount: Int = 0
        set(value) {
            field = if (value < 0) 0 else value
        }

    var available: Int = 0
        set(value) {
            field = if (value > amount) amount else (if (value < 0) 0 else value)
        }

}