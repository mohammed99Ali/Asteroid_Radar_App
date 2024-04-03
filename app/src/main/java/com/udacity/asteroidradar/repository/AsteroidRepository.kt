package com.udacity.asteroidradar.repository


import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.dataBase.AsteroidDatabase
import com.udacity.asteroidradar.dataBase.asAsteroidEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import androidx.lifecycle.map
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.asAsteroids


class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        database.asteroidDAO.getAllAsteroids().map {
            it.asAsteroids()
        }

    suspend fun refreshAsteroidAll() {
        withContext(Dispatchers.IO) {
            val asteroidList = AsteroidApi.retrofitService.getPropertiesAll()
                    val resultsAfterParsing: ArrayList<Asteroid> =
                        parseAsteroidsJsonResult(JSONObject(asteroidList))
            database.asteroidDAO.insertAll(*resultsAfterParsing.asAsteroidEntity().toTypedArray())
        }
    }

    suspend fun refreshAsteroidToday() {
        withContext(Dispatchers.IO) {
            val asteroidList = AsteroidApi.retrofitService.getPropertiesToday()
            val resultsAfterParsing: ArrayList<Asteroid> =
                parseAsteroidsJsonResult(JSONObject(asteroidList))
            database.asteroidDAO.insertAll(*resultsAfterParsing.asAsteroidEntity().toTypedArray())
        }
    }
    suspend fun refreshAsteroidWeek() {
        withContext(Dispatchers.IO) {
            val asteroidList = AsteroidApi.retrofitService.getPropertiesWeek()
            val resultsAfterParsing: ArrayList<Asteroid> =
                parseAsteroidsJsonResult(JSONObject(asteroidList))
            database.asteroidDAO.insertAll(*resultsAfterParsing.asAsteroidEntity().toTypedArray())
        }
    }



    suspend fun getPictureOfDay(): PictureOfDay {
        lateinit var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = AsteroidApi.retrofitService.getPictureOfDay()
        }
        return pictureOfDay
    }

}

