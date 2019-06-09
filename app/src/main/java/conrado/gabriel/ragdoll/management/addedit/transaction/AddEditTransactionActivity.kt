package conrado.gabriel.ragdoll.management.addedit.transaction

import android.os.Bundle
import conrado.gabriel.ragdoll.BaseActivity
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.data.source.memory.MemoryDataSource
import conrado.gabriel.ragdoll.data.source.memory.MemoryDatabase

class AddEditTransactionActivity : BaseActivity(R.layout.activity_add_edit_transaction) {

    private lateinit var addEditTransactionPresenter: AddEditTransactionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transactionId = intent?.getStringExtra(AddEditTransactionFragment.ARGUMENT_EDIT_TRANSACTION_ID)

        val fragment = supportFragmentManager.findFragmentById(
            R.id.add_edit_transaction_content_frame)
                as AddEditTransactionFragment?
            ?: AddEditTransactionFragment.newInstance(transactionId).also {
            replaceFragmentInActivity(it, R.id.add_edit_transaction_content_frame)
        }

        addEditTransactionPresenter = AddEditTransactionPresenter(
            DataRepository(MemoryDataSource(MemoryDatabase)),
            fragment,
            transactionId
        )
    }

    companion object {
        const val REQUEST_ADD_EDIT_TRANSACTION = 1
    }

}