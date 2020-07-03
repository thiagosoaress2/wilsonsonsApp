package com.bino.wilsonsonsapp.Utils

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bino.wilsonsonsapp.Controllers.AdminControllers
import com.bino.wilsonsonsapp.R

class ListCursosAdapter(private var context: Context, private var arrayNome:MutableList<String>, private var arrayValidade:MutableList<String>, private  val tipo: String): RecyclerView.Adapter<ListCursosAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return arrayNome.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.lista_cursos_itemrow, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (tipo.equals("curso")){
            holder.tvValidade.visibility = View.GONE
            holder.tvCurso.setText(arrayNome.get(position))
        } else {
            //aqui está exibindo os certificados
            if (AdminControllers.checkCertificateValidit(arrayValidade.get(position))){

                val nomeCurso = " <font color='#FF0000'>"+arrayNome.get(position) +"</font>"
                holder.tvCurso.setText(Html.fromHtml(nomeCurso))
                val valvencida = " <font color='#FF0000'> Validade: "+arrayValidade.get(position)+"</font>"
                holder.tvValidade.setText(Html.fromHtml(valvencida))

            } else {

                holder.tvCurso.setText(arrayNome.get(position))
                holder.tvValidade.setText(arrayValidade.get(position))
            }


        }


    }

    class ViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {
        //aqui você associa cada elemento a um nome para invocar nesta classe
        val tvCurso: TextView = itemView!!.findViewById(R.id.listaCurso_tvCurso)
        val tvValidade: TextView = itemView!!.findViewById(R.id.listaCurso_tvVal)

    }

}
