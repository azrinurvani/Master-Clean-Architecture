package com.azrinurvani.mastercleanapparchitecture.domain.repository

import com.azrinurvani.mastercleanapparchitecture.data.data_source.dto.coin_detail_dto.CoinDetailDto
import com.azrinurvani.mastercleanapparchitecture.data.data_source.dto.coin_list_dto.CoinListDTOItem

interface CoinRepository {

    suspend fun getAllCoins(page : String) : List<CoinListDTOItem>

    suspend fun getCoinById(id : String) : CoinDetailDto
}