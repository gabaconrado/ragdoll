package conrado.gabriel.ragdoll.management.addedit.transaction

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.data.Transaction
import kotlinx.android.synthetic.main.fragment_add_edit_transaction.*

class AddEditTransactionFragment : Fragment(), AddEditTransactionContract.View {

    override lateinit var presenter: AddEditTransactionContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = inflater.inflate(R.layout.fragment_add_edit_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<Button>(R.id.bt_confirm_add_edit_transaction)?.apply {
            setOnClickListener { handleSaveTransactionClick() }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showTransactionList() {
        activity?.apply {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun showInvalidTransactionError() {
        val parentActivity = activity as AddEditTransactionActivity?
        parentActivity?.showMessage(getString(R.string.invalid_transaction_data))
    }

    override fun setDescription(description: String) = et_transaction_description.setText(description)

    override fun setCost(cost: Double) = et_transaction_cost.setText(cost.toString())

    override fun setCategory(category: String) = et_transaction_category.setText(category)

    private fun handleSaveTransactionClick() {

        val transactionCostDouble = et_transaction_cost.text.toString().toDoubleOrNull()
        val transaction = Transaction(
            description = et_transaction_description.text.toString(),
            cost = transactionCostDouble ?: 0.0,
            category = et_transaction_category.text.toString()
        ).apply {
            // Make transaction invalid if any number conversion went wrong
            if (transactionCostDouble == null)
                description = ""
        }

        presenter.saveOrEditTransaction(transaction)
    }

    companion object {

        const val ARGUMENT_EDIT_TRANSACTION_ID = "TRANSACTION_ID"

        fun newInstance(towelId: String?) = AddEditTransactionFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_EDIT_TRANSACTION_ID, towelId)
            }
        }

    }

}