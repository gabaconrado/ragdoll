package conrado.gabriel.ragdoll.management.list.transactions

import android.app.Activity
import conrado.gabriel.ragdoll.data.Transaction
import conrado.gabriel.ragdoll.data.source.AbstractDataSource
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.management.addedit.transaction.AddEditTransactionActivity

class ListTransactionsPresenter(
    private val dataRepository: DataRepository, val listTransactionsView: ListTransactionsContract.View)
    : ListTransactionsContract.Presenter {

    init {
        listTransactionsView.presenter = this
    }

    override fun start() = loadTransactions()

    override fun loadTransactions() {

        listTransactionsView.setLoadingIndicator(true)
        dataRepository.getTransactions(object : AbstractDataSource.LoadTransactionsCallback {

            override fun onTransactionsLoaded(transactions: List<Transaction>) {
                listTransactionsView.setLoadingIndicator(false)
                listTransactionsView.showTransactions(transactions)
            }

            override fun onNoTransactionsLoaded() {
                listTransactionsView.setLoadingIndicator(false)
                listTransactionsView.showNoTransactions()
            }

        })
    }

    override fun newTransaction() {
        listTransactionsView.showAddTransaction()
    }

    override fun editTransaction(transactionId: String) {
        listTransactionsView.showEditTransaction(transactionId)
    }

    override fun removeTransactions(transactions: List<Transaction>) {
        dataRepository.removeTransactions(transactions)
        listTransactionsView.showRemoveTransactionSuccess()
    }

    override fun result(requestCode: Int, resultCode: Int) {
        if (requestCode == AddEditTransactionActivity.REQUEST_ADD_EDIT_TRANSACTION
            && resultCode == Activity.RESULT_OK) {
            listTransactionsView.showAddEditTransactionSuccess()
        }
    }

}