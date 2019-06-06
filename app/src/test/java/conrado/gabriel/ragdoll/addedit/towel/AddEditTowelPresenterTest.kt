package conrado.gabriel.ragdoll.addedit.towel

import conrado.gabriel.ragdoll.any
import conrado.gabriel.ragdoll.capture
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.data.source.AbstractDataSource
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.eq
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AddEditTowelPresenterTest{

    @Mock private lateinit var dataRepository: DataRepository
    @Mock private lateinit var addEditTowelView: AddEditTowelContract.View

    @Captor
    private lateinit var getTowelCallbackCaptor: ArgumentCaptor<AbstractDataSource.GetTowelCallback>

    private lateinit var addEditTowelPresenter: AddEditTowelPresenter

    @Before
    fun setupAddEditTowelPresenter() = MockitoAnnotations.initMocks(this)

    @Test
    fun createPresenter_bindToView(){

        addEditTowelPresenter = AddEditTowelPresenter(
            dataRepository, addEditTowelView, null)

        verify(addEditTowelView).presenter = addEditTowelPresenter

    }

    @Test
    fun createPresenter_populateTask(){

        val towelId = "1"
        addEditTowelPresenter = AddEditTowelPresenter(
            dataRepository, addEditTowelView, towelId
        )
        addEditTowelPresenter.populateTowel()
        verify(dataRepository).getTowel(eq(towelId), capture(getTowelCallbackCaptor))

    }

    @Test(expected = RuntimeException::class)
    fun createPresenter_populateNoId(){

        addEditTowelPresenter = AddEditTowelPresenter(
            dataRepository, addEditTowelView, null
        )

        addEditTowelPresenter.populateTowel()
    }

    @Test
    fun saveTowel(){

        addEditTowelPresenter = AddEditTowelPresenter(
            dataRepository, addEditTowelView, null
        )
        addEditTowelPresenter.saveTowel("Tipo 1", "1", "1")

        verify(dataRepository).saveTowel(any())
        verify(addEditTowelView).showTowelsList()

    }

    @Test
    fun testSaveEmptyTowel_invalidType(){

        addEditTowelPresenter = AddEditTowelPresenter(
            dataRepository, addEditTowelView, null
        )

        // Invalid type
        addEditTowelPresenter.saveTowel("", "10", "0")
        verify(addEditTowelView).showInvalidTowelError()

    }

    @Test
    fun testSaveEmptyTowel_invalidAmount(){

        addEditTowelPresenter = AddEditTowelPresenter(
            dataRepository, addEditTowelView, null
        )

        // Invalid type
        addEditTowelPresenter.saveTowel("123", "0", "0")
        verify(addEditTowelView).showInvalidTowelError()

    }

    @Test
    fun testSaveEmptyTowel_invalidNumberAmount(){

        addEditTowelPresenter = AddEditTowelPresenter(
            dataRepository, addEditTowelView, null
        )

        // Invalid type
        addEditTowelPresenter.saveTowel("123", "asd", "0")
        verify(addEditTowelView).showInvalidTowelError()

    }

    @Test
    fun testSaveEmptyTowel_invalidNumberAvailable(){

        addEditTowelPresenter = AddEditTowelPresenter(
            dataRepository, addEditTowelView, null
        )

        // Invalid type
        addEditTowelPresenter.saveTowel("123", "123", "asd")
        verify(addEditTowelView).showInvalidTowelError()

    }

    @Test
    fun towelLoaded(){

        val towel = Towel("Limpinha").apply {
            amount = 10
            available = 10
        }

        addEditTowelPresenter = AddEditTowelPresenter(
            dataRepository, addEditTowelView, towel.id
        ).apply { populateTowel() }

        verify(dataRepository).getTowel(eq(towel.id), capture(getTowelCallbackCaptor))
        getTowelCallbackCaptor.value.onTowelLoaded(towel)

        verify(addEditTowelView).setType(towel.type)
        verify(addEditTowelView).setAmount(10)
        verify(addEditTowelView).setAvailable(10)

    }

    @Test
    fun noTowelLoaded(){

        val invalidId = "123"

        // Get invalid towel
        addEditTowelPresenter = AddEditTowelPresenter(
            dataRepository, addEditTowelView, invalidId
        ).apply { populateTowel() }

        // Mock the callback and verify the response
        verify(dataRepository).getTowel(any(), capture(getTowelCallbackCaptor))
        getTowelCallbackCaptor.value.onNoTowelLoaded()

        verify(addEditTowelView).showInvalidTowelError()
        verify(addEditTowelView).showTowelsList()

    }

}