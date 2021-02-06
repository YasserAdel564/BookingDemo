package com.booking.app.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.booking.app.data.UserModel
import com.booking.app.ui.BaseViewModel
import com.booking.app.utils.Constants
import com.booking.app.utils.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class HomeViewModel : BaseViewModel() {


    private var job: Job? = null
    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState


    var requestModel: UserModel? = null

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
            Constants.collection.addSnapshotListener { snapshot, e ->
                if (snapshot != null)
                    for (document in snapshot.documents) {
                        val request: UserModel = document.toObject(UserModel::class.java)!!
                        if (request.drivers?.contains(driverId) == true) {
                            requestModel = request
                            _uiState.value = MyUiStates.Success
                        }
                    }
            }


        }
    }
}