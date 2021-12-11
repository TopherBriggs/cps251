package com.example.contactsproject.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import com.example.contactsproject.R

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsproject.Product
import androidx.fragment.app.viewModels

import java.util.*

import com.example.contactsproject.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private var adapter: ProductListAdapter? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    val viewModel: MainViewModel by viewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listenerSetup()
        observerSetup()
        recyclerSetup()

    }

    private fun listenerSetup() {

        binding.addButton.setOnClickListener {
            val name = binding.contactName.text.toString()
            val quantity = binding.phoneNumber.text.toString()

            if (name != "" && quantity != "") {
                val product = Product(name, quantity)
                viewModel.insertProduct(product)
                clearFields()
            } else {
                binding.productID.text = "Incomplete information"
            }
        }

        binding.findButton.setOnClickListener { viewModel.findProduct(binding.contactName.text.toString()) }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteProduct(binding.contactName.text.toString())
            clearFields()
        }
    }

    private fun observerSetup() {

        viewModel.getAllProducts()?.observe(this, Observer { products ->
            products?.let  {
                adapter?.setProductList(it)
            }
        })

        viewModel.getSearchResults().observe(this, Observer { products ->

            products?.let {
                if (it.isNotEmpty()) {
                    binding.productID.text = it[0].id.toString()
                    binding.contactName.setText(it[0].contactName)
                    binding.phoneNumber.setText(it[0].phoneNumber);
//                    binding.productID.text = String.format(Locale.US, "%d", it[0].id)
//                    binding.contactName.setText(it[0].contactName)
//                    binding.phoneNumber.setText(String.format(Locale.US, "%d",
//                        it[0].phoneNumber))
                } else {
                    binding.productID.text = "No Match"
                }
            }
        })
    }

    private fun recyclerSetup() {
        adapter = ProductListAdapter(R.layout.product_list_item)
        binding.productRecycler.layoutManager = LinearLayoutManager(context)
        binding.productRecycler.adapter = adapter
    }

    private fun clearFields() {
        binding.productID.text = ""
        binding.contactName.setText("")
        binding.phoneNumber.setText("")
    }
}