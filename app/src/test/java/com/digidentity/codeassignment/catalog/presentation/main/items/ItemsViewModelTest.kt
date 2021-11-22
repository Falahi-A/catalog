package com.digidentity.codeassignment.catalog.presentation.main.items

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digidentity.codeassignment.catalog.domain.model.NewItem
import com.digidentity.codeassignment.catalog.domain.repository.FakeCatalogRepository
import com.digidentity.codeassignment.catalog.domain.usecase.AddCatalogNewItemUseCase
import com.digidentity.codeassignment.catalog.domain.usecase.GetCatalogItemsUseCase
import com.digidentity.codeassignment.catalog.utils.CoroutinesTestRule
import com.digidentity.codeassignment.catalog.utils.Event
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


@ExperimentalCoroutinesApi
class ItemsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule: CoroutinesTestRule = CoroutinesTestRule()

    private lateinit var fakeGetCatalogItemsUseCase: GetCatalogItemsUseCase
    private lateinit var fakeAddCatalogNewItemUseCase: AddCatalogNewItemUseCase
    private lateinit var fakeCatalogRepository: FakeCatalogRepository
    private lateinit var itemsListViewModel: ItemsViewModel


    @Before
    fun setUp() {
        fakeCatalogRepository = FakeCatalogRepository()
        fakeGetCatalogItemsUseCase =
            GetCatalogItemsUseCase(fakeCatalogRepository, testCoroutineRule.testDispatcher)
        fakeAddCatalogNewItemUseCase =
            AddCatalogNewItemUseCase(fakeCatalogRepository, testCoroutineRule.testDispatcher)
        itemsListViewModel =
            ItemsViewModel(fakeGetCatalogItemsUseCase, fakeAddCatalogNewItemUseCase)
    }

    @Test
    fun `get catalog items return items list, internet is available`() = runBlockingTest {

        fakeCatalogRepository.isNetworkAvailable(true)
        itemsListViewModel.getItems()
        Truth.assertThat(itemsListViewModel.items.value is ItemsViewState).isTrue()
        Truth.assertThat(itemsListViewModel.items.value?.errorMessage).isEmpty()
        Truth.assertThat(itemsListViewModel.items.value?.data)
            .isEqualTo(fakeCatalogRepository.getLocalItemsList())

    }

    @Test
    fun `get catalog items return items list, internet is not available`() = runBlockingTest {

        fakeCatalogRepository.isNetworkAvailable(false)
        itemsListViewModel.getItems()
        Truth.assertThat(itemsListViewModel.items.value is ItemsViewState).isTrue()
        Truth.assertThat(itemsListViewModel.items.value?.errorMessage).isNotEmpty()
    }


    @Test
    fun `get catalog items return error, internet is available`() = runBlockingTest {

        fakeCatalogRepository.isNetworkAvailable(true)
        fakeCatalogRepository.hasHttpException(true)
        itemsListViewModel.getItems()
        Truth.assertThat(itemsListViewModel.items.value is ItemsViewState).isTrue()
        Truth.assertThat(itemsListViewModel.items.value?.errorMessage).isNotEmpty()
        Truth.assertThat(itemsListViewModel.items.value?.data)
            .isEqualTo(fakeCatalogRepository.getLocalItemsList())

    }


    @Test
    fun `add new catalog item return new item`() {
        fakeCatalogRepository.isNetworkAvailable(true)
        itemsListViewModel.addNewItem(NewItem("", 0.0, ""))
        Truth.assertThat(itemsListViewModel.newItem.value is Event<NewItemViewState>).isTrue()
        Truth.assertThat(itemsListViewModel.newItem.value?.getContentIfNotHandled()?.newItem).isNotNull()
    }

    @Test
    fun `add new catalog item return error`() {
        fakeCatalogRepository.isNetworkAvailable(true)
        fakeCatalogRepository.hasHttpException(true)
        itemsListViewModel.addNewItem(NewItem("", 0.0, ""))
        Truth.assertThat(itemsListViewModel.newItem.value is Event<NewItemViewState>).isTrue()
        Truth.assertThat(itemsListViewModel.newItem.value?.getContentIfNotHandled()?.newItem).isNull()

    }

}