package com.digidentity.codeassignment.catalog.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digidentity.codeassignment.catalog.domain.repository.FakeCatalogRepository
import com.digidentity.codeassignment.catalog.utils.CoroutinesTestRule
import com.digidentity.codeassignment.catalog.utils.Resource
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


@ExperimentalCoroutinesApi
class GetCatalogItemsUseCaseTest{

    private lateinit var fakeRepository: FakeCatalogRepository

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule: CoroutinesTestRule = CoroutinesTestRule()

    private lateinit var getCatalogItemsUseCase: GetCatalogItemsUseCase


    @Before
    fun setup() {
        fakeRepository = FakeCatalogRepository()
        getCatalogItemsUseCase = GetCatalogItemsUseCase(
            catalogRepository = fakeRepository,
            dispatcher = testCoroutineRule.testDispatcher
        )
    }

    @Test
    fun `invoke return items list, network is available`() =
        testCoroutineRule.testDispatcher.runBlockingTest {

            val items = getCatalogItemsUseCase.invoke()
            fakeRepository.isNetworkAvailable(true)
            fakeRepository.setListSize(10)


            Truth.assertThat(items.first() is Resource.Loading).isEqualTo(true)
            Truth.assertThat(items.drop(1).first() is Resource.Success).isEqualTo(true)
            Truth.assertThat(items.drop(1).first().data).isEqualTo(fakeRepository.getLocalItemsList().reversed())
            Truth.assertThat(items.drop(1).first().data?.size)
                .isEqualTo(fakeRepository.getLocalItemsList().size)


        }

    @Test
    fun `invoke return httpException, network is available`() =
        testCoroutineRule.testDispatcher.runBlockingTest {

            fakeRepository.isNetworkAvailable(true)
            fakeRepository.hasHttpException(true)
            fakeRepository.hasSqliteException(false)
            val items = getCatalogItemsUseCase.invoke()

            Truth.assertThat(items.first() is Resource.Loading).isEqualTo(true)
            Truth.assertThat(items.drop(1).first() is Resource.Error).isEqualTo(true)
            Truth.assertThat(items.drop(1).first().message).isNotEmpty()

        }



}