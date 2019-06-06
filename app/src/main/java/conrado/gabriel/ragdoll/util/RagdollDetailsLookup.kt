package conrado.gabriel.ragdoll.util

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class RagdollDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>(){

    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) return (recyclerView.getChildViewHolder(view) as GenericViewHolder<*>).getItemDetails()
        return null
    }

}