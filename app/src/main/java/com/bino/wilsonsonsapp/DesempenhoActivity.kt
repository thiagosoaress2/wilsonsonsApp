package com.bino.wilsonsonsapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bino.wilsonsonsapp.Models.ObjectStatusUser
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import java.util.*


class DesempenhoActivity  : AppCompatActivity()  {

    lateinit var objectStatusUser: ObjectStatusUser

    val DESC_TECNICA = "Texto da técnica"
    val DESC_SEGURANCA = "Texto da segurança"
    val DESC_RELACIONAMENTO = "Texto do relacionamento"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desempenho)

        objectStatusUser = ObjectStatusUser()

        mountChart()

        val btnVoltar: Button = findViewById(R.id.desempenho_btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

    }


    fun mountChart(){

        val desempenho_txPorcent: TextView = findViewById(R.id.desempenho_txPorcent)
        val desempenho_txCat: TextView = findViewById(R.id.desempenho_txCat)
        val desempenho_txExplicacao: TextView = findViewById(R.id.desempenho_txExplicacao)

        val pieChart = findViewById<PieChart>(R.id.pieChart)

        val NoOfEmp = ArrayList<PieEntry>()

        //var entry1 = (objectStatusUser.skill1_points.toString()+"f").toFloat()
        var entry1 = (10.toString()+"f").toFloat()
        NoOfEmp.add(PieEntry((entry1), "Segurança"))
        //entry1 = (objectStatusUser.skill2_points.toString()+"f").toFloat()
        entry1 = (20.toString()+"f").toFloat()
        NoOfEmp.add(PieEntry((entry1), "Relacionamento"))
        //entry1 = (objectStatusUser.skill3_points.toString()+"f").toFloat()
        entry1 = (15.toString()+"f").toFloat()
        NoOfEmp.add(PieEntry((entry1), "Técnica"))

        val dataSet = PieDataSet(NoOfEmp, "")

        pieChart.getDescription().setEnabled(false)
        pieChart.setDrawSliceText(false)
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.selectionShift = 5f
        //dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        dataSet.setColors(Color.parseColor("#105FE8"), Color.parseColor("#B4E4FF"), Color.parseColor("#98FCF6"))

        val data = PieData(dataSet)
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data
        pieChart.highlightValues(null)
        pieChart.invalidate()
        pieChart.animateXY(2000, 2000)


        pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {}

            override fun onValueSelected(e: Entry?, h: Highlight?) {

                val pieEntry = e as PieEntry
                val label: String = pieEntry.label
                val valor = e.value
                val valorInt = valor.toInt()
                var percent = valorInt.toString().replace("f", "")

                if (label.equals("Segurança")){
                    desempenho_txPorcent.setText(percent+"%")
                    desempenho_txCat.setText(label)
                    desempenho_txExplicacao.setText(DESC_SEGURANCA)
                } else if (label.equals("Técnica")){
                    desempenho_txPorcent.setText(percent+"%")
                    desempenho_txCat.setText(label)
                    desempenho_txExplicacao.setText(DESC_TECNICA)
                } else {
                    desempenho_txPorcent.setText(percent+"%")
                    desempenho_txCat.setText(label)
                    desempenho_txExplicacao.setText(DESC_RELACIONAMENTO)
                }

            }
        })

    }

}