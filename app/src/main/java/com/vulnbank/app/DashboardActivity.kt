package com.vulnbank.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // M9 - Leitura via EncryptedSharedPreferences
        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val prefs = EncryptedSharedPreferences.create(
            this,
            "vulnbank_prefs_secure",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val username = prefs.getString("username", "")

        // M6 - Log sem dados sensíveis
        Log.d("VulnBank", "Dashboard carregado")

        findViewById<TextView>(R.id.tvWelcome).text =
            getString(R.string.welcome_message, username)

        findViewById<Button>(R.id.btnTransfer).setOnClickListener {
            startActivity(Intent(this, TransferActivity::class.java))
        }

        findViewById<Button>(R.id.btnProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
