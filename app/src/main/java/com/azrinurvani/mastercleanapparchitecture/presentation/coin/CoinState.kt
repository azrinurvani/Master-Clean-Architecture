package com.azrinurvani.mastercleanapparchitecture.presentation.coin

import com.azrinurvani.mastercleanapparchitecture.domain.model.CoinDetail

data class CoinState(
    val isLoading : Boolean = false,
    val coinDetail : CoinDetail? = null,
    val error : String = ""
)
