package conrado.gabriel.ragdoll.util.client

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.util.GenericViewHolder
import conrado.gabriel.ragdoll.util.ItemListener

class ClientViewHolder(inflater: LayoutInflater, parent: ViewGroup, var itemListener: ItemListener<Client>)
    : GenericViewHolder<Client>(inflater, parent, R.layout.item_client_list){

    private var clientName : TextView? = itemView.findViewById(R.id.tv_item_client_name)


    override fun bind(item: Client, isActivated: Boolean) {
        clientName?.text = item.name
        clientName?.isActivated = isActivated
        clientName?.setOnClickListener { itemListener.onItemClick(item) }
    }

}