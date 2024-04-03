package com.udacity.asteroidradar.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.dataBase.AsteroidEntity
import com.udacity.asteroidradar.databinding.AsteroidCardViewBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Timer

class AsteroidAdapter(private val clickListener: AsteroidClickListener) :
    ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(AsteroidDiffCallback()) {

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(clickListener,getItem(position)!!)
               holder.itemView.setOnClickListener {
            clickListener.asteroidOnClick(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    class AsteroidViewHolder private constructor(private val binding: AsteroidCardViewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: AsteroidClickListener, item: Asteroid) {
            binding.property = item
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidCardViewBinding.inflate(layoutInflater, parent, false)
                return AsteroidViewHolder(binding)
            }
        }
    }

}

class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}

class AsteroidClickListener(val clickListener: (asteroid : Asteroid) -> Unit){
    fun asteroidOnClick(asteroid: Asteroid) = clickListener(asteroid)
}
