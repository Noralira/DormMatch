package com.example.dormmatch.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dormmatch.R
import com.example.dormmatch.ViewRoom
import com.example.dormmatch.adapters.propriedadeAdapter
import com.example.dormmatch.models.propriedade.Propriedade
import com.example.dormmatch.models.propriedade.propriedadeViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
private lateinit var viewModel: propriedadeViewModel
private lateinit var propriedadeRecyclerView: RecyclerView
private lateinit var adapter: propriedadeAdapter

private lateinit var propriedadeArrayList: ArrayList<Propriedade>


class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        propriedadeArrayList = arrayListOf<Propriedade>()

        propriedadeRecyclerView = view.findViewById(R.id.recView)
        propriedadeRecyclerView.layoutManager = LinearLayoutManager(this.context)
        propriedadeRecyclerView.setHasFixedSize(true)
        adapter = propriedadeAdapter(propriedadeArrayList, this)
        propriedadeRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(propriedadeViewModel::class.java)

        viewModel.allPropriedade.observe(viewLifecycleOwner, Observer {

            adapter.updatePropriedadeList(it)

        })
    }

      fun onStudentClickItem(position: Int) {
        val idProp = propriedadeArrayList[position].idPropriedade

        val intent = Intent(context, ViewRoom::class.java)
        intent.putExtra("idPropriedade", idProp)
        startActivity(intent)
    }

}