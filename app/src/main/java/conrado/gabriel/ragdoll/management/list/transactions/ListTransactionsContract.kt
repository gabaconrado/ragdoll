package conrado.gabriel.ragdoll.management.list.transactions

import conrado.gabriel.ragdoll.BasePresenter
import conrado.gabriel.ragdoll.BaseView
import conrado.gabriel.ragdoll.data.Transaction

interface ListTransactionsContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showTransactions(transactions: List<Transaction>)

        fun showNoTransactions()

        fun showAddTransaction()

        fun showEditTransaction(transactionId: String)

        fun showAddEditTransactionSuccess()

        fun showRemoveTransactionSuccess()

    }

    interface Presenter : BasePresenter {

        fun loadTransactions()

        fun newTransaction()

        fun editTransaction(transactionId: String)

        fun removeTransactions(transactions: List<Transaction>)

        fun result(requestCode: Int, resultCode: Int)

    }

}