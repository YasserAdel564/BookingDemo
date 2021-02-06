package com.booking.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.booking.app.R
import com.booking.app.databinding.HomeFragmentBinding
import com.booking.app.utils.MyUiStates
import com.booking.app.utils.snackBar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.navigation.NavigationView


class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener,
    OnMapReadyCallback {

    lateinit var binding: HomeFragmentBinding
    private lateinit var mMap: GoogleMap

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_host) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.navViewHome.setNavigationItemSelectedListener(this)
        viewModel.uiState.observe(viewLifecycleOwner, Observer { onResponse(it) })
        viewModel.checkRequests("10")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewClicks()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.map_dark_mood));
        val cairo = LatLng(30.033333, 31.233334)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cairo));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14f), 1000, null)
    }


    private fun pointToPosition(positionSource: LatLng, positionDest: LatLng?) {
        val markerOptions = MarkerOptions()
        mMap.moveCamera(CameraUpdateFactory.newLatLng(positionSource));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14f), 1000, null)
        markerOptions.position(positionSource)
        mMap.addMarker(markerOptions)
        if (positionDest != null) {
            val list: ArrayList<LatLng> = arrayListOf()
            list.add(positionSource)
            list.add(positionDest)
            val polyline1: Polyline = mMap.addPolyline(
                PolylineOptions()
                    .clickable(true)
                    .addAll(list)
            )
            polyline1.color = ContextCompat.getColor(requireActivity(), R.color.colorAccent)
        }

    }

    private fun onViewClicks() {
        binding.toolbarHome.menuImgV.setOnClickListener { openDrawer() }
    }

    private fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun openDialog() {
        findNavController().navigate(R.id.dialog_fragment)
    }


    private fun onResponse(states: MyUiStates?) {
        when (states) {
            MyUiStates.Loading -> {

            }
            MyUiStates.NoConnection -> {
            }
            MyUiStates.Success -> {
                openDialog()
                var positionDest: LatLng? = null
                val positionSource = LatLng(
                    viewModel.requestModel?.sourceLatitude!!,
                    viewModel.requestModel?.sourceLongitude!!
                )
                if (viewModel.requestModel?.destinationLatitude != null)
                    positionDest = LatLng(
                        viewModel.requestModel?.destinationLatitude!!,
                        viewModel.requestModel?.destinationLongitude!!
                    )
                pointToPosition(positionSource, positionDest)

            }
            is MyUiStates.Error -> {
                activity?.snackBar(states.message, binding.drawerLayout)
            }

        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        binding.drawerLayout.close()
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        return (NavigationUI.onNavDestinationSelected(menuItem, navController)
                || super.onOptionsItemSelected(menuItem))
    }


}