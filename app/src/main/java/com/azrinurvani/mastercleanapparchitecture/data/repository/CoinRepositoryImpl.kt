package com.azrinurvani.mastercleanapparchitecture.data.repository

import com.azrinurvani.mastercleanapparchitecture.data.data_source.CoinGeckoApi
import com.azrinurvani.mastercleanapparchitecture.data.data_source.dto.coin_detail_dto.CoinDetailDto
import com.azrinurvani.mastercleanapparchitecture.data.data_source.dto.coin_list_dto.CoinListDTOItem
import com.azrinurvani.mastercleanapparchitecture.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinApi : CoinGeckoApi
) : CoinRepository {

    override suspend fun getAllCoins(page: String): List<CoinListDTOItem> {
        return coinApi.getAllCoins( page = page)

    }

    override suspend fun getCoinById(id: String): CoinDetailDto {
        return coinApi.getCoinById(id = id)
    }
}