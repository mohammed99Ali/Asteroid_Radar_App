package com.udacity.asteroidradar.recycle

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.dataBase.AsteroidEntity

@BindingAdapter("asteroidName")
fun TextView.setAsteroidName(item : Asteroid?){
    item?.let{
        text = item.codename
    }
}

@BindingAdapter("asteroidDate")
fun TextView.setAsteroidDAte(item : Asteroid?){
    item?.let{
        text = item.closeApproachDate
    }
}

@BindingAdapter("asteroidSafetyImage")
fun ImageView.setAsteroidSafetyImage(item : Asteroid?){
    item?.let{
        if (!item.isPotentiallyHazardous)
            setImageResource(R.drawable.ic_status_normal)
        else
            setImageResource(R.drawable.ic_status_potentially_hazardous)
    }    }

