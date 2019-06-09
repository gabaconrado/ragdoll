package conrado.gabriel.ragdoll.management.addedit.transaction

import conrado.gabriel.ragdoll.data.Transaction
import conrado.gabriel.ragdoll.data.source.AbstractDataSource
import conrado.gabriel.ragdoll.data.source.DataRepository

class AddEditTransactionPresenter(
    private val dataRepository: DataRepository,
    private val addEditTransactionView: AddEditTransactionContract.View,
    private val transactionId: String?)
    : AddEditTransactionContract.Presenter, AbstractDataSource.GetTransactionCallback {

    init {
        addEditTransactionView.presenter = this
    }

    override fun start() {
        if (transactionId != null) populateTransaction()
    }

    override fun saveOrEditTransaction(transaction: Transaction) {
        if (transaction.isInvalid) {
            addEditTransactionView.showInvalidTransactionError()
        } else {
            if (transactionId != null) {
                transaction.id = transactionId
                dataRepository.editTransaction(transaction)
            } else {
                dataRepository.saveTransaction(transaction)
            }
            addEditTransactionView.showTransactionList()
        }
    }

    override fun populateTransaction() {
        if (transactionId != null) {
            dataRepository.getTransaction(transactionId, this)
        } else {
            throw RuntimeException("No Transaction ID Given")
        }
    }

    override fun onTransactionLoaded(transaction: Transaction) {

        addEditTransactionView.setDescription(transaction.description)
        addEditTransactionView.setCost(transaction.cost)
        addEditTransactionView.setCategory(transaction.category)

    }

    override fun onNoTransactionLoaded() {
        addEditTransactionView.showInvalidTransactionError()
        addEditTransactionView.showTransactionList()
    }

}