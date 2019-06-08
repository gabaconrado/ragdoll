package conrado.gabriel.ragdoll.management.addedit.towel

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

    override fun saveOrEditTowel(type: String, amount: String, available: String) {
        val amountInt = amount.toIntOrNull()
        val availableInt = available.toIntOrNull()
        val newTowel = Towel(type).apply {
            this.amount = amountInt ?: 0
            this.available = availableInt ?: 0
            if (availableInt == null)
                this.type = ""
        }
        if (newTowel.isInvalid){
            addEditTowelView.showInvalidTowelError()
        } else {
            // Edit or save new towel depending on towel ID
            if (towelId != null){
                newTowel.id = towelId
                dataRepository.editTowel(newTowel)
            } else {
                dataRepository.saveTowel(newTowel)
            }
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
        addEditTowelView.showTowelsList()
    }


}