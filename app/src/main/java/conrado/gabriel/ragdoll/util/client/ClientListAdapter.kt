package conrado.gabriel.ragdoll.util.client

import android.view.LayoutInflater
import android.view.ViewGroup
import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.util.GenericListAdapter
import conrado.gabriel.ragdoll.util.ItemListener

class ClientListAdapter(clients: List<Client>, private var itemListener: ItemListener<Client>)
    : GenericListAdapter<Client, ClientViewHolder>(clients) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ClientViewHolder(inflater, parent, itemListener)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        selectionTracker?.let {
            holder.bind(items[position], it.isSelected(position.toLong()))
        }
    }

}