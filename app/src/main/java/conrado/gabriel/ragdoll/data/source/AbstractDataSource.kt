package conrado.gabriel.ragdoll.data.source

import conrado.gabriel.ragdoll.data.Towel

interface AbstractDataSource {

    interface LoadTowelsCallback {

        fun onTowelsLoaded(towels: List<Towel>)

        fun onNoTowelsLoaded()

    }

    interface GetTowelCallback {

        fun onTowelLoaded(towel: Towel)

        fun onNoTowelLoaded()

    }

    fun getTowels(callback: LoadTowelsCallback)

    fun saveTowel(towel: Towel)

    fun getTowel(towelId: String, callback: GetTowelCallback)

    fun removeTowel(towelId: String)

    fun removeTowels(towels: List<Towel>)

}