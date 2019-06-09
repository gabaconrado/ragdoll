package conrado.gabriel.ragdoll.management.addedit.transaction

import conrado.gabriel.ragdoll.BasePresenter
import conrado.gabriel.ragdoll.BaseView
import conrado.gabriel.ragdoll.data.Transaction

interface AddEditTransactionContract {

    interface View : BaseView<Presenter> {

        fun showTransactionList()

        fun showInvalidTransactionError()

        fun setDescription(description: String)

        fun setCost(cost: Double)

        fun setCategory(category: String)

    }

    interface Presenter : BasePresenter {

        fun saveOrEditTransaction(transaction: Transaction)

        fun populateTransaction()

    }
}