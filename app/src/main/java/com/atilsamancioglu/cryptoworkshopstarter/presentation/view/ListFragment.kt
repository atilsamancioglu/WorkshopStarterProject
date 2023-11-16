package com.atilsamancioglu.cryptoworkshopstarter.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atilsamancioglu.cryptoworkshopstarter.domain.model.CryptoModel
import com.atilsamancioglu.cryptoworkshopstarter.databinding.FragmentListBinding
import com.atilsamancioglu.cryptoworkshopstarter.data.service.CryptoAPI
import com.atilsamancioglu.cryptoworkshopstarter.presentation.viewmodel.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class ListFragment : Fragment(), RecyclerViewAdapter.Listener {

    private var _binding: FragmentListBinding? = null
    private val binding get()= _binding!!
    private var cryptoAdapter = RecyclerViewAdapter(arrayListOf(),this)
    private lateinit var viewModel : CryptoViewModel

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

        viewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager


        viewModel.loadData()

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.cryptoList.observe(viewLifecycleOwner, Observer {cryptos ->
            binding.cryptoErrorText.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            cryptoAdapter = RecyclerViewAdapter(ArrayList(cryptos.data ?: arrayListOf()), this@ListFragment)
            binding.recyclerView.adapter = cryptoAdapter
        })


        viewModel.cryptoError.observe(viewLifecycleOwner, Observer {error ->
            error.data?.let {
                if (it) {
                    binding.cryptoErrorText.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.cryptoErrorText.visibility = View.GONE
                }
            }

        })

        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading.data?.let {
                if(it) {
                    binding.cryptoProgressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.cryptoErrorText.visibility = View.GONE
                } else {
                    binding.cryptoProgressBar.visibility = View.GONE
                }
            }

        })
    }


    override fun onItemClick(cryptoModel: CryptoModel) {
            Toast.makeText(requireContext(),"Clicked on: ${cryptoModel.currency}",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}