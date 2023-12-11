package com.azrinurvani.mastercleanapparchitecture.presentation.coin_list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.azrinurvani.mastercleanapparchitecture.R
import com.azrinurvani.mastercleanapparchitecture.databinding.ItemRowCoinBinding
import com.azrinurvani.mastercleanapparchitecture.domain.model.Coin
import com.azrinurvani.mastercleanapparchitecture.presentation.coin.CoinActivity
import com.bumptech.glide.Glide

class CoinAdapter(
    private val context: Context,
    var coinList : ArrayList<Coin>
) : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>(), Filterable {

    lateinit var filteredList : ArrayList<Coin>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinAdapter.CoinViewHolder {
        val coinView = ItemRowCoinBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CoinViewHolder(coinView)
    }

    override fun onBindViewHolder(holder: CoinAdapter.CoinViewHolder, position: Int) {
        val coin = coinList[position]

        holder.binding.tvCoinName.text = coin.name
        Glide.with(context)
            .load(coin.image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_error_gray)
            .into(holder.binding.imgCoinImage)

        //Ganti menggunakan interface action listener
        holder.binding.linearLayoutCoin.setOnClickListener {
            val intent = Intent(context,CoinActivity::class.java)
            intent.putExtra("id",coin.id)
            context.startActivity(intent)

        }
    }

    override fun getItemCount() = coinList.size

    fun setData(list : ArrayList<Coin>){
        this.coinList = list
        notifyDataSetChanged()
    }


    inner class CoinViewHolder(val binding: ItemRowCoinBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val string = charSequence?.toString() ?: ""
                if (string.isNotEmpty()){
                    val arrayList = arrayListOf<Coin>()
                    filteredList.filter {
                        it.name.lowercase().contains(string.lowercase())
                    }.forEach {
                        arrayList.add(it)
                    }
                    filteredList.clear()
                    filteredList.addAll(arrayList)
                }else{
                    filteredList = coinList
                }
                return  FilterResults().apply {
                    this.values = filteredList
                }
            }

            override fun publishResults(charSequence: CharSequence?, results: FilterResults?) {
                if (results?.values == null){
                    ArrayList<Coin>()
                }else{
                    setData(filteredList)
                }
            }
        }
    }
}