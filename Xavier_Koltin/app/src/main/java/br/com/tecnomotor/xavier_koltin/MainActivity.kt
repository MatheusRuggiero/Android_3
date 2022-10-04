package br.com.tecnomotor.xavier_koltin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    var buttonConfiguracao : Button? = null
    var buttonDiagnostico : Button? = null
    var buttonGrafico: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        buttonConfiguracao = findViewById(R.id.btnconfiguracao)

        buttonConfiguracao!!.setOnClickListener {


                val intent: Intent? = Intent(this, ActivityPareamento::class.java)
                startActivity(intent)

        }

        buttonDiagnostico = findViewById(R.id.btndiagnostico)

        buttonDiagnostico!!.setOnClickListener {
            val intent: Intent? = Intent(this, ActivityDiagnostico::class.java)
            startActivity(intent)
        }

        buttonGrafico = findViewById(R.id.btngrafico)
        buttonGrafico!!.setOnClickListener {


            val intent: Intent? = Intent(this, GraficoActivity::class.java)
            startActivity(intent)

        }
    }

}
