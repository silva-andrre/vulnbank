package com.vulnbank.app

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

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

        // M1 + M8 - Sem informações sensíveis de infraestrutura
        findViewById<TextView>(R.id.tvProfile).text =
            getString(R.string.profile_username, username)

        // M6 - Log sem dados sensíveis
        Log.d("VulnBank", "Perfil carregado")
    }
}
