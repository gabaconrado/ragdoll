package conrado.gabriel.ragdoll.management.addedit.client

import conrado.gabriel.ragdoll.capture
import conrado.gabriel.ragdoll.data.Client
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

class AddEditClientPresenterTest {

    @Mock private lateinit var dataRepository: DataRepository
    @Mock private lateinit var addEditClientView: AddEditClientContract.View

    @Captor
    private lateinit var getClientCallbackCaptor: ArgumentCaptor<AbstractDataSource.GetClientCallback>

    private lateinit var addEditClientPresenter: AddEditClientPresenter

    private val sampleClient = Client("Name", "Adress", "Phone", "Email", 1.0)

    @Before
    fun setupAddEditClientPresenter() = MockitoAnnotations.initMocks(this)

    @Test
    fun createPresenter_bindToView() {

        addEditClientPresenter = AddEditClientPresenter(
            dataRepository, addEditClientView, null
        )

        verify(addEditClientView).presenter = addEditClientPresenter

    }

    @Test
    fun saveClient() {

        addEditClientPresenter = AddEditClientPresenter(
            dataRepository, addEditClientView, null
        )

        addEditClientPresenter.saveOrEditClient(sampleClient)
        verify(dataRepository).saveClient(sampleClient)
        verify(addEditClientView).showClientList()

    }

    @Test
    fun editClient() {

        addEditClientPresenter = AddEditClientPresenter(
            dataRepository, addEditClientView, sampleClient.id
        )

        addEditClientPresenter.saveOrEditClient(sampleClient)
        verify(dataRepository).editClient(sampleClient)
        verify(addEditClientView).showClientList()

    }


     // I will not develop test for each invalid field in Client because there are a lot of fields (hehe)
     // This should not be a problem though, as the isInvalid method is centralized
    @Test
    fun saveEditInvalidClient() {

        addEditClientPresenter = AddEditClientPresenter(
            dataRepository, addEditClientView, null
        )

        sampleClient.name = ""
        addEditClientPresenter.saveOrEditClient(sampleClient)
        verify(addEditClientView).showInvalidClientError()

    }

    @Test
    fun populateClient() {

        addEditClientPresenter = AddEditClientPresenter(
            dataRepository, addEditClientView, sampleClient.id
        )

        addEditClientPresenter.populateClient()
        verify(dataRepository).getClient(eq(sampleClient.id), capture(getClientCallbackCaptor))
        getClientCallbackCaptor.value.onClientLoaded(sampleClient)

        verify(addEditClientView).setName(sampleClient.name)
        verify(addEditClientView).setAddress(sampleClient.address)
        verify(addEditClientView).setPhone(sampleClient.phone)
        verify(addEditClientView).setEmail(sampleClient.email)
        verify(addEditClientView).setTowelPrice(sampleClient.towelPrice)
        verify(addEditClientView).setBalance(sampleClient.balance)
        verify(addEditClientView).setTowels(sampleClient.towels)

    }

    @Test
    fun populateInvalidClient() {

        addEditClientPresenter = AddEditClientPresenter(
            dataRepository, addEditClientView, "KDORG"
        )

        addEditClientPresenter.populateClient()
        verify(dataRepository).getClient(eq("KDORG"), capture(getClientCallbackCaptor))
        getClientCallbackCaptor.value.onNoClientLoaded()

        verify(addEditClientView).showInvalidClientError()
        verify(addEditClientView).showClientList()

    }

}