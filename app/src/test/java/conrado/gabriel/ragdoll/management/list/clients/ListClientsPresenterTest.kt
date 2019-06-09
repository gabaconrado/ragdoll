package conrado.gabriel.ragdoll.management.list.clients

import android.app.Activity
import conrado.gabriel.ragdoll.argumentCaptor
import conrado.gabriel.ragdoll.capture
import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.data.source.AbstractDataSource
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.eq
import conrado.gabriel.ragdoll.management.addedit.client.AddEditClientActivity
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class ListClientsPresenterTest {

    @Mock private lateinit var dataRepository: DataRepository

    @Mock private lateinit var listClientsView: ListClientsContract.View

    @Captor
    private lateinit var loadClientsCallbackCaptor: ArgumentCaptor<AbstractDataSource.LoadClientsCallback>

    private lateinit var listClientsPresenter: ListClientsPresenter

    private val sampleClient = Client("Name", "Adress", "Phone", "Email", 1.0)
    private val sampleClient2 = Client("Name2", "Adress2", "Phone2", "Email2", 2.0)
    private val clients = mutableListOf(sampleClient, sampleClient2)

    @Before
    fun setupListTowelsPresenter(){

        MockitoAnnotations.initMocks(this)

        listClientsPresenter = ListClientsPresenter(dataRepository, listClientsView)

    }

    @Test
    fun initAndBindToView(){

        listClientsPresenter = ListClientsPresenter(dataRepository, listClientsView)
        verify(listClientsView).presenter = listClientsPresenter

    }

    @Test
    fun loadClients_withClients(){

        listClientsPresenter.loadClients()

        verify(dataRepository).getClients(capture(loadClientsCallbackCaptor))
        loadClientsCallbackCaptor.value.onClientsLoaded(clients)

        val inOrder = inOrder(listClientsView)
        inOrder.verify(listClientsView).setLoadingIndicator(true)
        inOrder.verify(listClientsView).setLoadingIndicator(false)

        val showClientArgumentCaptor = argumentCaptor<List<Client>>()
        verify(listClientsView).showClients(capture(showClientArgumentCaptor))
        assertTrue(showClientArgumentCaptor.value.size == 2)

    }

    @Test
    fun loadClients_withNoClients(){

        listClientsPresenter.loadClients()

        verify(dataRepository).getClients(capture(loadClientsCallbackCaptor))
        loadClientsCallbackCaptor.value.onNoClientsLoaded()

        val inOrder = inOrder(listClientsView)
        inOrder.verify(listClientsView).setLoadingIndicator(true)
        inOrder.verify(listClientsView).setLoadingIndicator(false)

        verify(listClientsView).showNoClients()

    }

    @Test
    fun newClient(){

        listClientsPresenter.newClient()
        verify(listClientsView).showAddClient()

    }

    @Test
    fun editClient(){

        val clientId = "MOSTRAORG"
        listClientsPresenter.editClient(clientId)
        verify(listClientsView).showEditClient(eq(clientId))

    }

    @Test
    fun removeClients(){
        listClientsPresenter.removeClients(clients)
        verify(listClientsView).showRemoveClientSuccess()
    }

    @Test
    fun clientAddEditSuccessfuly(){
        listClientsPresenter.result(AddEditClientActivity.REQUEST_ADD_EDIT_CLIENT, Activity.RESULT_OK)
        verify(listClientsView).showAddEditClientSuccess()
    }

}