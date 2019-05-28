package conrado.gabriel.ragdoll.data.source

import conrado.gabriel.ragdoll.data.Towel

class DataRepository(val dataSource: AbstractDataSource) : AbstractDataSource{

    override fun getTowels(callback: AbstractDataSource.LoadTowelsCallback) {
        dataSource.getTowels(callback)
    }

    override fun saveTowel(towel: Towel) {
        dataSource.saveTowel(towel)
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