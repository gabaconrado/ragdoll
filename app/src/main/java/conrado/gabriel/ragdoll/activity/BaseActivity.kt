package conrado.gabriel.ragdoll.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import conrado.gabriel.ragdoll.R
import kotlinx.android.synthetic.main.app_toolbar.*

abstract class BaseActivity(private val layoutId: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        setSupportActionBar(app_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.action_activity_home -> {
            startActivity(Intent(this, HomeActivity::class.java))
            true
        }
        R.id.action_activity_finances -> {
            startActivity(Intent(this, FinancesActivity::class.java))
            true
        }
        R.id.action_activity_management -> {
            startActivity(Intent(this, DataManagementActivity::class.java))
            true
        }
        R.id.action_activity_sale -> {
            startActivity(Intent(this, SaleActivity::class.java))
            true
        }
        R.id.action_activity_explore -> {
            startActivity(Intent(this, DataExploreActivity::class.java))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}