package conrado.gabriel.ragdoll.management.list.transactions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.Transaction
import conrado.gabriel.ragdoll.management.ManagementActivity
import conrado.gabriel.ragdoll.management.addedit.transaction.AddEditTransactionActivity
import conrado.gabriel.ragdoll.management.addedit.transaction.AddEditTransactionFragment
import conrado.gabriel.ragdoll.util.ItemListener
import conrado.gabriel.ragdoll.util.RagdollDetailsLookup
import conrado.gabriel.ragdoll.util.RagdollItemKeyProvider
import conrado.gabriel.ragdoll.util.transaction.TransactionListAdapter
import kotlinx.android.synthetic.main.fragment_list_transactions.*

class ListTransactionsFragment : Fragment(), ListTransactionsContract.View, ItemListener<Transaction> {

    override lateinit var presenter: ListTransactionsContract.Presenter

    private lateinit var transactionSelectionTracker: SelectionTracker<Long>

    private val transactionListAdapter = TransactionListAdapter(emptyList(), this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = inflater.inflate(R.layout.fragment_list_transactions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_list_transactions.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = transactionListAdapter
        }

        transactionSelectionTracker = createTransactionTracker()

        (rv_list_transactions.adapter as TransactionListAdapter).selectionTracker = transactionSelectionTracker

        fab_new_transaction.setOnClickListener { handleFabClick() }

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) pb_loading_transactions.visibility = View.VISIBLE else pb_loading_transactions.visibility = View.GONE
    }

    override fun showTransactions(transactions: List<Transaction>) {
        transactionListAdapter.items = transactions
        tv_no_transactions.visibility = View.GONE
        rv_list_transactions.visibility = View.VISIBLE
    }

    override fun showNoTransactions() {
        tv_no_transactions.visibility = View.VISIBLE
        rv_list_transactions.visibility = View.GONE
    }

    override fun showAddTransaction() {
        val intent = Intent(context, AddEditTransactionActivity::class.java)
        startActivityForResult(intent, AddEditTransactionActivity.REQUEST_ADD_EDIT_TRANSACTION)
    }

    override fun showEditTransaction(transactionId: String) {
        val intent = Intent(context, AddEditTransactionActivity::class.java).apply {
            putExtra(AddEditTransactionFragment.ARGUMENT_EDIT_TRANSACTION_ID, transactionId)
        }
        startActivityForResult(intent, AddEditTransactionActivity.REQUEST_ADD_EDIT_TRANSACTION)
    }

    override fun showAddEditTransactionSuccess() {
        val parentActivity = activity as ManagementActivity?
        parentActivity?.showMessage(getString(R.string.success_add_edit_transaction))
    }

    override fun showRemoveTransactionSuccess() {
        val parentActivity = activity as ManagementActivity?
        parentActivity?.showMessage(getString(R.string.success_remove_transaction))
    }

    override fun onItemClick(item: Transaction) {
        presenter.editTransaction(item.id)
    }

    private fun createTransactionTracker() : SelectionTracker<Long> {
        val selectionTracker = SelectionTracker.Builder<Long>(
            "TransactionSelection",
            rv_list_transactions,
            RagdollItemKeyProvider(rv_list_transactions),
            RagdollDetailsLookup(rv_list_transactions),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        selectionTracker.addObserver(
            object : SelectionTracker.SelectionObserver<Long>(){
                override fun onSelectionChanged() {
                    val selectedCount = selectionTracker?.selection?.size() ?: 0
                    if (selectedCount > 0) {
                        switchFab(STATE_REMOVE)
                    } else {
                        switchFab(STATE_ADD)
                    }
                }
            }
        )
        return selectionTracker
    }

    private fun switchFab(state: Int){
        fab_new_transaction.hide()
        val newDrawableId = when (state) {
            STATE_ADD -> R.drawable.ic_add
            STATE_REMOVE -> R.drawable.ic_delete
            else -> R.drawable.ic_add
        }
        fab_new_transaction.setImageResource(newDrawableId)
        fab_new_transaction.show()
    }

    private fun handleFabClick(){

        if (transactionSelectionTracker.hasSelection()) {
            // Remove transactions
            val transactions = mutableListOf<Transaction>()
            val adapter = rv_list_transactions?.adapter as TransactionListAdapter
            // Build list
            for (index in transactionSelectionTracker.selection) {
                transactions.plusAssign(adapter.items[index.toInt()])
            }
            transactionSelectionTracker.clearSelection()
            presenter.removeTransactions(transactions)
            adapter.notifyDataSetChanged()
        } else {
            presenter.newTransaction()
        }

    }

    companion object {
        const val STATE_ADD = 1
        const val STATE_REMOVE = 2
        fun newInstance() = ListTransactionsFragment()
    }

}