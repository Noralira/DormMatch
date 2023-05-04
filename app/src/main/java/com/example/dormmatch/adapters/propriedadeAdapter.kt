package com.example.dormmatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dormmatch.R
import com.example.dormmatch.models.propriedade.Propriedade
import com.squareup.picasso.Picasso

class propriedadeAdapter:  RecyclerView.Adapter<propriedadeAdapter.AnnounceListViewHolder>() {

    private val propriedadeList = ArrayList<Propriedade>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnounceListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.announce_line,
            parent,false
        )
        return AnnounceListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.propriedadeList.size
    }

    fun updatePropriedadeList(proplist: List<Propriedade>){
        this.propriedadeList.clear()
        this.propriedadeList.addAll(proplist)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AnnounceListViewHolder, position: Int) {
        //val currentList = annouceL[position]
        val currentList = this.propriedadeList[position]

        holder.title.text = currentList.titulo
        holder.address.text = currentList.localizacao
        holder.rent.text = currentList.preco.toString() + " € / mês"
        holder.rooms.text = currentList.nQuartos.toString() + " Quartos"
        val imagem = "https://st3.idealista.pt/news/arquivos/styles/fullwidth_xl_2x/public/2021-11/isaac-martin-wh2afgo-rt0-unsplash.jpg?VersionId=Thj_5X7rWKL2m9iZZMQcPC6W_E2nBOGo&itok=VSl7gF0m"
        Picasso.get().load(currentList.imagem).into(holder.foto)
        /*db.collection("propriedade")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }*/

    }
    class AnnounceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.title)
        val address: TextView = itemView.findViewById(R.id.address)
        val rent: TextView = itemView.findViewById(R.id.price)
        val rooms: TextView = itemView.findViewById(R.id.roooms)
        val foto: ImageView = itemView.findViewById(R.id.imagemA)

    }
}