package com.example.pmuprojekat.viewadapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.pmuprojekat.R
import com.example.pmuprojekat.aktivnosti.DetaljiKategorijeActivity
import com.example.pmuprojekat.aktivnosti.DetaljiProizvodiActivity
import com.example.pmuprojekat.data.Category
import com.example.pmuprojekat.data.Proizvod

class ProizvodiViewAdapter(val ctx: Context, val data: LiveData<List<Proizvod>>)
    : RecyclerView.Adapter<ProizvodiViewAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ProizvodiViewAdapter.CategoryViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(ctx)
        val view = inflater.inflate(R.layout.proizvodi_row,parent,false)
        return CategoryViewHolder(view)
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model:Proizvod){
            val proizvodId = model.productId
            val nam=itemView.findViewById<TextView>(R.id.proizvodiName)
            nam.text=model.productName

//            val discontinued = itemView.findViewById<TextView>(R.id.discontinued)

            if (model.discontinued) {
                nam.append(" DISCONTINUED")
            }

            val detaljnije=itemView.findViewById<Button>(R.id.detaljnijeProizvodi)

            detaljnije.setOnClickListener {
                val intent= Intent(ctx, DetaljiProizvodiActivity::class.java)
                intent.putExtra("pId",proizvodId)
                ctx.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindItems(data.value!!.get(position))
    }

    override fun getItemCount(): Int {
        if(data.value!=null){
            return data.value!!.size
        }
        return 0;
    }

}