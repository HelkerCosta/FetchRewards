package com.example.fetchrewards.ui.mainpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchrewards.R
import com.example.fetchrewards.adapter.ItemAdapter
import com.example.fetchrewards.databinding.FragmentMainPageBinding
import com.example.fetchrewards.util.Resources

class MainPageFragment: Fragment(R.layout.fragment_main_page) {
    private lateinit var _binding: FragmentMainPageBinding
    private lateinit var mainPageViewModel: MainPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainPageBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainPageViewModel = ViewModelProvider(requireActivity()).get(MainPageViewModel::class.java)

        val recyclerview = _binding.itemRV
        val itemAdapter = ItemAdapter()

        mainPageViewModel.items.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resources.Success -> {
                    _binding.progressBar.visibility = View.INVISIBLE
                    itemAdapter.addHeaderAndList(response.data)
                }
                is Resources.Error -> {
                    _binding.progressBar.visibility = View.INVISIBLE
                    _binding.itemRV.visibility = View.INVISIBLE
                    _binding.noInternetLayout.root.visibility = View.VISIBLE
                    _binding.noInternetLayout.errorTitleTV.text = getString(R.string.error)
                    _binding.noInternetLayout.errorDescriptionTV.text = response.message
                }
                is Resources.Loading -> {
                    _binding.progressBar.visibility = View.VISIBLE
                }
                is Resources.NoInternetConnection -> {
                     _binding.progressBar.visibility = View.INVISIBLE
                    _binding.itemRV.visibility = View.INVISIBLE
                    _binding.noInternetLayout.root.visibility = View.VISIBLE
                }
            }
        }

        recyclerview.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}