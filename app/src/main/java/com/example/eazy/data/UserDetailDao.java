package com.example.eazy.data;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.eazy.model.User;
import com.example.eazy.model.relations.UserAndKyc;

import java.util.List;

@Dao
public interface UserDetailDao {

    @Query("SELECT * FROM UserDetailsTable ORDER BY user_id")
    List<User> loadAllPersons();

    @Insert
    void insertPerson(User person);

    @Update
    void updatePerson(User person);

    @Delete
    void delete(User person);

    @Query("SELECT * FROM UserDetailsTable WHERE user_id = :user_id")
    User loadPersonById(int user_id);

    @Transaction
    @Query("SELECT * FROM UserDetailsTable WHERE user_id = :user_id")
    UserAndKyc getUserAndKycDetail(int user_id);
}
