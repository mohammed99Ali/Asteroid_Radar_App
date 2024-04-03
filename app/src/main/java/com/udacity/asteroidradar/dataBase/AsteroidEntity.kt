package com.udacity.asteroidradar.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

@Entity(tableName = "asteroid_table")
data class AsteroidEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long ,
    @ColumnInfo(name = "code_name")
    val codename: String ,
    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: String ,
    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Double ,
    @ColumnInfo(name = "estimated_diameter")
    val estimatedDiameter: Double ,
    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double ,
    @ColumnInfo(name = "distance_from_earth")
    val distanceFromEarth: Double ,
    @ColumnInfo(name = "is_potentially_hazardous")
    val isPotentiallyHazardous: Boolean
)

fun List<Asteroid>.asAsteroidEntity() : List<AsteroidEntity> {
    return map {
        AsteroidEntity(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
