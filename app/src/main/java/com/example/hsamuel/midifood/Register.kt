package com.example.hsamuel.midifood

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.ProgressDialog
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.register.*


class Register : AppCompatActivity(), View.OnClickListener {

    //Données Firebase
    private var mAuth: FirebaseAuth? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null

    //variables layout
    private var edtTextName: EditText? = null
    lateinit var edtTextEmail: EditText
    lateinit var edtTextPassword: EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        mAuth = FirebaseAuth.getInstance()
        edtTextEmail = findViewById<View>(R.id.emailTextInput) as EditText
        edtTextPassword = findViewById(R.id.passwordTextInput)
        edtTextName = findViewById<View>(R.id.full_name) as EditText

        findViewById<TextView>(R.id.textViewConnexion).setOnClickListener(this)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Comptes")

    }

    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.button_SignUp ->{
                registerUser()
            }
            R.id.facebook_button ->{
                val intent = Intent(this, FoodList::class.java)
                startActivity(intent)
            }
            R.id.textViewConnexion ->{
                val intent = Intent(this, Connexion::class.java)
                startActivity(intent)
            }
        }
    }


    private fun registerUser(){
        val email: String = edtTextEmail.text.toString().trim()
        val password: String = edtTextPassword.text.toString().trim()
        val name: String = edtTextName!!.text.toString().trim()

        validate(email, password, name)


        mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext, "Inscription réussie", Toast.LENGTH_SHORT).show()
                val user: FirebaseUser = mAuth!!.currentUser!!
                updateUI(user)

                val userId= mAuth!!.currentUser!!.uid

                val currentUserDb = mDatabaseReference!!.child(userId)
                currentUserDb.child("Name").setValue(name)

                val next = Intent(this, FoodList::class.java)
                next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(next)

            }
            else{
                if (task.exception is FirebaseAuthUserCollisionException){
                    Toast.makeText(this, "Vous avez déjà un compte", Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
                updateUI(null)
            }
        }
        showProgress(true)

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
    private fun updateUI(user: FirebaseUser?){
        if (user!=null){
            findViewById<EditText>(R.id.emailTextInput).visibility = View.GONE
            findViewById<EditText>(R.id.passwordTextInput).visibility = View.GONE
            findViewById<LinearLayout>(R.id.Input).visibility = View.VISIBLE
        }else{
            findViewById<EditText>(R.id.emailTextInput).visibility = View.VISIBLE
            findViewById<EditText>(R.id.passwordTextInput).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.Input).visibility = View.GONE
        }
    }

    companion object {

        private fun validate(email: String, password: String, name: String): Boolean{

            //Vérifier le EditText nom_complet
            if (!TextUtils.isEmpty(name)){
                Log.e(password, R.string.error_password_field_required.toString())
            }

            // Voir si le mot de passe est valide, si l'utilisateur en a entrer un.
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                Log.e(password, R.string.error_password_field_required.toString())

            }

            // Voir si l'adresse mail est valide.
            if (TextUtils.isEmpty(email)) {
                Log.e(email, R.string.error_email_field_required.toString())
                return false
            } else if (!isEmailValid(email)) {
                Log.e(email, R.string.error_email_field_required.toString())
            }
            return true
        }
        private fun isEmailValid(email: String): Boolean {
            return email.contains("@")
        }

        private fun isPasswordValid(password: String): Boolean {
            return password.length > 8
        }

    }
}
