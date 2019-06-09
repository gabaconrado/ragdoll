package conrado.gabriel.ragdoll.management.list.clients

import android.app.Activity
import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.data.source.AbstractDataSource
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.management.addedit.client.AddEditClientActivity

class ListClientsPresenter(
    private val dataRepository: DataRepository, val listClientsView: ListClientsContract.View)
    : ListClientsContract.Presenter {

    init {
        listClientsView.presenter = this
    }

    override fun start() = loadClients()

    override fun loadClients() {
        listClientsView.setLoadingIndicator(true)
        dataRepository.getClients(object : AbstractDataSource.LoadClientsCallback{

            override fun onClientsLoaded(clients: List<Client>) {
                listClientsView.setLoadingIndicator(false)
                listClientsView.showClients(clients)
            }

            override fun onNoClientsLoaded() {
                listClientsView.setLoadingIndicator(false)
                listClientsView.showNoClients()
            }

        })
    }

    override fun newClient() {
        listClientsView.showAddClient()
    }

    override fun editClient(clientId: String) {
        listClientsView.showEditClient(clientId)
    }

    override fun removeClients(clients: List<Client>) {
        dataRepository.removeClients(clients)
        listClientsView.showRemoveClientSuccess()
    }

    override fun result(requestCode: Int, resultCode: Int) {
        if (requestCode == AddEditClientActivity.REQUEST_ADD_EDIT_CLIENT
            && resultCode == Activity.RESULT_OK) {
            listClientsView.showAddEditClientSuccess()
        }
    }

}