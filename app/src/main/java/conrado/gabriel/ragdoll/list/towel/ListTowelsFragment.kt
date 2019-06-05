package conrado.gabriel.ragdoll.list.towel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.activity.HomeActivity
import conrado.gabriel.ragdoll.addedit.towel.AddEditTowelActivity
import conrado.gabriel.ragdoll.data.Towel
import kotlinx.android.synthetic.main.fragment_list_towels.*


class ListTowelsFragment : Fragment(), ListTowelsContract.View{

    private val towelListAdapter = TowelListAdapter(emptyList())

    override lateinit var presenter: ListTowelsContract.Presenter

    private lateinit var towelSelectionTracker : SelectionTracker<Long>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = inflater.inflate(R.layout.fragment_list_towels, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_list_towels.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = towelListAdapter
        }

        towelSelectionTracker = createTowelTracker()

        (rv_list_towels.adapter as TowelListAdapter).selectionTracker = towelSelectionTracker

        fab_new_towel.setOnClickListener { handleFabClick() }

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) pb_loading_towels.visibility = View.VISIBLE else pb_loading_towels.visibility = View.GONE
    }

    override fun showTowels(towel: List<Towel>) {
        towelListAdapter.towels = towel
        tv_no_towels.visibility = View.GONE
        rv_list_towels.visibility = View.VISIBLE
    }

    override fun showAddTowel() {
        val intent = Intent(context, AddEditTowelActivity::class.java)
        startActivityForResult(intent, AddEditTowelActivity.REQUEST_ADD_TOWEL)
    }

    override fun showNoTowels() {
        tv_no_towels.visibility = View.VISIBLE
        rv_list_towels.visibility = View.GONE
    }

    override fun showAddEditSuccess() {
        val parentActivity = (activity as HomeActivity?)
        parentActivity?.showMessage(getString(R.string.success_add_edit_towel))
    }

    override fun showRemoveSuccess() {
        val parentActivity = (activity as HomeActivity?)
        parentActivity?.showMessage(getString(R.string.success_remove_towel))
    }

    private fun createTowelTracker() : SelectionTracker<Long> {
        val selectionTracker = SelectionTracker.Builder<Long>(
            "TowelSelection",
            rv_list_towels,
            TowelListAdapter.TowelItemKeyProvider(rv_list_towels),
            TowelListAdapter.TowelDetailsLookup(rv_list_towels),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        selectionTracker.addObserver(
            object : SelectionTracker.SelectionObserver<Long>(){
                override fun onSelectionChanged() {
                    val selectedCount = selectionTracker?.selection?.size() ?: 0
                    if (selectedCount > 0) switchFab(STATE_REMOVE) else switchFab(STATE_ADD)
                }
            }
        )
        return selectionTracker
    }

    private fun switchFab(state: Int){
        fab_new_towel.hide()
        val newDrawableId = when (state) {
            STATE_ADD -> R.drawable.ic_add
            STATE_REMOVE -> R.drawable.ic_delete
            else -> R.drawable.ic_add
        }
        fab_new_towel.setImageResource(newDrawableId)
        fab_new_towel.show()
    }

    private fun handleFabClick(){

        if (towelSelectionTracker.hasSelection()) {
            // Remove towels
            val towels = mutableListOf<Towel>()
            val adapter = rv_list_towels?.adapter as TowelListAdapter
            // Build list
            for (index in towelSelectionTracker.selection) {
                towels.plusAssign(adapter.towels[index.toInt()])
            }
            towelSelectionTracker.clearSelection()
            presenter.removeTowels(towels)
        } else {
            presenter.newTowel()
        }

    }

    companion object {
        const val STATE_ADD = 1
        const val STATE_REMOVE = 2
        fun newInstance() = ListTowelsFragment()
    }

    /**
     * Adapter to hold the RecyclerView Towel items
     */
    class TowelListAdapter(towels: List<Towel>)
        : RecyclerView.Adapter<TowelListAdapter.TowelListViewHolder>(){

        init {
            setHasStableIds(true)
        }

        var selectionTracker : SelectionTracker<Long>? = null

        var towels: List<Towel> = towels
            set(towels) {
                field = towels
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TowelListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return TowelListViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: TowelListViewHolder, position: Int) {
            selectionTracker?.let {
                holder.bind(towels[position], it.isSelected(position.toLong()))
            }
        }


        override fun getItemCount(): Int = towels.size

        override fun getItemId(position: Int): Long = position.toLong()

        inner class TowelListViewHolder(inflater: LayoutInflater, parent: ViewGroup)
            : ViewHolder(inflater.inflate(R.layout.item_towel_list, parent, false)){

            private var towelTitle: TextView? = null

            init {
                towelTitle = itemView.findViewById(R.id.tv_item_towel_title)
            }

            fun bind(towel: Towel, isActivated: Boolean){
                towelTitle?.text = towel.type
                towelTitle?.isActivated = isActivated
            }

            fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> = object
                : ItemDetailsLookup.ItemDetails<Long>() {
                    override fun getSelectionKey(): Long? = itemId
                    override fun getPosition(): Int = adapterPosition
            }

        }

        class TowelDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>(){

            override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
                val view = recyclerView.findChildViewUnder(e.x, e.y)
                if (view != null) return (recyclerView.getChildViewHolder(view) as TowelListViewHolder).getItemDetails()
                return null
            }

        }

        class TowelItemKeyProvider(private val recyclerView: RecyclerView) :
            ItemKeyProvider<Long>(SCOPE_MAPPED) {

            override fun getKey(position: Int): Long? {
                return recyclerView.adapter?.getItemId(position)
            }

            override fun getPosition(key: Long): Int {
                val viewHolder = recyclerView.findViewHolderForItemId(key)
                return viewHolder?.layoutPosition ?: RecyclerView.NO_POSITION
            }
        }

    }

}