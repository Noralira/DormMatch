package com.example.dormmatch.fragments

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.dormmatch.R
import com.example.dormmatch.adapters.MyInfoWindowAdapter
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Maps.newInstance] factory method to
 * create an instance of this fragment.
 */
class Maps : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var nMap: GoogleMap
    private lateinit var localizacao: ArrayList<String>
    private lateinit var descricao: ArrayList<String>
    private lateinit var imagem: ArrayList<String>
    private lateinit var titulo: ArrayList<String>
    private lateinit var idPropriedade: ArrayList<String>

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var lastLocation: Location
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        loadStreets()
        createLocationRequest()

        // initialize fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation!!
                val loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    loc,
                    17.0f
                ))
                Log.d("**** TAG", "new location received - " + loc.latitude + " -" + loc.longitude)

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            Maps().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        nMap = googleMap
        val address = "Avenida do Atlântico Viana do Castelo"

        nMap.setInfoWindowAdapter(MyInfoWindowAdapter(requireContext()))

        // ZOOM AND MOVE CAMERA NA LOCALIZAÇÃO ATUAL
        //this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(getCoord(address)))
        //this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getCoord(address), 18.0f))


        for (i in 0 until idPropriedade.size) {
            nMap.addMarker(
                MarkerOptions().position(getCoord(localizacao[i])).title(imagem[i])
                    .snippet(titulo[i] + "&_:_&" + descricao[i])
            )
        }
        setUpMap()
    }

    fun getCoord(address: String): LatLng {
        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocationName(address, 1)
        val lat = list?.get(0)?.latitude
        val lng = list?.get(0)?.longitude

        return LatLng(lat!!, lng!!)
    }

    fun loadStreets() {
        val ref = FirebaseDatabase.getInstance().getReference("propriedade")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val loc = "${snap.child("localizacao").value}"
                        val idP = "${snap.child("idPropriedade").value}"
                        val desc = "${snap.child("descricao").value}"
                        val title = "${snap.child("titulo").value}"
                        val Image = "${snap.child("imagemCapa").value}"

                        localizacao.add(loc)
                        descricao.add(desc)
                        titulo.add(title)
                        idPropriedade.add(idP)
                        imagem.add(Image)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        // interval specifies the rate at which your app will like to receive updates.
        locationRequest.interval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("**** TAG", "onPause - removeLocationUpdates")
    }
     public override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("**** TAG", "onResume - startLocationUpdates")
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }
}
