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
import com.bino.wilsonsonsapp.Models.ObjectIntro
import com.bino.wilsonsonsapp.R
import com.bumptech.glide.Glide

class IntroQuestAdapter(private var context: Context, private var listObjectIntro: List<ObjectIntro>): RecyclerView.Adapter<IntroQuestAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return listObjectIntro.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.intro_quest_itemrow, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //aqui você define os valores em cada elemento

        if (position % 2 == 0){

            holder.layout.visibility = View.VISIBLE
            if (!(listObjectIntro.get(position).title.equals("")) || listObjectIntro.get(position).title == null){
                holder.textViewTit.visibility = View.VISIBLE
                holder.textViewTit.text = listObjectIntro.get(position).title
            }
            if (!(listObjectIntro.get(position).text.equals("")) || listObjectIntro.get(position).text == null){
                holder.textViewTexto.visibility = View.VISIBLE
                //holder.textViewTexto.text = arrayTexto.get(position)
                holder.textViewTexto.setText(listObjectIntro.get(position).text)
            }
            if (!(listObjectIntro.get(position).img.equals("")) || listObjectIntro.get(position).img == null){
                holder.img.visibility = View.VISIBLE
                holder.img.setImageResource(listObjectIntro.get(position).img)
            }

        } else {

            holder.layout2.visibility = View.VISIBLE
            if (!(listObjectIntro.get(position).title.equals("")) || listObjectIntro.get(position).title == null){
                holder.textViewTit2.visibility = View.VISIBLE
                holder.textViewTit2.text = listObjectIntro.get(position).title
            }
            if (!(listObjectIntro.get(position).text.equals("")) || listObjectIntro.get(position).text == null){
                holder.textViewTexto2.visibility = View.VISIBLE
                //holder.textViewTexto2.text = arrayTexto.get(position)
                holder.textViewTexto2.setText(listObjectIntro.get(position).text)
            }
            if (!(listObjectIntro.get(position).img.equals("")) || listObjectIntro.get(position).img == null){
                holder.img2.visibility = View.VISIBLE
                holder.img2.setImageResource(listObjectIntro.get(position).img)
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
