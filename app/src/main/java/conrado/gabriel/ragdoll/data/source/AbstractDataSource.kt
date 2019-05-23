package conrado.gabriel.ragdoll.data.source

import conrado.gabriel.ragdoll.data.Towel

interface AbstractDataSource {

    interface LoadTowelsCallback {

        fun onTowelsLoaded(towels: List<Towel>)

        fun onNoTowelsLoaded()

    }

    fun getTowels(callback: LoadTowelsCallback)

    fun saveTowel(towel: Towel)

}