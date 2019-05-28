package conrado.gabriel.ragdoll.activity

import android.os.Bundle
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.data.source.memory.MemoryDataSource
import conrado.gabriel.ragdoll.data.source.memory.MemoryDatabase
import conrado.gabriel.ragdoll.list.towel.ListTowelsFragment
import conrado.gabriel.ragdoll.list.towel.ListTowelsPresenter

class HomeActivity : BaseActivity(layoutId = R.layout.activity_home){

    private lateinit var listTowelsPresenter: ListTowelsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val fragment = supportFragmentManager.findFragmentById(R.id.home_content_frame)
                as ListTowelsFragment? ?: ListTowelsFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.home_content_frame)
        }

        listTowelsPresenter = ListTowelsPresenter(DataRepository(MemoryDataSource(MemoryDatabase)), fragment)

    }

}
