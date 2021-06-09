package com.example.eazy.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserDetailsTable")
data class User(  @PrimaryKey var user_id: Int,
                  var user_name: String,
                  var email_id: String,
                  var contact_no: String,
                  var date_of_birth: String,
                  var address: String,
                  var city: String,
                  var state: String,
                  var country: String,
                  var Pincode: String,
) {
}