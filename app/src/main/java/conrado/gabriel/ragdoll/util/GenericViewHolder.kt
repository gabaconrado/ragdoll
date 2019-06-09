package conrado.gabriel.ragdoll.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericViewHolder<T>(inflater: LayoutInflater, parent: ViewGroup, layoutId: Int)
    : RecyclerView.ViewHolder(inflater.inflate(layoutId, parent, false)) {

    abstract fun bind(item: T, isActivated: Boolean)

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> = object
        : ItemDetailsLookup.ItemDetails<Long>() {
        override fun getSelectionKey(): Long? = itemId
        override fun getPosition(): Int = adapterPosition
    }

}