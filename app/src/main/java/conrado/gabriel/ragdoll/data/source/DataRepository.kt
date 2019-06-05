package conrado.gabriel.ragdoll.data.source

import conrado.gabriel.ragdoll.data.Towel

class DataRepository(private val dataSource: AbstractDataSource) : AbstractDataSource{

    override fun getTowel(towelId: String, callback: AbstractDataSource.GetTowelCallback) {
        dataSource.getTowel(towelId, callback)
    }

    override fun getTowels(callback: AbstractDataSource.LoadTowelsCallback) {
        dataSource.getTowels(callback)
    }

    override fun saveTowel(towel: Towel) {
        dataSource.saveTowel(towel)
    }

    override fun removeTowel(towelId: String) {
        dataSource.removeTowel(towelId)
    }

    override fun removeTowels(towels: List<Towel>) {
        dataSource.removeTowels(towels)
    }

    companion object {

        private var INSTANCE: DataRepository? = null

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }

        /**
         * Returns the single instance of this class, creating it if necessary.
         *
         * @param dataSource the device storage data source
         *
         * @return the [DataRepository] instance
         */
        @JvmStatic fun getInstance(dataSource: AbstractDataSource): DataRepository{
            return INSTANCE ?: DataRepository(dataSource)
                .apply { INSTANCE = this }
        }
    }

}