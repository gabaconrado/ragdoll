package conrado.gabriel.ragdoll.management.addedit.towel

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import conrado.gabriel.ragdoll.R
import kotlinx.android.synthetic.main.fragment_add_edit_towel.*

class AddEditTowelFragment : Fragment(), AddEditTowelContract.View {

    override lateinit var presenter: AddEditTowelContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = inflater.inflate(R.layout.fragment_add_edit_towel, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.findViewById<Button>(R.id.bt_confirm_add_edit_towel)?.apply {
            setOnClickListener {
                presenter.saveOrEditTowel(
                    et_towel_type.text.toString(),
                    et_towel_amount.text.toString(),
                    et_towel_available.text.toString()
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showTowelsList() {
        activity?.apply {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun showInvalidTowelError() {
        val parentActivity = activity as AddEditTowelActivity?
        parentActivity?.showMessage(getString(R.string.invalid_towel_data))
    }

    override fun setType(type: String) = et_towel_type.setText(type)

    override fun setAmount(amount: Int) = et_towel_amount.setText(amount.toString())

    override fun setAvailable(available: Int) = et_towel_available.setText(available.toString())

    companion object {

        const val ARGUMENT_EDIT_TOWEL_ID = "TOWEL_ID"

        fun newInstance(towelId: String?) = AddEditTowelFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_EDIT_TOWEL_ID, towelId)
            }
        }

    }

}