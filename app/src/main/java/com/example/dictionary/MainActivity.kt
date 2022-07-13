package com.example.dictionary

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.Retrofit.RetrofitInstance
import com.example.dictionary.adapters.MeaningAdapter
import com.example.dictionary.adapters.PhoeticsAdapter
import com.example.dictionary.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PhoeticsAdapter
    private lateinit var adapter1: MeaningAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        installSplashScreen()
        setContentView(binding.root)
        binding.search.visibility=View.INVISIBLE
       if (checkInternetConnection(this)) {
             postData()
        } else {
            val alert = AlertDialog.Builder(this)
            alert.setMessage("Check Your Connection")
            alert.setPositiveButton("Try again") { Dialog,_ ->
            if(checkInternetConnection(this)){
                Dialog.dismiss()
                postData()
            }else{
                startActivity(Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS))
                postData()
            }
            }
           alert.setNegativeButton("Exit"){_,_ ->
               this.finish()
               exitProcess(0)
           }
            alert.setCancelable(false)
            alert.show()
        }
    }
   private fun postData(){
       binding.search.visibility=View.VISIBLE
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val word = p0.toString()
                if(!word.equals(null)) {
                    binding.pro1.visibility = View.VISIBLE
                    getData(word)
                    binding.search.clearFocus()
                }
                return true
            }
                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }
        })
        binding.recycle1.hasFixedSize()
        binding.recycle1.layoutManager = LinearLayoutManager(this)
        binding.recycle.hasFixedSize()
        binding.recycle.layoutManager = LinearLayoutManager(this)
    }


    private fun getData(word:String){
        binding.word.text=word
        binding.word.visibility=View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val response=RetrofitInstance.api.getWord(word)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful && response.body() != null) {
                        binding.txt1.visibility = View.VISIBLE
                        binding.txt2.visibility = View.VISIBLE
                        binding.pro1.visibility = View.INVISIBLE
                        val responseBody = response.body()!!.listIterator()
                        val next = responseBody.next()
                        adapter = PhoeticsAdapter(next.phonetics)
                        binding.recycle1.adapter = adapter
                        adapter1 = MeaningAdapter(next.meanings)
                        binding.recycle.adapter = adapter1
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Given World Not in  Dictionary",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }catch (e:HttpException){
                    Toast.makeText(this@MainActivity,"Cannot Reach Server",Toast.LENGTH_LONG).show()
                }catch(e:IOException){
                    Toast.makeText(this@MainActivity,"Check your Internet Connection",Toast.LENGTH_LONG).show()
                }
            }
        }


    }
    private fun checkInternetConnection(context: Context):Boolean{
        val connectivityManger=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            val network=connectivityManger.activeNetwork?:return false
            val activityNetwork=connectivityManger.getNetworkCapabilities(network)?:return false
            return when{
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                else->false
            }
        }
        return false
    }
}