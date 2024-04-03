package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates

import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.recycle.AsteroidAdapter
import com.udacity.asteroidradar.recycle.AsteroidClickListener

class MainFragment<Asteroid> : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)

            val application = requireNotNull(this.activity).application
            val viewModel = ViewModelProvider(this,MainViewModel.Factory(application)).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = AsteroidAdapter(AsteroidClickListener { viewModel.submitAsteroid(it)

            })

        binding.asteroidRecycler.adapter = adapter


        viewModel.properties.observe(viewLifecycleOwner, Observer {asteroids ->
            adapter.submitList(asteroids)
        })


            viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.navigated()
            }
        })


        setHasOptionsMenu(true)

        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val application = requireNotNull(this.activity).application
        val viewModel = ViewModelProvider(this,MainViewModel.Factory(application)).get(MainViewModel::class.java)

        when (item.itemId) {
            R.id.show_all_menu -> {
                //Display the next seven days asteroids (not including today)
                viewModel.refreshAsteroidWeek()

            }
            R.id.show_rent_menu -> {
                //Display today's asteroids only
                viewModel.refreshAsteroidToday()
            }
            R.id.show_buy_menu -> {
                //Display today's asteroids + the next seven days asteroids
                viewModel.refreshAsteroidAll()
            }
        }
        return true
    }

    }
