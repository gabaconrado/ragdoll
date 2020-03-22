package conrado.gabriel.ragdoll.management

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import conrado.gabriel.ragdoll.BaseActivity
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.data.source.memory.MemoryDataSource
import conrado.gabriel.ragdoll.data.source.memory.MemoryDatabase
import conrado.gabriel.ragdoll.management.list.clients.ListClientsFragment
import conrado.gabriel.ragdoll.management.list.clients.ListClientsPresenter
import conrado.gabriel.ragdoll.management.list.towel.ListTowelsFragment
import conrado.gabriel.ragdoll.management.list.towel.ListTowelsPresenter
import conrado.gabriel.ragdoll.management.list.transactions.ListTransactionsFragment
import conrado.gabriel.ragdoll.management.list.transactions.ListTransactionsPresenter
import kotlinx.android.synthetic.main.activity_management.*


class ManagementActivity : BaseActivity(layoutId = R.layout.activity_management),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var listTowelsPresenter: ListTowelsPresenter
    private lateinit var listTowelsFragment: ListTowelsFragment

    private lateinit var listClientsPresenter: ListClientsPresenter
    private lateinit var listClientsFragment: ListClientsFragment

    private lateinit var listTransactionsPresenter: ListTransactionsPresenter
    private lateinit var listTransactionsFragment: ListTransactionsFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        initFragments()

        initBottomNav()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val fragment: Fragment = when(item.itemId) {
            R.id.nav_towels -> {
                listTowelsFragment
            }
            R.id.nav_clients -> {
                listClientsFragment
            }
            R.id.nav_transactions -> {
                listTransactionsFragment
            }
            else -> {
                listTowelsFragment
            }
        }

        replaceFragmentInActivity(fragment, R.id.management_content_frame)

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

        // Clients
        listClientsFragment= ListClientsFragment.newInstance()
        listClientsPresenter = ListClientsPresenter(
            DataRepository(
                MemoryDataSource(MemoryDatabase)
            ), listClientsFragment
        )

        // Transactions
        listTransactionsFragment = ListTransactionsFragment.newInstance()
        listTransactionsPresenter = ListTransactionsPresenter(
            DataRepository(
                MemoryDataSource(MemoryDatabase)
            ), listTransactionsFragment
        )
    }

}
