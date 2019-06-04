package conrado.gabriel.ragdoll.addedit.towel

import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.data.source.AbstractDataSource

class AddEditTowelPresenter(
    private val dataRepository: AbstractDataSource,
    private val addEditTowelView: AddEditTowelContract.View,
    private val towelId: String?)
    : AddEditTowelContract.Presenter, AbstractDataSource.GetTowelCallback {

    init {
        addEditTowelView.presenter = this
    }

    override fun start() {
        if (towelId != null) populateTowel()
    }

    override fun saveTowel(type: String, amount: String, available: String) {
        val amountInt = amount.toIntOrNull()
        val availableInt = available.toIntOrNull()
        val newTowel = Towel(type).apply {
            this.amount = amountInt ?: 0
            this.available = availableInt ?: this.amount
        }
        if (newTowel.isInvalid){
            addEditTowelView.showInvalidTowelError()
        } else {
            dataRepository.saveTowel(newTowel)
            addEditTowelView.showTowelsList()
        }
    }

    override fun populateTowel() {
        if (towelId != null) {
            dataRepository.getTowel(towelId, this)
        } else {
            throw RuntimeException("Null Towel ID")
        }
    }

    override fun onTowelLoaded(towel: Towel) {

        // Update the view
        addEditTowelView.setType(towel.type)
        addEditTowelView.setAmount(towel.amount)
        addEditTowelView.setAvailable(towel.available)

    }

    override fun onNoTowelLoaded() {
        // Show empty towel error
        addEditTowelView.showInvalidTowelError()
    }

}