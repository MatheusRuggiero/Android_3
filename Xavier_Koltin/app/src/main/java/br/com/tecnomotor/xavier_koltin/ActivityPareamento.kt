package br.com.tecnomotor.xavier_koltin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ActivityPareamento : AppCompatActivity() {
        var listView : ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pareamento)

        listView = findViewById(R.id.listview)

        val dados = arrayOf("Placa Leitora de Pressão", "Rasther")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dados)
        listView!!.adapter = adapter

        title = "Dispositivos"

    }
}