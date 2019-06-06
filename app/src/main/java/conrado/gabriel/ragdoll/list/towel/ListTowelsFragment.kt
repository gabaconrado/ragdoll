package conrado.gabriel.ragdoll.list.towel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.activity.HomeActivity
import conrado.gabriel.ragdoll.addedit.towel.AddEditTowelActivity
import conrado.gabriel.ragdoll.addedit.towel.AddEditTowelFragment
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.util.ItemListener
import conrado.gabriel.ragdoll.util.RagdollDetailsLookup
import conrado.gabriel.ragdoll.util.RagdollItemKeyProvider
import conrado.gabriel.ragdoll.util.towel.TowelListAdapter
import kotlinx.android.synthetic.main.fragment_list_towels.*


class ListTowelsFragment : Fragment(), ListTowelsContract.View, ItemListener<Towel> {

    private val towelListAdapter = TowelListAdapter(emptyList(), this)

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

    override fun onItemClick(item: Towel) {
        presenter.editTowel(item.id)
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) pb_loading_towels.visibility = View.VISIBLE else pb_loading_towels.visibility = View.GONE
    }

    override fun showTowels(towels: List<Towel>) {
        towelListAdapter.items = towels
        tv_no_towels.visibility = View.GONE
        rv_list_towels.visibility = View.VISIBLE
    }

    override fun showAddTowel() {
        val intent = Intent(context, AddEditTowelActivity::class.java)
        startActivityForResult(intent, AddEditTowelActivity.REQUEST_ADD_EDIT_TOWEL)
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

    override fun showEditTowel(taskId: String) {
        val intent = Intent(context, AddEditTowelActivity::class.java).apply {
            putExtra(AddEditTowelFragment.ARGUMENT_EDIT_TOWEL_ID, taskId)
        }
        startActivityForResult(intent, AddEditTowelActivity.REQUEST_ADD_EDIT_TOWEL)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
    }

    private fun createTowelTracker() : SelectionTracker<Long> {
        val selectionTracker = SelectionTracker.Builder<Long>(
            "TowelSelection",
            rv_list_towels,
            RagdollItemKeyProvider(rv_list_towels),
            RagdollDetailsLookup(rv_list_towels),
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
                towels.plusAssign(adapter.items[index.toInt()])
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

}