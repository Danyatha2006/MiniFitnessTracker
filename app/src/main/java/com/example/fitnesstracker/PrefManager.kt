package com.example.fitnesstracker

import android.content.Context

class PrefManager(context: Context) {

    private val pref = context.getSharedPreferences("FitTrack", Context.MODE_PRIVATE)

    // Save User
    fun saveUser(name: String, email: String) {
        pref.edit()
            .putString("name", name)
            .putString("email", email)
            .apply()
    }

    fun getName(): String {
        return pref.getString("name", "User") ?: "User"
    }

    // Save Steps
    fun saveSteps(steps: Int) {
        pref.edit()
            .putInt("steps", steps)
            .apply()
    }

    fun getSteps(): Int {
        return pref.getInt("steps", 0)
    }

    // FIXED Calories (Int only)
    fun saveCalories(cal: Int) {
        pref.edit()
            .putInt("calories", cal)
            .apply()
    }

    fun getCalories(): Int {
        return pref.getInt("calories", 0)
    }

    // Clear all data
    fun clear() {
        pref.edit()
            .clear()
            .apply()
    }
    // Profile Data
    fun saveProfile(username: String, bio: String) {
        pref.edit()
            .putString("username", username)
            .putString("bio", bio)
            .apply()
    }

    fun getUsername(): String {
        return pref.getString("username", "") ?: ""
    }

    fun getBio(): String {
        return pref.getString("bio", "") ?: ""
    }
}