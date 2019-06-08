package conrado.gabriel.ragdoll.data.source

import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.data.Towel

class DataRepository(private val dataSource: AbstractDataSource) : AbstractDataSource{


    // Towels
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

    override fun editTowel(towel: Towel){
        dataSource.editTowel(towel)
    }

    // Clients
    override fun getClients(callback: AbstractDataSource.LoadClientsCallback) {
        dataSource.getClients(callback)
    }

    override fun saveClient(client: Client) {
        dataSource.saveClient(client)
    }

    override fun getClient(clientId: String, callback: AbstractDataSource.GetClientCallback) {
        dataSource.getClient(clientId, callback)
    }

    override fun removeClient(clientId: String) {
        dataSource.removeClient(clientId)
    }

    override fun removeClients(clients: List<Client>) {
        dataSource.removeClients(clients)
    }

    override fun editClient(client: Client) {
        dataSource.editClient(client)
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