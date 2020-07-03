package com.bino.wilsonsonsapp.Utils

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bino.wilsonsonsapp.Controllers.AdminControllers
import com.bino.wilsonsonsapp.R
import java.util.*


class ListFuncPorEstadoAdapter (private var context: Context, private var arrayNome:MutableList<String>, private var arrayFuncao:MutableList<String>, private var arrayCertificados:MutableList<String>, private var arrayValidade:MutableList<String>, private var arrayBd:MutableList<String>): RecyclerView.Adapter<ListFuncPorEstadoAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return arrayNome.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_func_estado_itemrow, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //aqui você define os valores em cada elemento
        holder.tvNome.text = arrayNome.get(position);
        holder.tvFunc.text = arrayFuncao.get(position)
        var quantidade=0
        var cont=0
        var indexes: MutableList<Int> = ArrayList()
        while (cont<arrayCertificados.size){  //descobrir quantas vezes teremos que entrar pra pegar o dado
            if (arrayCertificados.get(cont).contains(arrayBd.get(position))){
                indexes.add(cont)
            }
            cont++
        }

        cont=0
        while (cont<indexes.size){

            val tokens = StringTokenizer(arrayCertificados.get(indexes.get(cont)).toString(), "!*!??#") //”*” este é delim
            val desc = tokens.nextToken() // descartar
            val certificado = tokens.nextToken() // valor que queremos
            if (cont==0){
                //holder.tvCertf.text = certificado+" - val: "+arrayValidade.get(cont)
                if (AdminControllers.checkCertificateValidit(arrayValidade.get(indexes.get(cont)))){
                    Log.d("teste", "entrou no vermelho na primeira")
                    val valvencida = " <font color='#FF0000'>"+certificado+" - val: "+arrayValidade.get(indexes.get(cont)).toString()+"</font>"
                    holder.tvCertf.setText(Html.fromHtml(valvencida));
                    //holder.tvCertf.text = valvencida
                } else {
                    holder.tvCertf.text = certificado+" - val: "+arrayValidade.get(indexes.get(cont))
                }
            } else {
                val textoInicial = holder.tvCertf.text.toString()
                if (AdminControllers.checkCertificateValidit(arrayValidade.get(indexes.get(cont)))){
                    Log.d("teste", "entrou no vermelho")
                    val valvencida = " <font color='#FF0000'>"+certificado+" - val: "+arrayValidade.get(indexes.get(cont)).toString()+"</font>"
                    //holder.tvCertf.text = textoInicial+"\n"+valvencida
                    holder.tvCertf.setText(Html.fromHtml(textoInicial+"\n"+valvencida));
                } else {
                    holder.tvCertf.text = textoInicial+"\n"+certificado+" - val: "+arrayValidade.get(indexes.get(cont))
                }

            }

            cont++
        }
    }

    fun filterCertificate(name: String){

        var cont=0
        while (cont<arrayNome.size){

            var contHere=0
            while (contHere<arrayCertificados.size){
                if (arrayCertificados.get(contHere).contains(name)){
                    val tokens = StringTokenizer(arrayCertificados.get(contHere).toString(), "!*!??#") //”*” este é delim
                    val desc = tokens.nextToken() // agora usamos ele
                    val certificado = tokens.nextToken() // descartar

                    var contHere2=0
                    while (contHere2<arrayBd.size){
                        if (arrayBd.get(contHere2).equals(desc)){
                            //remover
                            arrayNome.removeAt(contHere2)
                            arrayBd.removeAt(contHere2)
                            arrayCertificados.removeAt(contHere2)
                            arrayFuncao.removeAt(contHere2)
                            arrayValidade.removeAt(contHere2)
                            notifyDataSetChanged()
                        }
                        contHere2++
                    }
                }
                contHere++
            }
        }
        cont++

    }


    class ViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {
        //aqui você associa cada elemento a um nome para invocar nesta classe
        var tvNome: TextView = itemView!!.findViewById(R.id.list_func_estado_nome)
        //var img: ImageView = itemView!!.findViewById(R.id.minhaloja_rv_row_imageview)
        var tvFunc: TextView = itemView!!.findViewById(R.id.list_func_estado_funcao)
        var tvCertf: TextView = itemView!!.findViewById(R.id.list_func_estado_certificados)

    }

}
