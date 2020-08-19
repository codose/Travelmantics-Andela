package com.codose.travelmantics_andela.views.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codose.travelmantics_andela.R
import com.codose.travelmantics_andela.repository.MainViewModel
import com.codose.travelmantics_andela.utils.Resource
import com.codose.travelmantics_andela.views.adapter.MainClickListener
import com.codose.travelmantics_andela.views.adapter.MainRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {
    private val viewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var adapter : MainRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTravelmantics()
        adapter = MainRecyclerAdapter(requireContext(), MainClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNewTravelFragment(it))
        })
        main_recycler_view.adapter = adapter
        setUpObservers()
        add_travel_btn.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNewTravelFragment())
        }
    }

    private fun setUpObservers(){
        viewModel.allTravels.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                    empty_layout.visibility = GONE
                }
                is Resource.Success -> {
                    val data = it.data
                    if(data.isEmpty()){
                        empty_layout.visibility = VISIBLE
                    }else{
                        empty_layout.visibility = GONE
                        adapter.submitList(it.data)
                    }
                    hideProgress()
                }
                is Resource.Failure -> {
                    empty_layout.visibility = GONE
                    hideProgress()
                }
            }
        })
    }

    private fun hideProgress() {
        main_progress.visibility = View.GONE
    }

    private fun showProgress() {
        main_progress.visibility = View.VISIBLE
    }
}