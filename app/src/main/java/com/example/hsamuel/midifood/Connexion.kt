package com.example.hsamuel.midifood

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_connexion.*

class Connexion : AppCompatActivity(), View.OnClickListener {

    //Données Firebase
    private var mAuth: FirebaseAuth? = null

    //Variables layout
    lateinit var edtTextEmail: EditText
    lateinit var edtTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)
        findViewById<TextView>(R.id.textViewRegister).setOnClickListener(this)

        edtTextEmail = findViewById(R.id.emailEditText)
        edtTextPassword = findViewById(R.id.passwordEditText)


        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View?) {
            when(v!!.id){
                R.id.textViewRegister ->{
                    val intent = Intent(this, Register::class.java)
                    startActivity(intent)
                }
                R.id.button_connexion ->{
                    loginUser()
                }
            }
        }


        private fun loginUser(){
            var email: String = edtTextEmail.text.toString().trim()
            var password: String = edtTextPassword.text.toString().trim()

            validate(email, password)

            showProgress(true)


            if (passwordEditText.text.isEmpty() && isPasswordValid(password)) {
                Log.e(password, R.string.error_password_field_required.toString())

                mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, FoodList::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Email ou Mot de passe incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    private fun showProgress(show: Boolean){
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
    }


        private fun validate(email: String, password: String): Boolean{

            // Voir si le mot de passe est valide, si l'utilisateur en a entrer un.


            // Voir si l'adresse mail est valide.

            return true
        }
        private fun isEmailValid(email: String): Boolean {
            return email.contains("@")
        }

        private fun isPasswordValid(password: String): Boolean {
            return password.length > 8
        }


}
