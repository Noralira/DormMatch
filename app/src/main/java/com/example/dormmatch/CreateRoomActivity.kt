package com.example.dormmatch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dormmatch.databinding.CriarEditarAnuncioBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CreateRoomActivity : AppCompatActivity() {
    private lateinit var binding: CriarEditarAnuncioBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var nameCategoria: ArrayList<String>
    private lateinit var cat : String
    override fun onCreate(savedInstanceState: Bundle?) {
        cat = ""
        nameCategoria = arrayListOf()
        listaCategoria()
        super.onCreate(savedInstanceState)
        binding = CriarEditarAnuncioBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        FirebaseApp.initializeApp(this)

        binding.btnFicheiros.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            //imageActivity.launch()
        }

        //val categorias = nameCategoria

        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nameCategoria)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            /*spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                    cat = nameCategoria[position]
                    Log.d("*** TAG", cat.toString())

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }*/
        }
    }

    fun btnCriar(view: View) {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    fun btnVoltar(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    fun usarCamara(view: View) {

    }

    fun selecionarFicheiros(view: View) {
    }

    fun listaCategoria() {
        val refCat = FirebaseDatabase.getInstance().getReference("categorias")
        refCat.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (anuncioSnap in snapshot.children) {
                        val name = "${anuncioSnap.child("descricao").value}"
                        nameCategoria.add(name)
                        Log.d("*** TAG", nameCategoria.toString())
                    }
                }
            }


                override fun onCancelled(error: DatabaseError) {

        }
    })
    }

    //private val imageActivity = registerForActivityResult(ActivityResultContracts.StartActivityResult())


}