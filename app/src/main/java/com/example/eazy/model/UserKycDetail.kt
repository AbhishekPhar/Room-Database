package com.example.eazy.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "UserKYCTable")
data class UserKycDetail(
        @PrimaryKey var user_id: Int,
        var gstin_no: String,
        var pan_no: String,
        var aadhaar_no: String,
        var driving_licence: String,
        var voter_id: String,
        var upi_id: String
        ) {

//    constructor(name: String, email: String, number: String, pincode: String, city: String) {
//        this.name = name
//        this.email = email
//        this.number = number
//        this.pincode = pincode
//        this.city = city
//    }

}