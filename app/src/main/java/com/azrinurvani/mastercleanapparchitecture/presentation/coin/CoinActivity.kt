package com.azrinurvani.mastercleanapparchitecture.presentation.coin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.azrinurvani.mastercleanapparchitecture.R
import com.azrinurvani.mastercleanapparchitecture.databinding.ActivityCoinBinding
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CoinActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCoinBinding
    private val coinViewModel : CoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrieveCoinDetail()
    }

    private fun retrieveCoinDetail(){
        intent?.let {
            val coinId = it.getStringExtra("id") ?: ""
            if (coinId.isNotBlank()){
                coinViewModel.getCoinById(coinId)
                observeCoinDetail()
            }else{
                Toast.makeText(this,"We don't have any id to call",Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeCoinDetail(){
        CoroutineScope(Dispatchers.IO).launch {
            coinViewModel._coinValue.collectLatest { coinValue ->

                withContext(Dispatchers.Main){
                    if (coinValue.isLoading){
                        binding.coinProgressBar.visibility = View.VISIBLE
                    }else {
                        if (coinValue.error.isNotBlank()){
                            binding.coinProgressBar.visibility = View.GONE
                            Toast.makeText(this@CoinActivity,coinValue.error,Toast.LENGTH_LONG).show()
                        }else{
                            binding.coinProgressBar.visibility = View.GONE

                            coinValue.coinDetail?.let { coinDetail ->
                                Glide.with(this@CoinActivity)
                                    .load(coinDetail.image)
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.ic_error_gray)
                                    .into(binding.imgCoinDetail)

                                binding.tvCoinPrice.text = "Price : ${coinDetail.price}"
                                binding.tvCoinName.text = "Coin Name : ${coinDetail.name}"
                                binding.tvCoinPriceLow.text = "Coin Price : ${coinDetail.lowPrice}"
                                binding.tvCoinPriceHigh.text = "Coin Price High : ${coinDetail.highPrice}"
                                binding.tvCoinMarketCap.text = "Coin Market Cap : ${coinDetail.marketCap}"
                                binding.tvCoinPricePercentChange.text =
                                    "Coin Price Percent Change : ${coinDetail.pricePercentage}"
                            }
                        }
                    }
                }


            }
        }
    }

}