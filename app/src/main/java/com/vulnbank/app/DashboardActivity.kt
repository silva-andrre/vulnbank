package com.vulnbank.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // M9 - Lendo dados sensíveis do SharedPreferences em texto claro
        val prefs    = getSharedPreferences("vulnbank_prefs", MODE_PRIVATE)
        val username = prefs.getString("username", "")
        val token    = prefs.getString("auth_token", "")
        val password = prefs.getString("password", "")

        // M6 - Expondo dados sensíveis no Logcat
        Log.d("VulnBank", "Dashboard carregado - user: $username token: $token senha: $password")

        findViewById<TextView>(R.id.tvWelcome).text = "Bem-vindo, $username"

        findViewById<Button>(R.id.btnTransfer).setOnClickListener {
            startActivity(Intent(this, TransferActivity::class.java))
        }

        findViewById<Button>(R.id.btnProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
