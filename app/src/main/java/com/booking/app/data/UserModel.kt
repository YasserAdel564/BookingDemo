package com.booking.app.data

import com.google.firebase.firestore.Exclude

class UserModel(
    @Exclude var sourceLatitude: Int ,
    @Exclude var sourceLongitude: Int,
    @Exclude var destinationLatitude: Int? = null,
    @Exclude var destinationLongitude: Int? = null,
    @Exclude var requestDate: String,
    @Exclude var clientName: String,
    @Exclude var clientPhone: String,
    @Exclude var drivers: List<String>
)
