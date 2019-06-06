package conrado.gabriel.ragdoll.list.towel

import android.app.Activity
import conrado.gabriel.ragdoll.addedit.towel.AddEditTowelActivity
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.data.source.AbstractDataSource
import conrado.gabriel.ragdoll.data.source.AbstractDataSource.LoadTowelsCallback

class ListTowelsPresenter(private val dataRepository: AbstractDataSource, val listTowelsView: ListTowelsContract.View)
    : ListTowelsContract.Presenter{

    init {
        listTowelsView.presenter = this
    }

    override fun start() = loadTowels()

    override fun loadTowels() {
        listTowelsView.setLoadingIndicator(true)
        dataRepository.getTowels(object : LoadTowelsCallback {

            override fun onTowelsLoaded(towels: List<Towel>) {
                listTowelsView.setLoadingIndicator(false)
                listTowelsView.showTowels(towels)
            }

            override fun onNoTowelsLoaded() {
                listTowelsView.setLoadingIndicator(false)
                listTowelsView.showNoTowels()
            }

        })
    }

    override fun newTowel() = listTowelsView.showAddTowel()

    override fun editTowel(taskId: String) = listTowelsView.showEditTowel(taskId)

    override fun removeTowels(towels: List<Towel>) {
        dataRepository.removeTowels(towels)
        loadTowels()
        listTowelsView.showRemoveSuccess()
    }

    override fun result(requestCode: Int, resultCode: Int) {
        if (requestCode == AddEditTowelActivity.REQUEST_ADD_EDIT_TOWEL
            && resultCode == Activity.RESULT_OK) {
            listTowelsView.showAddEditSuccess()
        }

    }

}