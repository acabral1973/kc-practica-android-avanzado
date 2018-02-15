package com.keepcoding.madridshops.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.keepcoding.madridshops.R
import com.keepcoding.madridshops.domain.model.Detailable
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_generic_detail.*

class GenericDetailActivity : AppCompatActivity() {

    companion object {

        private val EXTRA_NAME = "EXTRA_NAME"
        private val EXTRA_ADDRESS = "EXTRA_ADDRESS"
        private val EXTRA_HOURES = "EXTRA_HOURES"
        private val EXTRA_IMAGE = "EXTRA_IMAGE"
        private val EXTRA_LOGO = "EXTRA_LOGO"
        private val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
        lateinit var context: Context

        fun <T: Detailable> intent(context: Context, content: T): Intent {

            val intent = Intent(context, GenericDetailActivity::class.java)

            intent.putExtra(EXTRA_NAME, content.get_Name())
            intent.putExtra(EXTRA_ADDRESS, content.get_Address())
            intent.putExtra(EXTRA_DESCRIPTION, content.get_Description())
            intent.putExtra(EXTRA_HOURES, content.get_Houres())
            intent.putExtra(EXTRA_LOGO, content.get_logo())
            this.context = context
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic_detail)

        val nameExtra = intent.getStringExtra(EXTRA_NAME)
        val addressExtra = intent.getStringExtra(EXTRA_ADDRESS)
        val houresExtra = intent.getStringExtra(EXTRA_HOURES)
        val descriptionExtra = intent.getStringExtra(EXTRA_DESCRIPTION)
        val logoExtra = intent.getStringExtra(EXTRA_LOGO)

        supportActionBar?.title = "Detalles"
        Picasso.with(context).load(logoExtra).placeholder(android.R.drawable.alert_dark_frame).into(logo)
        name.text = nameExtra
        address.text = addressExtra
        houres.text = houresExtra
        description.text = descriptionExtra
    }
}




