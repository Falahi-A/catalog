package com.digidentity.codeassignment.catalog.domain.usecase

import com.digidentity.codeassignment.catalog.data.network.model.toItem
import com.digidentity.codeassignment.catalog.domain.model.Item
import com.digidentity.codeassignment.catalog.domain.model.NewItem
import com.digidentity.codeassignment.catalog.domain.model.toJson
import com.digidentity.codeassignment.catalog.domain.repository.CatalogRepository
import com.digidentity.codeassignment.catalog.utils.Constants
import com.digidentity.codeassignment.catalog.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class AddCatalogNewItemUseCase @Inject constructor(
    private val repository: CatalogRepository,
    @Named(Constants.IO_DISPATCHER) private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(item: NewItem) = flow<Resource<Item>> {
        try {
            emit(Resource.Loading())
            val newItem = repository.addNewItem(item.toJson())
            emit(Resource.Success(newItem.toItem()))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))

        }
    }.flowOn(dispatcher)


}