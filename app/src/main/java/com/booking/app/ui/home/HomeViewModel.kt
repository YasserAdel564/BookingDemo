package com.booking.app.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.booking.app.ui.BaseViewModel
import com.booking.app.utils.Constants
import com.booking.app.utils.MyUiStates
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel : BaseViewModel() {


    private var job: Job? = null
    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    public fun checkRequests(driverId: String) {
        if (NetworkUtils.isConnected()) {
            if (NetworkUtils.isConnected()) {
                if (job?.isActive == true)
                    return
                job = launchJob(driverId)
            } else
                _uiState.value = MyUiStates.NoConnection
        }
    }

    private fun launchJob(driverId: String): Job {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiState.value = MyUiStates.Loading }
            Constants.collection
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.e("Success", "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Error", "Error getting documents: ", exception)
                }
        }
    }
}