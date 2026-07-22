package com.example.fitnesstracker

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

data class WorkoutHistory(
    val date: String,
    val steps: Int,
    val calories: Int
)

class PrefManager(context: Context) {

    private val PREF_NAME = "fitness_prefs"
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // Profile Management
    fun setUsername(name: String) {
        sharedPref.edit().putString("username", name).apply()
    }

    fun getUsername(): String {
        return sharedPref.getString("username", "User") ?: "User"
    }

    fun setBio(bio: String) {
        sharedPref.edit().putString("bio", bio).apply()
    }

    fun getBio(): String {
        return sharedPref.getString("bio", "") ?: ""
    }

    // Daily Stats Management
    fun saveSteps(steps: Int) {
        sharedPref.edit().putInt("today_steps", steps).apply()
    }

    fun getTodaySteps(): Int {
        return sharedPref.getInt("today_steps", 0)
    }

    fun saveCalories(calories: Int) {
        sharedPref.edit().putInt("today_calories", calories).apply()
    }

    fun getTodayCalories(): Int {
        return sharedPref.getInt("today_calories", 0)
    }

    fun resetTodayWorkout() {
        sharedPref.edit()
            .putInt("today_steps", 0)
            .putInt("today_calories", 0)
            .apply()
    }

    // Save end-of-day workout to history
    fun saveEndOfDayWorkout(steps: Int, calories: Int) {
        val today = getTodayDate()
        val value = "$steps|$calories"
        sharedPref.edit().putString("history_$today", value).apply()
    }

    // Get previous day's workout from history
    fun getPreviousDayWorkout(): WorkoutHistory? {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        val yesterday = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)

        val value = sharedPref.getString("history_$yesterday", null) ?: return null
        val parts = value.split("|")
        val steps = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val calories = parts.getOrNull(1)?.toIntOrNull() ?: 0

        return if (steps == 0 && calories == 0) null
        else WorkoutHistory(yesterday, steps, calories)
    }

    private fun getTodayDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }
}