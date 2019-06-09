package conrado.gabriel.ragdoll.management.addedit.client

import android.os.Bundle
import conrado.gabriel.ragdoll.BaseActivity
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.data.source.memory.MemoryDataSource
import conrado.gabriel.ragdoll.data.source.memory.MemoryDatabase

class AddEditClientActivity : BaseActivity(R.layout.activity_add_edit_client) {

    private lateinit var addEditClientPresenter: AddEditClientPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val clientId = intent?.getStringExtra(AddEditClientFragment.ARGUMENT_EDIT_CLIENT_ID)

        val fragment = supportFragmentManager.findFragmentById(R.id.add_edit_client_content_frame)
            as AddEditClientFragment? ?: AddEditClientFragment.newInstance(clientId).also {
            replaceFragmentInActivity(it, R.id.add_edit_client_content_frame)
        }

        addEditClientPresenter = AddEditClientPresenter(
            DataRepository(MemoryDataSource(MemoryDatabase)),
            fragment,
            clientId
        )
    }

    companion object {
        const val REQUEST_ADD_EDIT_CLIENT  = 1
    }

}