package conrado.gabriel.ragdoll.util.towel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.util.GenericViewHolder
import conrado.gabriel.ragdoll.util.ItemListener

class TowelViewHolder(inflater: LayoutInflater, parent: ViewGroup, itemListener: ItemListener<Towel>)
    : GenericViewHolder<Towel>(inflater, parent, R.layout.item_towel_list){

    private var towelTitle: TextView? = null
    private var towelAmount: TextView? = null
    private var towelAvailable: TextView? = null
    private var itemListener : ItemListener<Towel>
    private var container : CardView? = null

    init {
        towelTitle = itemView.findViewById(R.id.tv_item_towel_name)
        towelAmount = itemView.findViewById(R.id.tv_item_amount)
        towelAvailable = itemView.findViewById(R.id.tv_item_towel_available)
        container = itemView.findViewById(R.id.tv_item_towel_container)
        this.itemListener = itemListener
    }

    override fun bind(item: Towel, isActivated: Boolean) {
        towelTitle?.text = item.type
        towelAmount?.text = item.amount.toString()
        towelAvailable?.text = item.available.toString()
        container?.isActivated = isActivated
        container?.setOnClickListener { itemListener.onItemClick(item) }
    }

}