package com.booking.app.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

class UserModel(
    @Exclude var sourceLatitude: Double? = null,
    @Exclude var sourceLongitude: Double? = null,
    @Exclude var destinationLatitude: Double? = null,
    @Exclude var destinationLongitude: Double? = null,
    @Exclude var requestDate: Timestamp? = null,
    @Exclude var clientName: String = "",
    @Exclude var clientPhone: String = "",
    @Exclude var drivers: List<String>? = null
)
