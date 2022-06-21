package com.assigment.taskcurrencyapp.domain.repositories

import com.assigment.taskcurrencyapp.domain.models.ApiBaseResponse
import com.assigment.taskcurrencyapp.data.server.common.ApiResource
import com.assigment.taskcurrencyapp.data.server.common.Resource
import com.assigment.taskcurrencyapp.ui.utils.Constants.NETWORK_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    protected suspend fun <T : ApiBaseResponse> callService(call: suspend () -> T): ApiResource<T> {

        return withContext(Dispatchers.IO) {

            try {
                val result = call.invoke()

                when (result.succes) {
                    true ->
                        ApiResource.Success(result)
                    else ->
                        ApiResource.Failure(errorCode = result.error?.code, result.error?.type)
                }

            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException ->
                        ApiResource.NetWorkError(
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    else ->
                        ApiResource.NetWorkError(null, null)
                }
            }
        }
    }


    protected inline fun <ResultType, RequestType> mapNetWorkResource(
        crossinline query: () -> Flow<ResultType>,
        crossinline fetchNetWorkResource: suspend () -> ApiResource<RequestType>,
        crossinline saveFetchedResult: suspend (RequestType) -> Unit,
        crossinline shouldFetch: (ResultType) -> Boolean = { true }
    ) = flow {
        val data = query().first()

        emit(Resource.Loading(true))
        val flowable = if (shouldFetch(data)) {

            val resourceResult = when (val netWorkResult = fetchNetWorkResource()) {
                is ApiResource.Success -> {

                    saveFetchedResult(netWorkResult.value)

                    val localResource = query().first()

                    Resource.Success(localResource)
                }

                is ApiResource.Failure ->
                    Resource.Error(
                        message = netWorkResult.errorBody,
                        data = netWorkResult.errorCode
                    )

                is ApiResource.NetWorkError -> {
                    val error = if (netWorkResult.errorBody != null)
                        netWorkResult.errorBody.toString()
                    else NETWORK_ERROR

                    Resource.Error(message = error, data = netWorkResult.errorCode)
                }

            }


            query().map { resourceResult }
        } else {
            query().map { Resource.Success(it) }
        }

        emit(Resource.Loading(false))
        emitAll(flowable)
    }
}