package com.example.dormmatch.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dormmatch.models.propriedade.Propriedade
import com.example.dormmatch.models.propriedade.propriedadeViewModel
import com.google.firebase.database.*

class propriedadeRepository {

    private val databaseReference = FirebaseDatabase.getInstance().getReference("propriedade")

    @Volatile private var INSTANCE : propriedadeRepository ?= null

    fun getInstance() : propriedadeRepository{
        return INSTANCE ?: synchronized(this){

            val instance = propriedadeRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadPropriedade(propriedadeList: MutableLiveData<List<Propriedade>>){

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try{
                    val _propriedadeList: List<Propriedade> = snapshot.children.map{ dataSnapshot ->
                        dataSnapshot.getValue(Propriedade::class.java)!!
                    }
                    propriedadeList.postValue(_propriedadeList)
                }catch (e: Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}