package conrado.gabriel.ragdoll.data.source

import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.data.Transaction

interface AbstractDataSource {

    // Towels

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

    fun editTowel(towel: Towel)

    // Clients

    interface LoadClientsCallback {

        fun onClientsLoaded(clients: List<Client>)

        fun onNoClientsLoaded()

    }

    interface GetClientCallback {

        fun onClientLoaded(client: Client)

        fun onNoClientLoaded()

    }

    fun getClients(callback: LoadClientsCallback)

    fun saveClient(client: Client)

    fun getClient(clientId: String, callback: GetClientCallback)

    fun removeClient(clientId: String)

    fun removeClients(clients: List<Client>)

    fun editClient(client: Client)

    // Finances

    interface LoadTransactionsCallback {

        fun onTransactionsLoaded(transactions: List<Transaction>)

        fun onNoTransactionsLoaded()

    }

    interface GetTransactionCallback {

        fun onTransactionLoaded(transaction: Transaction)

        fun onNoTransactionLoaded()

    }

    fun getTransactions(callback: LoadTransactionsCallback)

    fun saveTransaction(transaction: Transaction)

    fun getTransaction(transactionId: String, callback: GetTransactionCallback)

    fun removeTransaction(transactionId: String)

    fun removeTransactions(transactions: List<Transaction>)

    fun editTransaction(transaction: Transaction)

}