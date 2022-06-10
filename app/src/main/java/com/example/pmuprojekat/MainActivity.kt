package com.example.pmuprojekat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.pmuprojekat.aktivnosti.KategorijeActivity
import com.example.pmuprojekat.aktivnosti.ProizvodiActivity
import com.example.pmuprojekat.aktivnosti.TrecaAktivnost

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // CLICK LISTENER DEFINED IN XML LAYOUT FILE
    fun otvoriAktivnostKategorije(view: View) {
        val intent = Intent(this,KategorijeActivity::class.java)
        startActivity(intent)
    }

    fun otvoriAktivnostProizvodi(view: View) {
        val intent = Intent(this, ProizvodiActivity::class.java)
        startActivity(intent)
    }

    fun otvoriAktivnost3(view: View) {
        val intent = Intent(this, TrecaAktivnost::class.java)
        startActivity(intent)
    }

}