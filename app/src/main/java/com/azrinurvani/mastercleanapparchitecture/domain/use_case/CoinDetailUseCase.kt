package com.azrinurvani.mastercleanapparchitecture.domain.use_case

import com.azrinurvani.mastercleanapparchitecture.domain.model.CoinDetail
import com.azrinurvani.mastercleanapparchitecture.domain.repository.CoinRepository
import com.azrinurvani.mastercleanapparchitecture.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(id : String) : Flow<ResponseState<CoinDetail>> = flow {
        try {
            emit(ResponseState.Loading())
            val coinDetail = repository.getCoinById(id).toCoinDetail()
            emit(ResponseState.Success(coinDetail))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An Unexpected Error"))
        } catch (e: IOException) {
            emit(ResponseState.Error("Internet Error"))
        }
    }
}