package br.com.tecnomotor.xavier_koltin

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import br.com.tecnomotor.serialport.GenericSerialPortFactory
import br.com.tecnomotor.xavier_koltin.model.Stat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.appbar.AppBarLayout
import com.pusher.client.Pusher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeoutException


class GraficoActivity : AppCompatActivity() {
    var mChart: LineChart? = null
    var pusher: Pusher? = null
    var PUSHER_APP_KEY = "5"
    var PUSHER_APP_CLUSTER = "6"
    val CHANNEL_NAME = "stats"
    val EVENT_NAME = "new_memory_stat"

    val TOTAL_MEMORY = 3000.0f
    val LIMIT_MAX_MEMORY = 2000.0f
    var colocarMediaNoGrafico: Float = 0f
    var resultadoCalculo = 0f // resultado do calculo da media


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    lateinit var serialFactory: GenericSerialPortFactory


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_grafico)

        var tvMedia = findViewById<TextView>(R.id.tvMedia)


        mChart = findViewById<View>(R.id.chart) as LineChart
        setupChart()
        setupAxes()
        setupData()
        setLegend()


        serialFactory = GenericSerialPortFactory(this@GraficoActivity).withBluetooth()

        GlobalScope.launch(context = Dispatchers.IO) {


            val devDescrption = serialFactory.descriptors()[0]
            val serialPort = serialFactory.createSerialPort(devDescrption)

            var RetornoSerial: ByteArray
            var stat = Stat()
            var contador: Int = 0
            var mediaLeituras: Float = 0f
            var contadorParaMedia: Int = 0
            var totalMedia: Float = 0f

            var qtdDeLeituras: Int = 10 // TODO: 22/04/2021 // ira ser mdificado futuramente

            serialPort.write(byteArrayOf("49".toByte())) // enviando o valor 1 para ativar o led de bluetooth

            while (true) {
                // delay(1000)
                try {
                    RetornoSerial = serialPort.read(timeout = 150)
                } catch (e: TimeoutException) {
                    Log.d("graficoactivity", "${e.message}")
                    continue
                }

                val mensagens = String(RetornoSerial).split("\r\n")


                for (mensagem in mensagens) {

                    contador += 1

                    if (contador % 1000 == 0) {
                        serialPort.write(byteArrayOf("51".toByte())) // enviando o numero 3 para que o Xavier nao desligue
                    }

                    val stat = Stat()
                    try {
                        stat.memory = mensagem.toFloat()


                            contadorParaMedia += 1
                            totalMedia += mensagem.toFloat()

                            if (contadorParaMedia >= qtdDeLeituras) {

                                 resultadoCalculo = (totalMedia / contadorParaMedia)
                                contadorParaMedia = 0
                                totalMedia = 0f
                            }

                        runOnUiThread {
                            addEntry(stat)
                            tvMedia.text = "Média de " + resultadoCalculo.toString() + " Bar" // TODO: estudar melhor  outra maneira de concatenar a string com os valores plotados.
                        }

                    } catch (e: Exception) {
                        Log.d("graficoactivity", "${e.message}")

                    }


                }

            }

        }

    }


    fun setupChart() {
        // disable description text
        mChart!!.description.isEnabled = true
        // enable touch gestures
        mChart!!.setTouchEnabled(true)
        // if disabled, scaling can be done on x- and y-axis separately
        mChart!!.setPinchZoom(true)
        // enable scaling
        mChart!!.setDragEnabled(true)
        mChart!!.setScaleEnabled(true)
        mChart!!.setDrawGridBackground(false)
        // set an alternative background color
       mChart!!.setBackgroundColor(Color.DKGRAY)
        mChart!!.description.text = "Tempo (Milisegundos)"
        mChart!!.description.textColor = Color.WHITE
        mChart!!.description.textSize = 12f


    }

    fun setupAxes() {
        val xl = mChart!!.xAxis
        xl.textColor = Color.WHITE
        xl.setDrawGridLines(true)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = true
        xl.valueFormatter = FormatadorEixoX()
        xl.position = XAxis.XAxisPosition.BOTTOM

        val leftAxis = mChart!!.axisLeft
        leftAxis.textColor = Color.WHITE
        leftAxis.axisMaximum = TOTAL_MEMORY
        leftAxis.valueFormatter = FormatadorEixoY()
        leftAxis.axisMinimum = 0f

        leftAxis.setDrawGridLines(true)
        val rightAxis = mChart!!.axisRight
        rightAxis.isEnabled = false

        // Add a limit line
        val ll = LimitLine(LIMIT_MAX_MEMORY, "Limite de Pressão")

        ll.lineWidth = 2f
        ll.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        ll.textSize = 10f
        ll.textColor = Color.WHITE
        // reset all limit lines to avoid overlapping lines


        leftAxis.removeAllLimitLines()
        leftAxis.addLimitLine(ll)
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true)

    }

    fun setupData() {
        val data = LineData()
        data.setValueTextColor(Color.WHITE)
        data.isHighlightEnabled


        // add empty data
        mChart!!.data = data
    }

    fun setLegend() {
        // get the legend (only possible after setting data)
        val l = mChart!!.legend

        //l.isEnabled = false
        // modify the legend ...
        l.form = Legend.LegendForm.CIRCLE
        l.textColor = Color.WHITE
    }

    fun createSet(): LineDataSet? {
        val set = LineDataSet(null, "Pressão (Bar)")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.setColors(ColorTemplate.VORDIPLOM_COLORS[0])
        set.lineWidth = 2f
        set.valueTextColor = Color.WHITE
        set.valueTextSize = 10f
        // To show values of each point
        set.setDrawValues(false)
        set.setDrawCircles(false)
        set.valueFormatter = FormatadorEixoX()

        return set
    }

    fun addEntry(stat: Stat) {

        val data = mChart!!.data
        if (data != null) {
            var set = data.getDataSetByIndex(0)
            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }

            data.addEntry(Entry(set!!.entryCount.toFloat(), stat.memory), 0)


            // let the chart know it's data has changed
            data.notifyDataChanged()
            mChart!!.notifyDataSetChanged()

            // limit the number of visible entries
            mChart!!.setVisibleXRangeMaximum(25f)

            // move to the latest entry
            mChart!!.moveViewToX(data.entryCount.toFloat())
        }
    }

    class FormatadorEixoY : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return super.getFormattedValue(value) + " Bar"
        }
    }

    class FormatadorEixoX : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return super.getFormattedValue(value) + " s"
        }
    }

//     override fun onDestroy() {
//        super.onDestroy()
//        pusher!!.disconnect()
//    }
}