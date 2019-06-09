package conrado.gabriel.ragdoll.management.list.clients

import conrado.gabriel.ragdoll.BasePresenter
import conrado.gabriel.ragdoll.BaseView
import conrado.gabriel.ragdoll.data.Client

interface ListClientsContract {

    interface View: BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showClients(clients: List<Client>)

        fun showNoClients()

        fun showAddClient()

        fun showEditClient(clientId: String)

        fun showAddEditClientSuccess()

        fun showRemoveClientSuccess()

    }

    interface Presenter: BasePresenter {

        fun loadClients()

        fun newClient()

        fun editClient(clientId: String)

        fun removeClients(clients: List<Client>)

        fun result(requestCode: Int, resultCode: Int)

    }

}