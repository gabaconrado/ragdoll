package conrado.gabriel.ragdoll.list.towel

import conrado.gabriel.ragdoll.argumentCaptor
import conrado.gabriel.ragdoll.capture
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.data.source.AbstractDataSource
import conrado.gabriel.ragdoll.data.source.DataRepository
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ListTowelsPresenterTest {

    @Mock private lateinit var dataRepository: DataRepository

    @Mock private lateinit var listTowelsView: ListTowelsContract.View

    @Captor private lateinit var loadTowelsCallbackCaptor: ArgumentCaptor<AbstractDataSource.LoadTowelsCallback>

    private lateinit var listTowelsPresenter: ListTowelsPresenter

    private lateinit var towels: MutableList<Towel>

    @Before
    fun setupListTowelsPresenter(){

        MockitoAnnotations.initMocks(this)

        listTowelsPresenter = ListTowelsPresenter(dataRepository, listTowelsView)

        towels = mutableListOf(Towel("Fofinha"), Towel("Branquinha"), Towel("Limpinha"))

    }

    /**
     * Check if a presenter is created and bind to the view successfully
     */
    @Test
    fun createPresenter_bindToView(){

        listTowelsPresenter = ListTowelsPresenter(dataRepository, listTowelsView)

        verify(listTowelsView).presenter = listTowelsPresenter

    }

    /**
     * Check if the towels are loaded correctly from the database into the view
     */
    @Test
    fun loadTowelsFromDataSourceIntoView(){

        listTowelsPresenter.loadTowels()

        verify(dataRepository).getTowels(capture(loadTowelsCallbackCaptor))
        loadTowelsCallbackCaptor.value.onTowelsLoaded(towels)

        val inOrder = inOrder(listTowelsView)
        inOrder.verify(listTowelsView).setLoadingIndicator(true)
        inOrder.verify(listTowelsView).setLoadingIndicator(false)

        val showTowelArgumentCaptor = argumentCaptor<List<Towel>>()
        verify(listTowelsView).showTowels(capture(showTowelArgumentCaptor))
        assertTrue(showTowelArgumentCaptor.value.size == 3)

    }

    /**
     * Check if loading empty list will show on screen
     */
    @Test
    fun loadTowelsEmpty_showMessage(){

        listTowelsPresenter.loadTowels()

        verify(dataRepository).getTowels(capture(loadTowelsCallbackCaptor))
        loadTowelsCallbackCaptor.value.onNoTowelsLoaded()

        verify(listTowelsView).showNoTowels()

    }

    /**
     * Check if the add towel is called in the view
     */
    @Test
    fun addTowel(){

        listTowelsPresenter.newTowel()

        verify(listTowelsView).showAddTowel()

    }

    @Test
    fun removeTowel(){

        val towels = mutableListOf(Towel("Limpinha"), Towel("Fofinha"))
        listTowelsPresenter.removeTowels(towels)

        verify(listTowelsView).showRemoveSuccess()

    }

}