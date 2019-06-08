package conrado.gabriel.ragdoll.data.source

import conrado.gabriel.ragdoll.any
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

    private lateinit var dataRepository: DataRepository

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
    fun loadAllTasksFromDataSource() {
        dataRepository.getTowels(loadTowelsCallback)
        verify(dataSource).getTowels(any())
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

}