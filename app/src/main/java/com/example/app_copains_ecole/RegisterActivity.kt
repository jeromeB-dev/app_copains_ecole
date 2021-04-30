package com.example.app_copains_ecole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.app_copains_ecole.model.UserBean
import com.example.app_copains_ecole.utils.WsUtils
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    // Déclaration d'un pointeur vers un Button / txt ...
    lateinit var btnValidate: Button
    lateinit var txtPseudo: EditText
    lateinit var txtPassword: EditText
    lateinit var txtGroup: EditText
    lateinit var progressBar: ProgressBar
    lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // bind pointeur et id
        btnValidate = findViewById(R.id.btnValidate)
        txtPseudo = findViewById(R.id.txtPseudo)
        txtPassword = findViewById(R.id.txtPassword)
        txtGroup = findViewById(R.id.txtGroup)
        progressBar = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)

        // Event listener on btn
        btnValidate.setOnClickListener(this)

        // ProgressBar init a false + errorTv a gone + ""
        showProgressBar(false)
        setErrorOnUiThread("")
    }

    override fun onClick(v: View?) {
        when (v) {
            btnValidate -> {
                Log.i("tag_i", "onClick: btnValidate")
                val intent = Intent(this, MapsActivity::class.java)
                val user = UserBean(pseudo = "${txtPseudo.text}", password = "${txtPassword.text}", group_users = 1)

                // ProgressBar le temps du register
                showProgressBar(true)

                // Lance un thread pour ne pas bloquer le thread graphique
                thread {
                    try {
                        WsUtils.register(user)
                        intent.putExtra("user", user)
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.w("tag_w", "${e.message}")
                        setErrorOnUiThread(e.message)
                    }
                }
            }
        }
    }

    /* -------------------------------- */
    // Méthode de mise à jour de l'ihm
    /* -------------------------------- */

    fun setErrorOnUiThread(text: String?) = runOnUiThread {
        if (text.isNullOrBlank()) {
            tvError.visibility = View.GONE
        } else {
            tvError.visibility = View.VISIBLE
        }
        tvError.text = text
    }

    private fun showProgressBar(visible: Boolean) = runOnUiThread {
        progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }
}