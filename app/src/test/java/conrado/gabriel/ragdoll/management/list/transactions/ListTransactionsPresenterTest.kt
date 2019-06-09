package conrado.gabriel.ragdoll.management.list.transactions

import android.app.Activity
import conrado.gabriel.ragdoll.argumentCaptor
import conrado.gabriel.ragdoll.capture
import conrado.gabriel.ragdoll.data.Transaction
import conrado.gabriel.ragdoll.data.source.AbstractDataSource
import conrado.gabriel.ragdoll.data.source.DataRepository
import conrado.gabriel.ragdoll.eq
import conrado.gabriel.ragdoll.management.addedit.transaction.AddEditTransactionActivity
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor

import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ListTransactionsPresenterTest {

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var listTransactionsView: ListTransactionsContract.View

    @Captor
    private lateinit var loadTransactionsCallbackCaptor: ArgumentCaptor<AbstractDataSource.LoadTransactionsCallback>

    private lateinit var listTransactionsPresenter: ListTransactionsPresenter

    private val sampleTransaction = Transaction("Description", 10.0, "Category")
    private val sampleTransaction2 = Transaction("Description2", 20.0, "Category2")
    private val sampleTransactions = listOf(sampleTransaction, sampleTransaction2)

    @Before
    fun setupPresenterTest() {

        MockitoAnnotations.initMocks(this)
        listTransactionsPresenter = ListTransactionsPresenter(dataRepository, listTransactionsView)

    }

    @Test
    fun bindPresenterToView() {
        verify(listTransactionsView).presenter = listTransactionsPresenter
    }

    @Test
    fun loadTransactions() {

        listTransactionsPresenter.loadTransactions()
        verify(dataRepository).getTransactions(capture(loadTransactionsCallbackCaptor))
        loadTransactionsCallbackCaptor.value.onTransactionsLoaded(sampleTransactions)

        val inOrder = inOrder(listTransactionsView)
        inOrder.verify(listTransactionsView).setLoadingIndicator(true)
        inOrder.verify(listTransactionsView).setLoadingIndicator(false)

        val showClientArgumentCaptor = argumentCaptor<List<Transaction>>()
        verify(listTransactionsView).showTransactions(capture(showClientArgumentCaptor))
        assertTrue(showClientArgumentCaptor.value.size == 2)

    }

    @Test
    fun loadNoTransactions() {

        listTransactionsPresenter.loadTransactions()
        verify(dataRepository).getTransactions(capture(loadTransactionsCallbackCaptor))
        loadTransactionsCallbackCaptor.value.onNoTransactionsLoaded()

        val inOrder = inOrder(listTransactionsView)
        inOrder.verify(listTransactionsView).setLoadingIndicator(true)
        inOrder.verify(listTransactionsView).setLoadingIndicator(false)

        verify(listTransactionsView).showNoTransactions()

    }

    @Test
    fun newTransaction() {

        listTransactionsPresenter.newTransaction()
        verify(listTransactionsView).showAddTransaction()

    }

    @Test
    fun editTransaction() {

        listTransactionsPresenter.editTransaction(sampleTransaction.id)
        verify(listTransactionsView).showEditTransaction(eq(sampleTransaction.id))

    }

    @Test
    fun removeTransactions() {

        listTransactionsPresenter.removeTransactions(sampleTransactions)
        verify(dataRepository).removeTransactions(eq(sampleTransactions))

        verify(listTransactionsView).showRemoveTransactionSuccess()
    }

    @Test
    fun transactionAddEditSuccess() {
        listTransactionsPresenter.result(AddEditTransactionActivity.REQUEST_ADD_EDIT_TRANSACTION, Activity.RESULT_OK)
        verify(listTransactionsView).showAddEditTransactionSuccess()
    }

}