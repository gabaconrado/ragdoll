package conrado.gabriel.ragdoll.data.source.memory

import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.data.Transaction
import conrado.gabriel.ragdoll.data.source.AbstractDataSource

class MemoryDataSource(private val database: MemoryDatabase) : AbstractDataSource {

    // Towels
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

    // Clients
    override fun getClients(callback: AbstractDataSource.LoadClientsCallback) {
        if (database.clients.isEmpty()) callback.onNoClientsLoaded() else callback.onClientsLoaded(database.clients)
    }

    override fun saveClient(client: Client) {
        database.clients.plusAssign(client)
    }

    override fun getClient(clientId: String, callback: AbstractDataSource.GetClientCallback) {
        for (c in database.clients){
            if (c.id == clientId){
                callback.onClientLoaded(c)
                return
            }
        }
        callback.onNoClientLoaded()
    }

    override fun removeClient(clientId: String) {
        database.clients.removeIf { it.id == clientId }
    }

    override fun removeClients(clients: List<Client>) {
        for (c in clients) removeClient(c.id)
    }

    override fun editClient(client: Client) {
        for (c in database.clients){
            if (c.id == client.id){
                c.name = client.name
                c.address = client.address
                c.phone = client.phone
                c.email = client.email
                c.towelPrice = client.towelPrice
                c.towels = client.towels
                c.balance = client.balance
            }
        }
    }

    // Transactions
    override fun getTransactions(callback: AbstractDataSource.LoadTransactionsCallback) {
        if (database.transactions.isEmpty()) {
            callback.onNoTransactionsLoaded()
        } else {
            callback.onTransactionsLoaded(database.transactions)
        }
    }

    override fun saveTransaction(transaction: Transaction) {
        database.transactions.plusAssign(transaction)
    }

    override fun getTransaction(transactionId: String, callback: AbstractDataSource.GetTransactionCallback) {
        for (t in database.transactions) {
            if (t.id == transactionId) {
                callback.onTransactionLoaded(t)
                return
            }
        }
        callback.onNoTransactionLoaded()
    }

    override fun removeTransaction(transactionId: String) {
        database.transactions.removeIf { it.id == transactionId }
    }

    override fun removeTransactions(transactions: List<Transaction>) {
        for (t in transactions) removeTransaction(t.id)
    }

    override fun editTransaction(transaction: Transaction) {
        for (t in database.transactions) {
            if (t.id == transaction.id) {
                t.description = transaction.description
                t.cost = transaction.cost
                t.category = transaction.category
            }
        }
    }

}
