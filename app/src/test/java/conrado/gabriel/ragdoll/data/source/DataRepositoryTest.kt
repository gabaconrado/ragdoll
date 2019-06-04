package conrado.gabriel.ragdoll.data.source

import conrado.gabriel.ragdoll.any
import conrado.gabriel.ragdoll.data.Towel
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

}