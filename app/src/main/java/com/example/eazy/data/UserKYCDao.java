package com.example.eazy.data;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eazy.model.User;
import com.example.eazy.model.UserKycDetail;

import java.util.List;

@Dao
public interface UserKYCDao {

    @Query("SELECT * FROM UserKYCTable ORDER BY user_id")
    List<UserKycDetail> loadAllPersons();

    @Insert
    void insertPerson(UserKycDetail person);

    @Update
    void updatePerson(UserKycDetail person);

    @Delete
    void delete(UserKycDetail person);

    @Query("SELECT * FROM UserKYCTable WHERE user_id = :user_id")
    UserKycDetail loadPersonById(int user_id);
}
