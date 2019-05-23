package conrado.gabriel.ragdoll.list.towel

import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.data.source.AbstractDataSource
import conrado.gabriel.ragdoll.data.source.AbstractDataSource.LoadTowelsCallback

class ListTowelsPresenter(val dataRepository: AbstractDataSource, val listTowelsView: ListTowelsContract.View)
    : ListTowelsContract.Presenter{

    private var firstLoad = true

    init {
        listTowelsView.presenter = this
    }

    override fun start() {
        loadTowels(false)
    }

    override fun loadTowels(refresh: Boolean) {
        loadTowels(refresh || firstLoad, true)
        firstLoad = false
    }

    private fun loadTowels(refresh: Boolean, showLoadUI: Boolean){
        if (showLoadUI)
            listTowelsView.setLoadingIndicator(true)
        if (refresh){
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
    }

    override fun newTowel() {
        listTowelsView.showAddTowel()
    }

}