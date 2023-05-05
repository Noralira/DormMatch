package com.example.dormmatch

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.dormmatch.databinding.ViewRoomBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ViewRoom : AppCompatActivity() {
    private lateinit var binding: ViewRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewRoomBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val idPropriedade = intent.getStringExtra("idPropriedade")

        loadAnuncio(idPropriedade!!)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.btnCall.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_DIAL)
                    .setData(Uri.parse("tel:" + binding.contacto.text))
            )
        }

    }

    private fun loadAnuncio(idPropriedade: String) {
        val ref = FirebaseDatabase.getInstance().getReference("propriedade")
        ref.child(idPropriedade)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    //carrega os dados
                    val titulo = "${snapshot.child("titulo").value}"
                    val localizacao = "${snapshot.child("localizacao").value}"
                    val preco = "${snapshot.child("preco").value}"
                    val descricao = "${snapshot.child("descricao").value}"
                    val telemovel = "${snapshot.child("telemovel").value}"
                    val imagem = "${snapshot.child("imagem").value}"
                    val tipo = "${snapshot.child("tipo").value}"
                    val arCondicionado = "${snapshot.child("arCondicionado").value}"
                    val wifi = "${snapshot.child("wifi").value}"
                    val mobilia = "${snapshot.child("mobilia").value}"
                    val maquilaLavar = "${snapshot.child("maquinaLavar").value}"

                    //coloca os dados
                    binding.roomTitle.text = titulo
                    binding.roomTitle2.text = titulo
                    binding.location.text = localizacao
                    binding.price.text = preco + " € / mês"
                    binding.type.text = "Tipo: " + tipo
                    binding.description.text = descricao
                    binding.contacto.text = telemovel
                    Picasso.get().load(imagem).into(binding.imagemA)

                    if (arCondicionado.toString().equals("true")) {
                        binding.LlArCondicionado.visibility = View.VISIBLE
                    } else {
                        binding.LlArCondicionado.visibility = View.GONE
                    }
                    if (wifi.toString().equals("true")) {
                        binding.LlWifi.visibility = View.VISIBLE
                    } else {
                        binding.LlWifi.visibility = View.GONE
                    }
                    if (mobilia.toString().equals("true")) {
                        binding.LlMobilia.visibility = View.VISIBLE
                    } else {
                        binding.LlMobilia.visibility = View.GONE
                    }
                    if (maquilaLavar.toString().equals("true")) {
                        binding.LlMaquinaLavar.visibility = View.VISIBLE
                    } else {
                        binding.LlMaquinaLavar.visibility = View.GONE
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}