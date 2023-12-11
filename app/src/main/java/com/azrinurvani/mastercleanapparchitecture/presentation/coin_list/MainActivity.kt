package com.azrinurvani.mastercleanapparchitecture.presentation.coin_list

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azrinurvani.mastercleanapparchitecture.R
import com.azrinurvani.mastercleanapparchitecture.databinding.ActivityMainBinding
import com.azrinurvani.mastercleanapparchitecture.domain.model.Coin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding : ActivityMainBinding

    private lateinit var coinAdapter: CoinAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var page : Int = 1
    private val tempCoinList =  arrayListOf<Coin>()

    private val coinListViewModel : CoinListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        listener()
        callAPI()
//        gridLayoutManager = GridLayoutManager(this,2)
        eventRecyclerScrollListener()

    }

    private fun listener() {
        binding.btnSort.setOnClickListener {
            tempCoinList.sortWith{ o1,o2 -> o1.name.compareTo(o2.name) }
            coinAdapter.setData(tempCoinList)
        }
    }

    private fun eventRecyclerScrollListener() {
        binding.rvCoin.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (gridLayoutManager.findLastVisibleItemPosition() == gridLayoutManager.itemCount - 1){
                    page += 1
                    coinListViewModel.getAllCoins(page.toString())
                    callAPI()
                }
            }
        })
    }

    private fun callAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            coinListViewModel.getAllCoins(page.toString())
            coinListViewModel._coinListValue.collectLatest { coinListValue ->
                withContext(Dispatchers.Main){
                    if (coinListValue.isLoading){
                        binding.progressBar.visibility = View.VISIBLE
                    }else {
                        if (coinListValue.error.isNotBlank()){
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@MainActivity,coinListValue.error,Toast.LENGTH_LONG).show()
                        }else{
                            binding.progressBar.visibility = View.GONE
                            tempCoinList.addAll(coinListValue.coinList)
                            coinAdapter.setData(tempCoinList)
                        }
                    }
                }


            }
        }
    }

    private fun setupRecyclerView() {
        coinAdapter = CoinAdapter(this@MainActivity,ArrayList())
        gridLayoutManager = GridLayoutManager(this,2)
        binding.rvCoin.apply {
            adapter = coinAdapter
            layoutManager = gridLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    GridLayoutManager(
                        this@MainActivity,
                        1
                    ).orientation
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu,menu)
        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText?.isEmpty() == true){
            coinAdapter.setData(tempCoinList)
        }else{
            coinAdapter.filter.filter(newText)
        }
        return true
    }

}
