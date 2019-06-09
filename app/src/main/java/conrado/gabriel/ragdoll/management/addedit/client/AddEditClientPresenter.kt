package conrado.gabriel.ragdoll.management.addedit.client

import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.data.source.AbstractDataSource
import conrado.gabriel.ragdoll.data.source.DataRepository

class AddEditClientPresenter(
    private val dataRepository: DataRepository,
    private val addEditClientView: AddEditClientContract.View,
    private val clientId: String?)
    : AddEditClientContract.Presenter, AbstractDataSource.GetClientCallback {

    init {
        addEditClientView.presenter = this
    }

    override fun start() {
        if (clientId != null) populateClient()
    }

    override fun saveOrEditClient(client: Client) {
        if (client.isInvalid) {
            addEditClientView.showInvalidClientError()
        } else {
            if (clientId != null) {
                client.id = clientId
                dataRepository.editClient(client)
            } else {
                dataRepository.saveClient(client)
            }
            addEditClientView.showClientList()
        }
    }

    override fun populateClient() {
        if (clientId != null) {
            dataRepository.getClient(clientId, this)
        } else {
            throw RuntimeException("No Client ID Given")
        }
    }

    override fun onClientLoaded(client: Client) {

        addEditClientView.setName(client.name)
        addEditClientView.setAddress(client.address)
        addEditClientView.setPhone(client.phone)
        addEditClientView.setEmail(client.email)
        addEditClientView.setTowelPrice(client.towelPrice)
        addEditClientView.setBalance(client.balance)

    }

    override fun onNoClientLoaded() {
        addEditClientView.showInvalidClientError()
        addEditClientView.showClientList()
    }

}