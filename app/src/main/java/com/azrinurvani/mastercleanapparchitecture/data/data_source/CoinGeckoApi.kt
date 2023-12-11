package com.azrinurvani.mastercleanapparchitecture.data.data_source

import com.azrinurvani.mastercleanapparchitecture.data.data_source.dto.coin_detail_dto.CoinDetailDto
import com.azrinurvani.mastercleanapparchitecture.data.data_source.dto.coin_list_dto.CoinListDTOItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&sparkline=false")
    suspend fun getAllCoins(
        @Query("page") page : String
    ) : List<CoinListDTOItem>

    @GET("/api/v3/coins/{id}")
    suspend fun getCoinById(
        @Path("id") id : String
    ) : CoinDetailDto
}