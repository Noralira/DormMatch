package com.example.dormmatch.adapters

import android.view.View
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.example.dormmatch.R
import com.squareup.picasso.Picasso

class MyInfoWindowAdapter(val context: Context): InfoWindowAdapter {

    override fun getInfoContents(p0: Marker): View? {
        TODO("Not yet implemented")
    }

    override fun getInfoWindow(p0: Marker): View {

        val infoView: View = LayoutInflater.from(context).inflate(R.layout.custom_info, null)

        val title: TextView = infoView.findViewById(R.id.title_info)
        val desc: TextView = infoView.findViewById(R.id.description_info)
        val image: ImageView = infoView.findViewById(R.id.imagemA_info)

        title.setText(p0.title)
        desc.setText(p0.snippet)

        return infoView

    }
}