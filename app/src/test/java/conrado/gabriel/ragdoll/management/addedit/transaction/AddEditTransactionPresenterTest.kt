package conrado.gabriel.ragdoll.management.addedit.transaction

import conrado.gabriel.ragdoll.capture
import conrado.gabriel.ragdoll.data.Transaction
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

class AddEditTransactionPresenterTest {

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var addEditTransactionView: AddEditTransactionContract.View

    @Captor
    private lateinit var getTransactionCallbackCaptor: ArgumentCaptor<AbstractDataSource.GetTransactionCallback>

    private lateinit var addEditTransactionPresenter: AddEditTransactionPresenter

    private val sampleTransaction = Transaction("Description", 0.0, "Category")

    @Before
    fun setupAddEditTransactionPresenter() = MockitoAnnotations.initMocks(this)

    @Test
    fun bindPresenterToView(){

        addEditTransactionPresenter = AddEditTransactionPresenter(
            dataRepository, addEditTransactionView, null
        )

        verify(addEditTransactionView).presenter = addEditTransactionPresenter

    }

    @Test
    fun saveTransaction() {

        addEditTransactionPresenter = AddEditTransactionPresenter(
            dataRepository, addEditTransactionView, null
        )

        addEditTransactionPresenter.saveOrEditTransaction(sampleTransaction)
        verify(dataRepository).saveTransaction(sampleTransaction)
        verify(addEditTransactionView).showTransactionList()

    }

    @Test
    fun editTransaction() {

        addEditTransactionPresenter = AddEditTransactionPresenter(
            dataRepository, addEditTransactionView, sampleTransaction.id
        )

        addEditTransactionPresenter.saveOrEditTransaction(sampleTransaction)
        verify(dataRepository).editTransaction(sampleTransaction)
        verify(addEditTransactionView).showTransactionList()

    }

    @Test
    fun saveEditInvalidTransaction() {

        addEditTransactionPresenter = AddEditTransactionPresenter(
            dataRepository, addEditTransactionView, null
        )

        sampleTransaction.description = ""
        addEditTransactionPresenter.saveOrEditTransaction(sampleTransaction)
        verify(addEditTransactionView).showInvalidTransactionError()
    }

    @Test
    fun populateTransaction() {

        addEditTransactionPresenter = AddEditTransactionPresenter(
            dataRepository, addEditTransactionView, sampleTransaction.id
        )

        addEditTransactionPresenter.populateTransaction()
        verify(dataRepository).getTransaction(eq(sampleTransaction.id), capture(getTransactionCallbackCaptor))
        getTransactionCallbackCaptor.value.onTransactionLoaded(sampleTransaction)

        verify(addEditTransactionView).setDescription(sampleTransaction.description)
        verify(addEditTransactionView).setCost(sampleTransaction.cost)
        verify(addEditTransactionView).setCategory(sampleTransaction.category)

    }

    @Test
    fun populateInvalidTransaction() {

        addEditTransactionPresenter = AddEditTransactionPresenter(
            dataRepository, addEditTransactionView, sampleTransaction.id
        )

        addEditTransactionPresenter.populateTransaction()
        verify(dataRepository).getTransaction(eq(sampleTransaction.id), capture(getTransactionCallbackCaptor))
        getTransactionCallbackCaptor.value.onNoTransactionLoaded()

        verify(addEditTransactionView).showInvalidTransactionError()
        verify(addEditTransactionView).showTransactionList()

    }

}