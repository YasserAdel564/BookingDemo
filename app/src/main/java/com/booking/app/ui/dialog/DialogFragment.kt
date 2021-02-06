package com.booking.app.ui.dialog

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.booking.app.R
import com.booking.app.databinding.DialogFragmentBinding
import com.booking.app.ui.home.HomeViewModel
import com.booking.app.utils.MyUiStates
import com.booking.app.utils.getLocationName
import com.booking.app.utils.snackBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class DialogFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialog

    lateinit var binding: DialogFragmentBinding

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner, Observer { onResponse(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewsClicks()
    }

    private fun onResponse(states: MyUiStates?) {
        when (states) {
            MyUiStates.Loading -> {

            }
            MyUiStates.NoConnection -> {
            }
            MyUiStates.Success -> {
                fillViews()
            }
            is MyUiStates.Error -> {
                activity?.snackBar(states.message, binding.dialogRoot)
            }

        }
    }

    private fun onViewsClicks() {
        binding.acceptTxt.setOnClickListener { dismiss() }
        binding.rejectTxt.setOnClickListener { dismiss() }
    }

    private fun fillViews() {
        val model = viewModel.requestModel
        binding.userNameTxt.text = model?.clientName
        binding.userPhoneTxt.text = model?.clientPhone
        binding.userPickUpTxt.text = requireActivity().getLocationName(
            model?.sourceLatitude!!,
            model.sourceLongitude!!
        )
    }


}