package com.booking.app.utils

import com.google.firebase.firestore.FirebaseFirestore

object Constants {

    var collection = FirebaseFirestore.getInstance().collection("Requests")

}