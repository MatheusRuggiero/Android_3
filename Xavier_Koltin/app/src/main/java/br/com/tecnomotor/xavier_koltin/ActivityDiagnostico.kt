package br.com.tecnomotor.xavier_koltin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import com.github.mikephil.charting.charts.LineChart

import com.github.mikephil.charting.data.Entry

import com.github.mikephil.charting.data.LineData

import com.github.mikephil.charting.data.LineDataSet

import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class ActivityDiagnostico : AppCompatActivity() {

    var mChart: LineChart? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnostico)

        mChart = findViewById(R.id.linechart)
        mChart!!.isDragEnabled = true
        mChart!!.setScaleEnabled(false)

        val yValues: ArrayList<Entry> = ArrayList<Entry>()

        yValues.add(Entry(0F, 6000f))
        yValues.add(Entry(10F, 5000f))
        yValues.add(Entry(20F, 7000f))
        yValues.add(Entry(30F, 3000f))
        yValues.add(Entry(40F, 2000f))
        yValues.add(Entry(50F, 4000f))
        yValues.add(Entry(60F, 7000f))
        yValues.add(Entry(70F, 5000f))
        yValues.add(Entry(80F, 1000f))

        val set1 = LineDataSet(yValues, "Data Set 1")
        set1.setFillAlpha(110)

        val dataSets: ArrayList<ILineDataSet> = ArrayList<ILineDataSet>()
        dataSets.add(set1)

        val data = LineData(dataSets)

        mChart!!.data = data


    }

}