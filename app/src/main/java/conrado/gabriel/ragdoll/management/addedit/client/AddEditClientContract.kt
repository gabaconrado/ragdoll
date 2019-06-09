package conrado.gabriel.ragdoll.management.addedit.client

import conrado.gabriel.ragdoll.BasePresenter
import conrado.gabriel.ragdoll.BaseView
import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.data.Towel

interface AddEditClientContract {

    interface View : BaseView<Presenter> {

        fun showClientList()

        fun showInvalidClientError()

        fun setName(name: String)

        fun setAddress(address: String)

        fun setPhone(phone: String)

        fun setEmail(email: String)

        fun setTowelPrice(towelPrice: Double)

        fun setTowels(towels: Map<Towel, Int>?)

        fun setBalance(balance: Double)

    }

    interface Presenter : BasePresenter {

        fun saveOrEditClient(client: Client)

        fun populateClient()

    }

}
