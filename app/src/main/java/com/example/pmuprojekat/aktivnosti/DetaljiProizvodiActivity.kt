package com.example.pmuprojekat.aktivnosti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.beust.klaxon.Klaxon
import com.example.pmuprojekat.R
import com.example.pmuprojekat.apihandlers.NorthwindApiHandler
import com.example.pmuprojekat.data.ApiRoutes
import com.example.pmuprojekat.data.Proizvod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetaljiProizvodiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.proizvod_item)
        val pId = intent.extras!!["pId"]
        dohvatiProizvode(ApiRoutes.products + "/${pId}")

    }

    fun obrisiProizvod(pId: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiHandler = NorthwindApiHandler()
                val result = apiHandler.deleteRequest(ApiRoutes.products + "/${pId}")
                if (result != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            getApplicationContext(),
                            "Usepsno obrisan proizvod",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {
                    print("Error: Get request returned no response")
                }

            } catch (err: Error) {
                print("Error when parsing JSON: " + err.localizedMessage)
            }
        }
    }

    fun snimanjeIzmena(pId: Int, pName: String, pDisc: Boolean) {
        val izmenjenProizvod = Proizvod(
            pId,
            pName,
            pDisc,
            null
        )

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiHandler = NorthwindApiHandler()
                val result = apiHandler.putRequest(
                    ApiRoutes.products + "/${pId}",
                    Klaxon().toJsonString(izmenjenProizvod)
                )
                if (result != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            getApplicationContext(),
                            "Usepsno izmenjeni podaci",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {
                    print("Error: Get request returned no response")
                }
            } catch (err: Error) {
                print("Error when parsing JSON: " + err.localizedMessage)
            }
        }

    }

    fun dohvatiProizvode(sUrl: String): Proizvod? {
        var proizvod: Proizvod? = null
        lifecycleScope.launch(Dispatchers.IO) {
            val apiHandler = NorthwindApiHandler()
            val result = apiHandler.getRequest(sUrl)

            if (result != null) {
                try {

                    proizvod = Klaxon().parse(result)
                    withContext(Dispatchers.Main) {

                        println(proizvod)

                        val nazivProizvoda = findViewById<EditText>(R.id.nazivProizvoda)

                        println(nazivProizvoda)

                        nazivProizvoda.setText(proizvod!!.productName)

                        val opisProizvoda = findViewById<EditText>(R.id.opisProizvoda)
                        opisProizvoda.setText(proizvod!!.discontinued.toString())

                        val brisanjeProizvoda = findViewById<Button>(R.id.proizvodAkcija2)
                        brisanjeProizvoda.text = "Obrisi"
                        brisanjeProizvoda.isVisible = true
                        brisanjeProizvoda.setOnClickListener {
                            println(proizvod!!.productId)
                            obrisiProizvod(proizvod!!.productId)
                        }

                        val snimiIzmene = findViewById<Button>(R.id.proizvodAkcija)
                        snimiIzmene.text = "Snimi"

                        snimiIzmene.setOnClickListener {

//                            var shit = if (opisProizvoda.text.toString().toBoolean()) "Discontinued" else "Not Discontinued"

                            snimanjeIzmena(
                                proizvod!!.productId,
                                nazivProizvoda.text.toString(),
                                opisProizvoda.text.toString().toBoolean()
                            )
                        }

                    }


                } catch (err: Error) {
                    print("Error when parsing JSON: " + err.localizedMessage)
                }
            } else {
                print("Error: Get request returned no response")
            }
        }
        return proizvod;

    }

}