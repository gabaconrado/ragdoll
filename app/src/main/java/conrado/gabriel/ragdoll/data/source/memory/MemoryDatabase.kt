package conrado.gabriel.ragdoll.data.source.memory

import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.data.Transaction

object MemoryDatabase {

    val towels: MutableList<Towel> = mutableListOf()

    val clients: MutableList<Client> = mutableListOf()

    val transactions: MutableList<Transaction> = mutableListOf()

}