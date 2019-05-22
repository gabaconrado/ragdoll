package conrado.gabriel.ragdoll.data.source

import conrado.gabriel.ragdoll.data.Towel

interface AbstractDataSource {

    interface LoadTowelsCallback {

        fun onTaskLoaded(towels: List<Towel>)

        fun onNoTasksLoaded()

    }

    fun getTowels(callback: LoadTowelsCallback)

    fun saveTowel(towel: Towel)

}