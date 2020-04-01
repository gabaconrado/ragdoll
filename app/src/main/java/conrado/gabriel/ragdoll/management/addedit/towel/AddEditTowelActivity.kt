package conrado.gabriel.ragdoll.management.addedit.towel

import android.os.Bundle
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.BaseActivity
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.data.source.room.RagdollDatabase
import conrado.gabriel.ragdoll.data.source.room.RagdollDataSource

class AddEditTowelActivity : BaseActivity(layoutId = R.layout.activity_add_edit_towel) {

    private lateinit var addEditTowelPresenter : AddEditTowelPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val towelId = intent?.getStringExtra(AddEditTowelFragment.ARGUMENT_EDIT_TOWEL_ID)

        val fragment = supportFragmentManager.findFragmentById(R.id.add_edit_towel_content_frame)
                as AddEditTowelFragment? ?: AddEditTowelFragment.newInstance(
            towelId
        ).also {
            replaceFragmentInActivity(it, R.id.add_edit_towel_content_frame)
        }

        addEditTowelPresenter = AddEditTowelPresenter(
            DataRepository(RagdollDataSource(RagdollDatabase.getInstance(applicationContext))),
            fragment,
            towelId
        )

    }

    companion object {
        const val REQUEST_ADD_EDIT_TOWEL = 1
    }

}