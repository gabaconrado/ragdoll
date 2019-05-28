package conrado.gabriel.ragdoll.data.source.memory

import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.data.source.AbstractDataSource

class MemoryDataSource(val database: MemoryDatabase) : AbstractDataSource {

    override fun getTowels(callback: AbstractDataSource.LoadTowelsCallback) {
        if (database.towels.isEmpty()) callback.onNoTowelsLoaded() else callback.onTowelsLoaded(database.towels)
    }

    override fun saveTowel(towel: Towel) {
        database.towels.plus(towel)
    }


}
