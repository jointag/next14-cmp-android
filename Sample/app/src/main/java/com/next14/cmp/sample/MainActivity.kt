package com.next14.cmp.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.next14.cmp.CMPActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity_main_button.setOnClickListener { presentCMP() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Consent Asked
            val purposes = PreferenceManager.getDefaultSharedPreferences(this).getString("IABTCF_PurposeConsents", "") ?: ""
            Log.d("CMPSample", "Vendor Purposes : $purposes")
            val consents = PreferenceManager.getDefaultSharedPreferences(this).getString("IABTCF_VendorConsents", "") ?: ""
            Log.d("CMPSample", "Vendor Consents : $consents")
        }
    }

    override fun onStart() {
        super.onStart()
        if (CMPActivity.shouldPresentCMP(this)) {
            presentCMP()
        }
    }

    private fun presentCMP() {
        CMPActivity.start(this, "CADD2B2AD06D8A0CAEE658E3C05E615A", true, REQUEST_CODE)
    }

    companion object {
        const val REQUEST_CODE = 100
    }
}
