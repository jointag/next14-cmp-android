package com.next14.cmp.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.next14.cmp.CMPActivityContract
import com.next14.cmp.CMPSdk
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity_main_button.setOnClickListener { presentCMP() }
    }

    override fun onStart() {
        super.onStart()
        CMPSdk.getInstance(this).start("CADD2B2AD06D8A0CAEE658E3C05E615A") { success, error ->
            if (success && CMPSdk.getInstance(this).shouldPresentCMP()) {
                cmpLauncher.launch(true)
            }
        }
    }

    private fun presentCMP() {
        cmpLauncher.launch(true)
    }

    private val cmpLauncher = registerForActivityResult(CMPActivityContract()) { presented ->
        if (presented) {
            Log.d("CMP", "CMP Presented")
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val purposes = preferences.getString("IABTCF_PurposeConsents", "") ?: ""
            Log.d("CMPSample", "Vendor Purposes : $purposes")
            val consents = preferences.getString("IABTCF_VendorConsents", "") ?: ""
            Log.d("CMPSample", "Vendor Consents : $consents")
        }
    }
}
