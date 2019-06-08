package conrado.gabriel.ragdoll.data.source.memory

import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.data.source.AbstractDataSource

class MemoryDataSource(private val database: MemoryDatabase) : AbstractDataSource {

    override fun getTowel(towelId: String, callback: AbstractDataSource.GetTowelCallback) {
        for (t in database.towels){
            if (t.id == towelId) {
                callback.onTowelLoaded(t)
                return
            }
        }
        callback.onNoTowelLoaded()
    }

    override fun getTowels(callback: AbstractDataSource.LoadTowelsCallback) {
        if (database.towels.isEmpty()) callback.onNoTowelsLoaded() else callback.onTowelsLoaded(database.towels)
    }

    override fun saveTowel(towel: Towel) {
        database.towels.plusAssign(towel)
    }

    override fun removeTowel(towelId: String) {
        database.towels.removeIf { it.id == towelId }
    }

    override fun removeTowels(towels: List<Towel>) {
        for (t in towels) removeTowel(t.id)
    }

    override fun editTowel(towel: Towel) {
        for (t in database.towels){
            if (t.id == towel.id){
                t.type = towel.type
                t.amount = towel.amount
                t.available = towel.available
            }
        }
    }

}
