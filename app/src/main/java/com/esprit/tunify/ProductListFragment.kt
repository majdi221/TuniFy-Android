package com.esprit.tunify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esprit.tunify.adapter.ProductsRecyclerViewAdapter
import com.esprit.tunify.model.Product
import com.esprit.tunify.viewmodel.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ProductListFragment : Fragment() {

    private var items :Collection<Product> = ArrayList<Product>()


    private lateinit var rv: RecyclerView
    private lateinit var productAdapter : ProductsRecyclerViewAdapter


    private var x: Int = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)

        val cs = view.findViewById<TextView>(R.id.cart_size)
        cs.text = CartFragment.getShoppingCartSize().toString()



        val fab = view.findViewById<FloatingActionButton>(R.id.basketButton)
        fab.setOnClickListener {
            val cartFragment = CartFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayout, cartFragment)
            transaction.commit()
        }

        initViewModel(view)
        initViewModel()
        return view
    }

    private fun initViewModel(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)


        this.rv = recyclerView


        recyclerView.layoutManager = GridLayoutManager(activity, 2)//LinearLayoutManager(activity)
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
        productAdapter = ProductsRecyclerViewAdapter{position -> onItemClick(position)
            this.x = position
        }
        recyclerView.adapter = productAdapter

    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getProductListObserver()
            .observe(viewLifecycleOwner) {
                if (it != null) {
                    this.items=it
                    productAdapter.setUpdatedData(it)
                } else {
                    Toast.makeText(activity, "no data", Toast.LENGTH_SHORT).show()
                }
            }
        viewModel.makeApiCall()
    }

    private fun onItemClick(position: Int) {

        Toast.makeText(activity, items.elementAt(position).name, Toast.LENGTH_SHORT).show()
    }




    companion object {

        @JvmStatic
        fun newInstance() =
            ProductListFragment()
    }



}