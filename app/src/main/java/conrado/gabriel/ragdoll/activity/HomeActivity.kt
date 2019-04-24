package conrado.gabriel.ragdoll.activity

import android.os.Bundle
import android.widget.Toast
import conrado.gabriel.ragdoll.R

import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(layoutId = R.layout.activity_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        button.setOnClickListener { bt_click() }

    }

    private fun bt_click() {
        textView.setText("SEJA UM VENCEDOR")
        Toast.makeText(this, "SEJA UM VENCEDOR", Toast.LENGTH_LONG).show()
    }

}
