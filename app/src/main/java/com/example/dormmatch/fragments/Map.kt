package com.example.dormmatch.fragments

import android.content.ContentValues.TAG
import android.icu.text.Transliterator.Position
import android.location.Geocoder
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings.ZoomDensity
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBindings
import com.example.dormmatch.R
import com.example.dormmatch.adapters.MyInfoWindowAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Map.newInstance] factory method to
 * create an instance of this fragment.
 */
class Map : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var googleMap: GoogleMap
    private lateinit var localizacao: ArrayList<String>
    private lateinit var descricao: ArrayList<String>
    private lateinit var imagem: ArrayList<String>
    private lateinit var titulo: ArrayList<String>
    private lateinit var idPropriedade: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        loadStreets()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Initialize map fragment
        /*val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

        // Async map
        supportMapFragment!!.getMapAsync { googleMap ->
            // When map is loaded
            googleMap.setOnMapClickListener { latLng -> // When clicked on map
                // Initialize marker options
                val markerOptions = MarkerOptions().position(LatLng(0.0, 0.0))
                // Set position of marker
                //markerOptions.position(latLng)
                // Set title of marker
                markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude.toString())
                // Remove all marker
                googleMap.clear()
                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                // Add marker on map
                googleMap.addMarker(markerOptions)
            }
        }*/
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        localizacao = arrayListOf()
        descricao = arrayListOf()
        imagem = arrayListOf()
        titulo = arrayListOf()
        idPropriedade = arrayListOf()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Map.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Map().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMapReady(googleMap: GoogleMap){
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in sydney"))

        val address = "Avenida do Atlântico Viana do Castelo"

        googleMap.setInfoWindowAdapter(MyInfoWindowAdapter(requireContext(), imagem))

        // ZOOM AND MOVE CAMERA NA LOCALIZAÇÃO ATUAL
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(getCoord(address)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getCoord(address), 18.0f))

        for (i in 0 until localizacao.size) {
            googleMap.addMarker(MarkerOptions().position(getCoord(localizacao[i])).title(imagem[i]).snippet(titulo[i] + '_' + descricao[i]))
        }

    }

    fun getCoord(address: String): LatLng{
        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocationName(address, 1)
        val lat = list?.get(0)?.latitude
        val lng = list?.get(0)?.longitude

        return LatLng(lat!!, lng!!)
    }

    fun loadStreets(){
        val ref = FirebaseDatabase.getInstance().getReference("propriedade")
        ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        for(snap in snapshot.children){
                            val loc = "${snap.child("localizacao").value}"
                            val idP = "${snap.child("idPropriedade").value}"
                            val desc = "${snap.child("descricao").value}"
                            val title = "${snap.child("titulo").value}"

                            localizacao.add(loc)
                            descricao.add(desc)
                            titulo.add(title)
                            idPropriedade.add(idP)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        // atraves do codigo do anuncio vou buscar uma imagem a BD
        val ref2 = FirebaseDatabase.getInstance().getReference("foto")
        ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var conta = 0
                    for (anuncioSnap in snapshot.children) {
                        val idP = "${anuncioSnap.child("idPropriedade").value}"
                        for(idProp in idPropriedade){
                            if (idP.equals(idProp)) {
                                val image = "${anuncioSnap.child("imageData").value}"
                                imagem.add(image)
                                conta += 1
                            }
                        }

                    }
                    if (conta == 0) {
                        val image = "https://wallpapers.com/images/featured-full/blank-white-7sn5o1woonmklx1h.jpg"
                        imagem.add(image)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }
}