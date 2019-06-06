package conrado.gabriel.ragdoll.addedit.towel

import android.os.Bundle
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.activity.BaseActivity
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.data.source.memory.MemoryDataSource
import conrado.gabriel.ragdoll.data.source.memory.MemoryDatabase

class AddEditTowelActivity : BaseActivity(layoutId = R.layout.activity_add_edit_towel) {

    private lateinit var addEditTowelPresenter : AddEditTowelPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val towelId = intent?.getStringExtra(AddEditTowelFragment.ARGUMENT_EDIT_TOWEL_ID)

        val fragment = supportFragmentManager.findFragmentById(R.id.add_edit_towel_content_frame)
                as AddEditTowelFragment? ?: AddEditTowelFragment.newInstance(towelId).also {
            replaceFragmentInActivity(it, R.id.add_edit_towel_content_frame)
        }

        addEditTowelPresenter = AddEditTowelPresenter(
            DataRepository(MemoryDataSource(MemoryDatabase)),
            fragment,
            towelId
        )

    }

    companion object {
        const val REQUEST_ADD_EDIT_TOWEL = 1
    }

}