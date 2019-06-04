package conrado.gabriel.ragdoll.addedit.towel

import conrado.gabriel.ragdoll.BasePresenter
import conrado.gabriel.ragdoll.BaseView

interface AddEditTowelContract {

    interface View: BaseView<Presenter>{

        fun showTowelsList()

        fun showInvalidTowelError()

        fun setType(type: String)

        fun setAmount(amout: Int)

        fun setAvailable(available: Int)

    }

    interface Presenter: BasePresenter {

        fun saveTowel(type:String, amount: Int, available: Int)

        fun populateTowel()

    }

}