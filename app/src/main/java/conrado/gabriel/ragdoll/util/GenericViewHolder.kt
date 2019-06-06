package conrado.gabriel.ragdoll.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import conrado.gabriel.ragdoll.R

abstract class GenericViewHolder<T>(inflater: LayoutInflater, parent: ViewGroup)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_towel_list, parent, false)) {

    abstract fun bind(item: T, isActivated: Boolean)

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> = object
        : ItemDetailsLookup.ItemDetails<Long>() {
        override fun getSelectionKey(): Long? = itemId
        override fun getPosition(): Int = adapterPosition
    }

}