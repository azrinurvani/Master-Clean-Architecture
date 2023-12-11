package com.azrinurvani.mastercleanapparchitecture.domain.model

data class CoinDetail(
    val name : String,
    val image : String,
    val marketCap : Double,
    val price : Double,
    val pricePercentage : Double,
    val lowPrice : Double,
    val highPrice : Double,
    val description: String
)
