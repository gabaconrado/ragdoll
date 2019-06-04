package conrado.gabriel.ragdoll.list.towel

import conrado.gabriel.ragdoll.BasePresenter
import conrado.gabriel.ragdoll.BaseView
import conrado.gabriel.ragdoll.data.Towel

/**
 * The specification of the interations between the Presenter and the View for the List Towels feature
 */

interface ListTowelsContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showTowels(towel: List<Towel>)

        fun showNoTowels()

        fun showAddTowel()

        fun showAddEditSuccess()

    }

    interface Presenter : BasePresenter {

        fun loadTowels(refresh: Boolean)

        fun newTowel()

        fun result(requestCode: Int, resultCode: Int)

    }

}