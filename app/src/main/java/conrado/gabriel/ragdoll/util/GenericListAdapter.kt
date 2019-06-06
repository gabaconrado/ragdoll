package conrado.gabriel.ragdoll.util

import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView

abstract class GenericListAdapter<T, K : RecyclerView.ViewHolder?>(items: List<T>)
    : RecyclerView.Adapter<K>(){

    var selectionTracker: SelectionTracker<Long>? = null

    init {
        this.setHasStableIds(true)
    }

    var items: List<T> = items
        set(items) {
            field = items
            notifyDataSetChanged()
        }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K

    abstract override fun onBindViewHolder(holder: K, position: Int)

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = position.toLong()

}