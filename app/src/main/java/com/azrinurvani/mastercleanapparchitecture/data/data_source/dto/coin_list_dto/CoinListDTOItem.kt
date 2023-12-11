package com.azrinurvani.mastercleanapparchitecture.data.data_source.dto.coin_list_dto

import com.azrinurvani.mastercleanapparchitecture.domain.model.Coin

data class CoinListDTOItem(
    val ath: Double?,
    val ath_change_percentage: Double?,
    val ath_date: String?,
    val atl: Double?,
    val atl_change_percentage: Double?,
    val atl_date: String?,
    val circulating_supply: Double?,
    val current_price: Double?,
    val fully_diluted_valuation: Long?,
    val high_24h: Double?,
    val id: String?,
    val image: String?,
    val last_updated: String?,
    val low_24h: Double?,
    val market_cap: Long?,
    val market_cap_change_24h: Double?,
    val market_cap_change_percentage_24h: Double?,
    val market_cap_rank: Int?,
    val max_supply: Double?,
    val name: String?,
    val price_change_24h: Double?,
    val price_change_percentage_24h: Double?,
    val roi: Roi?,
    val symbol: String?,
    val total_supply: Double?,
    val total_volume: Long?
){
    fun toCoin() : Coin{
        return Coin(
            id = id ?: "",
            name = name ?: "",
            image = image ?:"",
            marketCap = market_cap ?:0,
            price = current_price ?: 0.0,
            pricePercentageChange = price_change_percentage_24h ?: 0.0,
            lowPrice = low_24h ?: 0.0,
            highPrice = high_24h ?: 0.0
        )
    }
}