package conrado.gabriel.ragdoll.util.towel

import android.view.LayoutInflater
import android.view.ViewGroup
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.util.GenericListAdapter
import conrado.gabriel.ragdoll.util.ItemListener

class TowelListAdapter(towels: List<Towel>, private var itemListener: ItemListener<Towel>)
    : GenericListAdapter<Towel, TowelViewHolder>(towels) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TowelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TowelViewHolder(inflater, parent, itemListener)
    }

    override fun onBindViewHolder(holder: TowelViewHolder, position: Int) {
        selectionTracker?.let {
            holder.bind(items[position], it.isSelected(position.toLong()))
        }
    }

}