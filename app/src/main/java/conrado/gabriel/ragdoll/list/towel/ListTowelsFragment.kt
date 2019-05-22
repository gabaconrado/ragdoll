package conrado.gabriel.ragdoll.list.towel

import androidx.fragment.app.Fragment
import conrado.gabriel.ragdoll.data.Towel

class ListTowelsFragment : Fragment(), ListTowelsContract.View{

    override lateinit var presenter: ListTowelsContract.Presenter

    override fun setLoadingIndicator(active: Boolean) {

    }

    override fun showTowels(towel: List<Towel>) {

    }

    override fun showAddTowel() {

    }

    override fun showNoTowels() {

    }

}