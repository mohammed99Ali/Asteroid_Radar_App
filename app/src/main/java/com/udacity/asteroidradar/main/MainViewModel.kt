package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.dataBase.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(application: Application) :
    AndroidViewModel(application) {


     val database = AsteroidDatabase.getDatabase(application)
     val repository = AsteroidRepository(database)
    val asteroids = repository.asteroids

    private val _today = MutableLiveData<Boolean>()
    var today: LiveData<Boolean> 
        get() = _today 
        set(value) {_today.value = value.value}

    private val _nextWeek = MutableLiveData<Boolean>()
    var nextWeek: LiveData<Boolean>
        get() = _nextWeek
        set(value) {_nextWeek.value = value.value}

    fun saveValues(today: Boolean,nextWeek: Boolean){
        this._today.value = today
        this._nextWeek.value = nextWeek
    }

    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val status: LiveData<String>
        get() = _status

    private val _statusPic = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val statusPic: LiveData<String>
        get() = _statusPic

    private val _properties = MutableLiveData<List<Asteroid>>()

    val properties: LiveData<List<Asteroid>>
        get() = _properties


    private fun getAsteroidProperties() {
        viewModelScope.launch {
            try {
                val listResult: String = AsteroidApi.retrofitService.getPropertiesAll()
                val resultsAfterParsing: ArrayList<Asteroid> =
                    parseAsteroidsJsonResult(JSONObject(listResult))
                if (listResult.length > 0) {
                    _properties.value = resultsAfterParsing
                }
                _status.value = "Success: ${listResult.length} Asteroid properties retrieved"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    private val _navigateToDetail = MutableLiveData<Asteroid?>()

    val navigateToDetail: MutableLiveData<Asteroid?>
        get() = _navigateToDetail

    fun submitAsteroid(asteroid: Asteroid) {
        _navigateToDetail.value = asteroid
    }

    fun navigated() {
        _navigateToDetail.value = null
    }

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value =  repository.getPictureOfDay()
                _status.value = "Success: the pic is showed"
            } catch (e: Exception) {
                _statusPic.value = "Failure: ${e.message}"
            }
        }
    }

    init {
        getAsteroidProperties()
        getPictureOfDay()
        refreshAsteroidAll()
    }

    fun refreshAsteroidAll(){
        viewModelScope.launch {
            repository.refreshAsteroidAll()
            val listResult: String = AsteroidApi.retrofitService.getPropertiesAll()
            val resultsAfterParsing: ArrayList<Asteroid> =
                parseAsteroidsJsonResult(JSONObject(listResult))
            _properties.value = resultsAfterParsing
        }
    }

    fun refreshAsteroidWeek(){
        viewModelScope.launch {
            repository.refreshAsteroidWeek()
            val listResult: String = AsteroidApi.retrofitService.getPropertiesWeek()
            val resultsAfterParsing: ArrayList<Asteroid> =
                parseAsteroidsJsonResult(JSONObject(listResult))
            _properties.value = resultsAfterParsing        }
    }

    fun refreshAsteroidToday(){
        viewModelScope.launch {
            repository.refreshAsteroidToday()
            val listResult: String = AsteroidApi.retrofitService.getPropertiesToday()
            val resultsAfterParsing: ArrayList<Asteroid> =
                parseAsteroidsJsonResult(JSONObject(listResult))
            _properties.value = resultsAfterParsing        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}

