package conrado.gabriel.ragdoll.management.addedit.client

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.Client
import kotlinx.android.synthetic.main.fragment_add_edit_client.*

class AddEditClientFragment : Fragment(), AddEditClientContract.View {

    override lateinit var presenter: AddEditClientContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = inflater.inflate(R.layout.fragment_add_edit_client, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<Button>(R.id.bt_confirm_add_edit_client)?.apply {
            setOnClickListener {
                handleSaveClientClick()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showClientList() {
        activity?.apply {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun showInvalidClientError() {
        val parentActivity = activity as AddEditClientActivity?
        parentActivity?.showMessage(getString(R.string.invalid_client_data))
    }

    override fun setName(name: String) = et_client_name.setText(name)

    override fun setAddress(address: String) = et_client_address.setText(address)

    override fun setPhone(phone: String) = et_client_phone.setText(phone)

    override fun setEmail(email: String) = et_client_email.setText(email)

    override fun setTowelPrice(towelPrice: Double) = et_client_towel_price.setText(towelPrice.toString())

    override fun setBalance(balance: Double) = et_client_balance.setText(balance.toString())

    private fun handleSaveClientClick() {

        val towelPriceDouble = et_client_towel_price.text.toString().toDoubleOrNull()
        val clientBalanceDouble = et_client_balance.text.toString().toDoubleOrNull()
        val client = Client(
            name = et_client_name.text.toString(),
            address = et_client_address.text.toString(),
            phone = et_client_phone.text.toString(),
            email = et_client_email.text.toString(),
            towelPrice = towelPriceDouble ?: 0.0
        ).apply {
            balance = clientBalanceDouble ?: 0.0
            // Make client invalid if any number conversion went wrong
            if (towelPriceDouble == null || clientBalanceDouble == null)
                name = ""
        }

        presenter.saveOrEditClient(client)
    }

    companion object {

        const val ARGUMENT_EDIT_CLIENT_ID = "CLIENT_ID"

        fun newInstance(towelId: String?) = AddEditClientFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_EDIT_CLIENT_ID, towelId)
            }
        }

    }

}