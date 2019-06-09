package conrado.gabriel.ragdoll.util.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.Transaction
import conrado.gabriel.ragdoll.util.GenericViewHolder
import conrado.gabriel.ragdoll.util.ItemListener

class TransactionViewHolder(
    inflater: LayoutInflater, parent: ViewGroup, private var itemListener: ItemListener<Transaction>)
    : GenericViewHolder<Transaction>(inflater, parent, R.layout.item_transaction_list) {

    private var transactionDescription : TextView? = itemView.findViewById(R.id.tv_item_transaction_description)

    override fun bind(item: Transaction, isActivated: Boolean) {
        transactionDescription?.text = item.description
        transactionDescription?.isActivated = isActivated
        transactionDescription?.setOnClickListener { itemListener.onItemClick(item) }
    }

}