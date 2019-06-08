package conrado.gabriel.ragdoll.data.source

import conrado.gabriel.ragdoll.any
import conrado.gabriel.ragdoll.data.Client
import conrado.gabriel.ragdoll.data.Towel
import conrado.gabriel.ragdoll.eq
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DataRepositoryTest {

    @Mock private lateinit var dataSource: AbstractDataSource

    @Mock private lateinit var loadTowelsCallback: AbstractDataSource.LoadTowelsCallback
    @Mock private lateinit var getTowelCallback: AbstractDataSource.GetTowelCallback

    @Mock private lateinit var loadClientsCallback: AbstractDataSource.LoadClientsCallback
    @Mock private lateinit var getClientCallback: AbstractDataSource.GetClientCallback

    private lateinit var dataRepository: DataRepository

    private val sampleClient = Client("Name", "Adress", "Phone", "Email", 1.0)

    @Before
    fun setupDataRepository() {

        MockitoAnnotations.initMocks(this)

        dataRepository = DataRepository.getInstance(dataSource)

    }

    @After
    fun destroyDataRepository() {
        DataRepository.destroyInstance()
    }

    @Test
    fun getTowels() {
        dataRepository.getTowels(loadTowelsCallback)
        verify(dataSource).getTowels(any())
    }

    @Test
    fun getTowel() {
        val towelId = "id"
        dataRepository.getTowel(towelId, getTowelCallback)
        verify(dataSource).getTowel(eq(towelId), eq(getTowelCallback))
    }

    @Test
    fun saveTowel(){
        val towel = Towel("Limpinha")
        dataRepository.saveTowel(towel)
        verify(dataSource).saveTowel(towel)
    }

    @Test
    fun removeTowel(){
        val towel = Towel("Limpinha")
        dataRepository.saveTowel(towel)

        dataRepository.removeTowel(towel.id)
        verify(dataSource).removeTowel(eq(towel.id))

    }

    @Test
    fun removeTowels(){

        val towel1 = Towel("Limpinha")
        val towel2 = Towel("Fofinha")
        dataRepository.saveTowel(towel1)
        dataRepository.saveTowel(towel2)

        val towels = mutableListOf(towel1, towel2)

        dataRepository.removeTowels(towels)
        verify(dataSource).removeTowels(eq(towels))

    }

    @Test
    fun editTowel(){

        val towel = Towel("Limpinha")
        dataRepository.saveTowel(towel)

        val towelEdited = Towel().apply {
            id = towel.id
            type = "Fofinha"
            amount = 0
            available = 0
        }

        dataRepository.editTowel(towelEdited)
        verify(dataSource).editTowel(eq(towelEdited))

    }

    @Test
    fun getClients() {
        dataRepository.getClients(loadClientsCallback)
        verify(dataSource).getClients(eq(loadClientsCallback))
    }

    @Test
    fun saveClient() {
        dataRepository.saveClient(sampleClient)
        verify(dataSource).saveClient(eq(sampleClient))
    }

    @Test
    fun getClient() {
        dataRepository.getClient(sampleClient.id, getClientCallback)
        verify(dataSource).getClient(eq(sampleClient.id), eq(getClientCallback))
    }

    @Test
    fun removeClient() {
        dataRepository.removeClient(sampleClient.id)
        verify(dataSource).removeClient(eq(sampleClient.id))
    }

    @Test
    fun removeClients() {
        val clientList = listOf(sampleClient)     // Nice TV Show BTW
        dataRepository.removeClients(clientList)
        verify(dataSource).removeClients(eq(clientList))
    }

    @Test
    fun editClient() {
        dataRepository.editClient(sampleClient)
        verify(dataSource).editClient(eq(sampleClient))
    }

}