package com.azrinurvani.mastercleanapparchitecture.domain.use_case

import com.azrinurvani.mastercleanapparchitecture.domain.model.Coin
import com.azrinurvani.mastercleanapparchitecture.domain.repository.CoinRepository
import com.azrinurvani.mastercleanapparchitecture.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinListUseCase @Inject constructor(
    private val repository : CoinRepository
) {
    operator fun invoke(page : String) : Flow<ResponseState<List<Coin>>> = flow {
        try {
            emit(ResponseState.Loading())
            val coins = repository.getAllCoins(page).map {
                it.toCoin()
            }
            emit(ResponseState.Success(coins))
        }catch (e : Exception){
            emit(ResponseState.Error("An Unexpected Error ${e.message}"))
        }
    }
}