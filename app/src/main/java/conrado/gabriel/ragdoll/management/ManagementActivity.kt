package conrado.gabriel.ragdoll.management

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import conrado.gabriel.ragdoll.BaseActivity
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.data.source.memory.MemoryDataSource
import conrado.gabriel.ragdoll.data.source.memory.MemoryDatabase
import conrado.gabriel.ragdoll.management.list.towel.ListTowelsFragment
import conrado.gabriel.ragdoll.management.list.towel.ListTowelsPresenter
import kotlinx.android.synthetic.main.activity_management.*


class ManagementActivity : BaseActivity(layoutId = R.layout.activity_management),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var listTowelsPresenter: ListTowelsPresenter
    private lateinit var listTowelsFragment: ListTowelsFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        initFragments()

        initBottomNav()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.nav_towels -> {
                supportFragmentManager
                    .findFragmentById(R.id.management_content_frame) as
                        ListTowelsFragment? ?: ListTowelsFragment.newInstance().also {
                    replaceFragmentInActivity(it, R.id.management_content_frame)
                }
            }
            else -> {

            }
        }



        return true
    }

    private fun initBottomNav() {
        bnv_management_tabs.setOnNavigationItemSelectedListener(this)
        replaceFragmentInActivity(listTowelsFragment, R.id.management_content_frame)
    }

    private fun initFragments(){

        // Towels
        listTowelsFragment = ListTowelsFragment.newInstance()
        listTowelsPresenter = ListTowelsPresenter(
            DataRepository(
                MemoryDataSource(MemoryDatabase)
            ), listTowelsFragment
        )

        // TODO: Clients
        // TODO: Finances
    }

}
