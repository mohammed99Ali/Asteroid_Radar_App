package com.udacity.asteroidradar.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: AsteroidEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroidEntity: AsteroidEntity)

    @Update
    suspend fun update(asteroid: AsteroidEntity)

    @Query("SELECT * from asteroid_table WHERE id = :key")
    suspend fun get(key: Long): AsteroidEntity?

    @Query("DELETE FROM asteroid_table")
    suspend fun clear()

    @Query("SELECT * FROM asteroid_table ORDER BY id DESC")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("SELECT code_name FROM asteroid_table where id = :key")
    fun getAsteroidName(key: Long): LiveData<String>

    @Query("SELECT close_approach_date FROM asteroid_table where id = :key")
    fun getAsteroidCloseApproachDate(key: Long): LiveData<String>

    @Query("SELECT is_potentially_hazardous FROM asteroid_table where id = :key")
    fun isPotentiallyHazardous(key: Long): LiveData<Boolean>
}

