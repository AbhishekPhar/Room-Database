package com.example.eazy.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.eazy.model.User
import com.example.eazy.model.UserKycDetail

data class UserAndKyc(
        @Embedded val user: User,
        @Relation(
                parentColumn = "user_id",
                entityColumn = "user_id"
        )
        val userKycDetail: UserKycDetail?,
)