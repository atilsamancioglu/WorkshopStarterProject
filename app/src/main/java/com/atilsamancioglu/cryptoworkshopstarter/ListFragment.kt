package com.atilsamancioglu.cryptoworkshopstarter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atilsamancioglu.cryptoworkshopstarter.databinding.FragmentListBinding

class ListFragment : Fragment(), RecyclerViewAdapter.Listener {

    private var _binding: FragmentListBinding? = null
    private val binding get()= _binding!!
    private var cryptoAdapter = RecyclerViewAdapter(arrayListOf(),this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        cryptoAdapter = RecyclerViewAdapter(arrayListOf(
            CryptoModel("Test","10"),
            CryptoModel("Test 2", "20"),
            CryptoModel("Test 3", "30"),
            CryptoModel("Test 4", "40"),
            CryptoModel("Test 5", "50"),
        ),this@ListFragment)
        binding.recyclerView.adapter = cryptoAdapter
        binding.cryptoErrorText.visibility = View.GONE
        binding.cryptoProgressBar.visibility = View.GONE
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
            Toast.makeText(requireContext(),"Clicked on: ${cryptoModel.currency}",Toast.LENGTH_SHORT).show()
    }

}