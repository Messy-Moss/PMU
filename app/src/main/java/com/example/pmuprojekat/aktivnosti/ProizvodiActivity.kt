package com.example.pmuprojekat.aktivnosti

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.example.pmuprojekat.R
import com.example.pmuprojekat.apihandlers.NorthwindApiHandler
import com.example.pmuprojekat.data.ApiRoutes
import com.example.pmuprojekat.data.Proizvod
import com.example.pmuprojekat.viewadapters.ProizvodiViewAdapter
import com.example.pmuprojekat.viewmodels.ProizvodiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProizvodiActivity : AppCompatActivity() {
    val viewModel: ProizvodiViewModel by viewModels()
    var proizvodiViewAdapter: ProizvodiViewAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proizvodi)
        dohvatiProizvode(this, ApiRoutes.products)
    }

    private fun dohvatiProizvode(ctx: Context, url: String): List<Proizvod>? {
        var proizvodi: List<Proizvod>? = null

        lifecycleScope.launch(Dispatchers.IO) {
            val apiHandler = NorthwindApiHandler()
            val result = apiHandler.getRequest(url)
            if (result != null) {
                try {
                    proizvodi = Klaxon().parseArray(result)

                    withContext(Dispatchers.Main) {
                        viewModel.lstProizovdi.value = proizvodi;
                        val lstProizvodiView = findViewById<RecyclerView>(R.id.lstProizvodiView)
                        lstProizvodiView.layoutManager = LinearLayoutManager(ctx)
                        proizvodiViewAdapter = ProizvodiViewAdapter(ctx, viewModel.lstProizovdi)
                        lstProizvodiView.adapter = proizvodiViewAdapter
                    }
                } catch (err: Error) {
                    print("Error when parsing JSON")
                }
            } else {
                print("Error: Get request returned no response")
            }
        }
        return proizvodi
    }

    fun prikaziUnosProizvoda(view: View) {
        setContentView(R.layout.proizvod_item)

        val proizvodBtn = findViewById<Button>(R.id.proizvodAkcija)
        proizvodBtn.text = "Dodaj"

        val nazivProizvoda = findViewById<EditText>(R.id.nazivProizvoda)
        val opisProizvoda = findViewById<EditText>(R.id.opisProizvoda)

        proizvodBtn.setOnClickListener {
            val novProizvod = Proizvod(
                0,
                nazivProizvoda.text.toString(),
                opisProizvoda.text.toString().toBoolean(),
                null
            )

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val apiHandler = NorthwindApiHandler()
                    val result = apiHandler.postRequest(
                        ApiRoutes.products,
                        Klaxon().toJsonString(novProizvod)
                    )
                    if (result != null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(getApplicationContext(), "Usepsno dodavanje proizvoda", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        print("Error: Get request returned no response")
                    }
                } catch (err: Error) {
                    print("Error when parsing JSON: " + err.localizedMessage)
                }
            }

        }

    }

}






















