package com.bino.wilsonsonsapp.Utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bino.wilsonsonsapp.R
import com.bumptech.glide.Glide

class introQuestAdapter(private var context: Context, private var arrayNome:MutableList<String>, private var arrayTexto:MutableList<String>, private var arrayImg:MutableList<String>): RecyclerView.Adapter<introQuestAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return arrayNome.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.intro_quest_itemrow, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //aqui você define os valores em cada elemento

        if (position % 2 == 0){

            Log.d("teste", "O valor de arrayTitulo é "+arrayNome.get(position))
            Log.d("teste", "O valor de arrayTexto é "+arrayTexto.get(position))
            Log.d("teste", "O valor de arrayImg é "+arrayImg.get(position))

            holder.layout.visibility = View.VISIBLE
            if (!arrayNome.get(position).equals("nao")){
                holder.textViewTit.visibility = View.VISIBLE
                holder.textViewTit.text = arrayNome.get(position)
            }
            if (!arrayTexto.get(position).equals("nao")){
                holder.textViewTexto.visibility = View.VISIBLE
                //holder.textViewTexto.text = arrayTexto.get(position)
                holder.textViewTexto.setText(arrayTexto.get(position))
            }
            if (!arrayImg.get(position).equals("nao")){
                holder.img.visibility = View.VISIBLE
                Glide.with(context).load(arrayImg.get(position)).into(holder.img)
            }

        } else {

            holder.layout2.visibility = View.VISIBLE
            if (!arrayNome.get(position).equals("nao")){
                holder.textViewTit2.visibility = View.VISIBLE
                holder.textViewTit2.text = arrayNome.get(position)
            }
            if (!arrayTexto.get(position).equals("nao")){
                holder.textViewTexto2.visibility = View.VISIBLE
                //holder.textViewTexto2.text = arrayTexto.get(position)
                holder.textViewTexto2.setText(arrayTexto.get(position))
            }
            if (!arrayImg.get(position).equals("nao")){
                holder.img2.visibility = View.VISIBLE
                Glide.with(context).load(arrayImg.get(position)).into(holder.img2)
            }
        }

    }


    class ViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {
        //aqui você associa cada elemento a um nome para invocar nesta classe
        val textViewTit: TextView = itemView!!.findViewById(R.id.tipo1_txtitulo)
        val textViewTexto: TextView = itemView!!.findViewById(R.id.tipo1_txTexto)
        val layout: ConstraintLayout = itemView!!.findViewById(R.id.tipo1)
        val img: ImageView = itemView!!.findViewById(R.id.tipo1_img)

        val textViewTit2: TextView = itemView!!.findViewById(R.id.tipo2_txtitulo)
        val textViewTexto2: TextView = itemView!!.findViewById(R.id.tipo2_txTexto)
        val layout2: ConstraintLayout = itemView!!.findViewById(R.id.tipo2)
        val img2: ImageView = itemView!!.findViewById(R.id.tipo2_img)
        //var img: ImageView = itemView!!.findViewById(R.id.minhaloja_rv_row_imageview)

    }

}
