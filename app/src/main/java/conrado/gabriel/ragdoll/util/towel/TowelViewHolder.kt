package conrado.gabriel.ragdoll.util.towel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.util.GenericViewHolder
import conrado.gabriel.ragdoll.util.ItemListener

class TowelViewHolder(inflater: LayoutInflater, parent: ViewGroup, itemListener: ItemListener<Towel>)
    : GenericViewHolder<Towel>(inflater, parent, R.layout.item_towel_list){

    private var towelTitle: TextView? = null
    private var itemListener : ItemListener<Towel>

    init {
        towelTitle = itemView.findViewById(R.id.tv_item_towel_title)
        this.itemListener = itemListener
    }

    override fun bind(item: Towel, isActivated: Boolean) {
        towelTitle?.text = item.type
        towelTitle?.isActivated = isActivated
        towelTitle?.setOnClickListener { itemListener.onItemClick(item) }
    }

}