package com.booking.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.booking.app.R
import com.booking.app.databinding.HomeFragmentBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.navigation.NavigationView



class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener,
    OnMapReadyCallback, GoogleMap.OnPolylineClickListener  {

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
        viewModel.checkRequests("")

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

        val source = LatLng(31.490127, 74.316971)
        val destination = LatLng(31.474316, 74.316112)

    }


    private fun pointToPosition(position: LatLng) {
        val cameraPosition = CameraPosition.Builder()
            .target(position)
            .zoom(9f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun onViewClicks() {
        binding.toolbarHome.menuImgV.setOnClickListener { openDrawer() }
    }

    private fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun openDialog() {
        findNavController().navigate(R.id.action_home_fragment_to_dialog_fragment)
    }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        binding.drawerLayout.close()
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        return (NavigationUI.onNavDestinationSelected(menuItem, navController)
                || super.onOptionsItemSelected(menuItem))
    }

    override fun onPolylineClick(p0: Polyline?) {
        val polyline1 = mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    LatLng(-35.016, 143.321),
                    LatLng(-34.747, 145.592)
                )
        )
    }



}