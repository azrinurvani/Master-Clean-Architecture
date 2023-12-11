package com.azrinurvani.mastercleanapparchitecture.presentation.coin_list

import com.azrinurvani.mastercleanapparchitecture.domain.model.Coin

data class CoinListState(
    val isLoading : Boolean = false,
    val coinList : List<Coin> = emptyList(),
    val error : String = ""
)
