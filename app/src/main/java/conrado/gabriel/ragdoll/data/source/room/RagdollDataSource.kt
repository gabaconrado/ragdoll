package conrado.gabriel.ragdoll.data.source.room

import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.data.Transaction
import conrado.gabriel.ragdoll.data.source.AbstractDataSource

class RagdollDataSource(private val ragdollDatabase: RagdollDatabase) : AbstractDataSource {

    override fun getTowels(callback: AbstractDataSource.LoadTowelsCallback) {
        val towels = ragdollDatabase.towelDao().getAll()
        if (towels.isEmpty()){
            callback.onNoTowelsLoaded()
        } else {
            callback.onTowelsLoaded(towels)
        }
    }

    override fun saveTowel(towel: Towel) {
        ragdollDatabase.towelDao().insertAll(towel)
    }

    override fun getTowel(towelId: String, callback: AbstractDataSource.GetTowelCallback) {
        val towel = ragdollDatabase.towelDao().getById(towelId)
        if (towel == null){
            callback.onNoTowelLoaded()
        } else {
            callback.onTowelLoaded(towel)
        }
    }

    override fun removeTowel(towelId: String) {
        ragdollDatabase.towelDao().getById(towelId)?.let { ragdollDatabase.towelDao().delete(it) }
    }

    override fun removeTowels(towels: List<Towel>) {
        for (towel in towels) {
            ragdollDatabase.towelDao().delete(towel)
        }
    }

    override fun editTowel(towel: Towel) {
        ragdollDatabase.towelDao().update(towel)
    }

    override fun getClients(callback: AbstractDataSource.LoadClientsCallback) {
        TODO("Not yet implemented")
    }

    override fun saveClient(client: Client) {
        TODO("Not yet implemented")
    }

    override fun getClient(clientId: String, callback: AbstractDataSource.GetClientCallback) {
        TODO("Not yet implemented")
    }

    override fun removeClient(clientId: String) {
        TODO("Not yet implemented")
    }

    override fun removeClients(clients: List<Client>) {
        TODO("Not yet implemented")
    }

    override fun editClient(client: Client) {
        TODO("Not yet implemented")
    }

    override fun getTransactions(callback: AbstractDataSource.LoadTransactionsCallback) {
        TODO("Not yet implemented")
    }

    override fun saveTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override fun getTransaction(
        transactionId: String,
        callback: AbstractDataSource.GetTransactionCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun removeTransaction(transactionId: String) {
        TODO("Not yet implemented")
    }

    override fun removeTransactions(transactions: List<Transaction>) {
        TODO("Not yet implemented")
    }

    override fun editTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }
}