package conrado.gabriel.ragdoll.util.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import conrado.gabriel.ragdoll.data.Transaction
import conrado.gabriel.ragdoll.util.GenericListAdapter
import conrado.gabriel.ragdoll.util.ItemListener

class TransactionListAdapter(transactions: List<Transaction>, private var itemListener: ItemListener<Transaction>)
    : GenericListAdapter<Transaction, TransactionViewHolder>(transactions){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TransactionViewHolder(inflater, parent, itemListener)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        selectionTracker?.let {
            holder.bind(items[position], it.isSelected(position.toLong()))
        }
    }

}