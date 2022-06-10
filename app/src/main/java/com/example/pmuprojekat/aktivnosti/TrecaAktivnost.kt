package com.example.pmuprojekat.aktivnosti

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.example.pmuprojekat.R
import com.example.pmuprojekat.apihandlers.NorthwindApiHandler
import com.example.pmuprojekat.data.ApiRoutes
import com.example.pmuprojekat.data.Category
import com.example.pmuprojekat.data.Proizvod
import com.example.pmuprojekat.viewadapters.CategoryViewAdapter
import com.example.pmuprojekat.viewadapters.ProizvodiViewAdapter
import com.example.pmuprojekat.viewmodels.ProizvodiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrecaAktivnost : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dohvatiKategorije(this, ApiRoutes.products)
    }

    private fun dohvatiKategorije(ctx: Context, sUrl: String): List<Proizvod>? {
        var products: List<Proizvod>? = null
        lifecycleScope.launch(Dispatchers.IO) {
            val apiHandler = NorthwindApiHandler()
            val result = apiHandler.getRequest(sUrl)

            val table = mutableMapOf<Int, Int>()

            if (result != null) {
                try {
                    products = Klaxon().parseArray(result)
                    for (product in products!!) {
                        if (product.supplierId != null){
                            if (table[product.supplierId!!] != null) {
                                table[product.supplierId!!] = table[product.supplierId]!! + 1
                            } else {
                                table[product.supplierId!!] = 1
                            }
                        }
                    }
                    withContext(Dispatchers.Main) {
                        val linearLayout = LinearLayout(ctx)
                        linearLayout.orientation = LinearLayout.VERTICAL

                        for ((key, value) in table) {
                            val textView: TextView = TextView(ctx)
                            textView.text = "Dobavljacu $key pripada $value ${if (value == 1) "proizvod" else "proizvoda"}"
                            linearLayout.addView(textView)
                        }
                        setContentView(linearLayout)

                    }
                } catch (err: Error) {
                    print("Error when parsing JSON: " + err.localizedMessage)
                }
            } else {
                print("Error: Get request returned no response")
            }

        }
        return products
    }
}