package conrado.gabriel.ragdoll.management.list.clients

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
import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.management.ManagementActivity
import conrado.gabriel.ragdoll.management.addedit.client.AddEditClientActivity
import conrado.gabriel.ragdoll.management.addedit.client.AddEditClientFragment
import conrado.gabriel.ragdoll.management.list.towel.ListTowelsFragment
import conrado.gabriel.ragdoll.util.ItemListener
import conrado.gabriel.ragdoll.util.RagdollDetailsLookup
import conrado.gabriel.ragdoll.util.RagdollItemKeyProvider
import conrado.gabriel.ragdoll.util.client.ClientListAdapter
import kotlinx.android.synthetic.main.fragment_list_clients.*

class ListClientsFragment : Fragment(), ListClientsContract.View, ItemListener<Client> {

    override lateinit var presenter: ListClientsContract.Presenter

    private lateinit var clientSelectionTracker : SelectionTracker<Long>

    private val clientListAdapter = ClientListAdapter(emptyList(), this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = inflater.inflate(R.layout.fragment_list_clients, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_list_clients.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = clientListAdapter
        }

        clientSelectionTracker = createClientTracker()

        (rv_list_clients.adapter as ClientListAdapter).selectionTracker = clientSelectionTracker

        fab_new_client.setOnClickListener { handleFabClick() }

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) pb_loading_clients.visibility = View.VISIBLE else pb_loading_clients.visibility = View.GONE
    }

    override fun showClients(clients: List<Client>) {
        clientListAdapter.items = clients
        tv_no_clients.visibility = View.GONE
        rv_list_clients.visibility = View.VISIBLE
    }

    override fun showNoClients() {
        tv_no_clients.visibility = View.VISIBLE
        rv_list_clients.visibility = View.GONE
    }

    override fun showAddClient() {
        val intent = Intent(context, AddEditClientActivity::class.java)
        startActivityForResult(intent, AddEditClientActivity.REQUEST_ADD_EDIT_CLIENT)
    }

    override fun showEditClient(clientId: String) {
        val intent = Intent(context, AddEditClientActivity::class.java).apply {
            putExtra(AddEditClientFragment.ARGUMENT_EDIT_CLIENT_ID, clientId)
        }
        startActivityForResult(intent, AddEditClientActivity.REQUEST_ADD_EDIT_CLIENT)
    }

    override fun showAddEditClientSuccess() {
        val parentActivity = activity as ManagementActivity?
        parentActivity?.showMessage(getString(R.string.success_add_edit_client))
    }

    override fun showRemoveClientSuccess() {
        val parentActivity = activity as ManagementActivity?
        parentActivity?.showMessage(getString(R.string.success_remove_client))
    }

    override fun onItemClick(item: Client) {
        presenter.editClient(item.id)
    }

    private fun createClientTracker() : SelectionTracker<Long> {
        val selectionTracker = SelectionTracker.Builder<Long>(
            "ClientSelection",
            rv_list_clients,
            RagdollItemKeyProvider(rv_list_clients),
            RagdollDetailsLookup(rv_list_clients),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        selectionTracker.addObserver(
            object : SelectionTracker.SelectionObserver<Long>(){
                override fun onSelectionChanged() {
                    val selectedCount = selectionTracker?.selection?.size() ?: 0
                    if (selectedCount > 0) {
                        switchFab(ListTowelsFragment.STATE_REMOVE)
                    } else {
                        switchFab(ListTowelsFragment.STATE_ADD)
                    }
                }
            }
        )
        return selectionTracker
    }

    private fun switchFab(state: Int){
        fab_new_client.hide()
        val newDrawableId = when (state) {
            STATE_ADD -> R.drawable.ic_add
            STATE_REMOVE -> R.drawable.ic_delete
            else -> R.drawable.ic_add
        }
        fab_new_client.setImageResource(newDrawableId)
        fab_new_client.show()
    }

    private fun handleFabClick(){

        if (clientSelectionTracker.hasSelection()) {
            // Remove clients
            val clients = mutableListOf<Client>()
            val adapter = rv_list_clients?.adapter as ClientListAdapter
            // Build list
            for (index in clientSelectionTracker.selection) {
                clients.plusAssign(adapter.items[index.toInt()])
            }
            clientSelectionTracker.clearSelection()
            presenter.removeClients(clients)
            adapter.notifyDataSetChanged()
        } else {
            presenter.newClient()
        }

    }

    companion object {
        const val STATE_ADD = 1
        const val STATE_REMOVE = 2
        fun newInstance() = ListClientsFragment()
    }

}