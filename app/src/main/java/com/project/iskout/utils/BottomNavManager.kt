package com.project.iskout.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.project.iskout.R
import com.project.iskout.deals.DealsActivity
import com.project.iskout.homepage.list.SpotsListActivity
import com.project.iskout.homepage.map.MapPageActivity
import com.project.iskout.profile.ProfileActivity

enum class NavTab { MAP, SPOTS, DEALS, PROFILE }

object BottomNavManager {

    fun setup(activity: Activity, currentTab: NavTab) {
        val navMap = activity.findViewById<LinearLayout>(R.id.navMap)
        val navSpots = activity.findViewById<LinearLayout>(R.id.navSpots)
        val navDeals = activity.findViewById<LinearLayout>(R.id.navDeals)
        val navProfile = activity.findViewById<LinearLayout>(R.id.navProfile)

        // 1. Setup Click Listeners (Only navigate if they click a DIFFERENT tab)
        navMap.setOnClickListener { if (currentTab != NavTab.MAP) navigate(activity, MapPageActivity::class.java) }
        navSpots.setOnClickListener { if (currentTab != NavTab.SPOTS) navigate(activity, SpotsListActivity::class.java) }
        navDeals.setOnClickListener { if (currentTab != NavTab.DEALS) navigate(activity, DealsActivity::class.java) }
        navProfile.setOnClickListener { if (currentTab != NavTab.PROFILE) navigate(activity, ProfileActivity::class.java) }

        // 2. Reset all UI to inactive state
        resetTabUI(navMap)
        resetTabUI(navSpots)
        resetTabUI(navDeals)
        resetTabUI(navProfile)

        // 3. Highlight the currently active tab
        when (currentTab) {
            NavTab.MAP -> highlightTabUI(navMap)
            NavTab.SPOTS -> highlightTabUI(navSpots)
            NavTab.DEALS -> highlightTabUI(navDeals)
            NavTab.PROFILE -> highlightTabUI(navProfile)
        }
    }

    private fun navigate(activity: Activity, destination: Class<*>) {
        val intent = Intent(activity, destination)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        activity.startActivity(intent)
        activity.finish()
        activity.overridePendingTransition(0, 0)
    }

    private fun resetTabUI(tabLayout: LinearLayout) {
        val icon = tabLayout.getChildAt(0) as ImageView
        val text = tabLayout.getChildAt(1) as TextView

        icon.background = null // Remove the white pill background
        icon.setColorFilter(Color.parseColor("#A0C4D3")) // Set icon to pale blue

        // Reset icon size to default 24dp for inactive tabs
        val layoutParams = icon.layoutParams
        layoutParams.width = (24 * tabLayout.resources.displayMetrics.density).toInt()
        layoutParams.height = (24 * tabLayout.resources.displayMetrics.density).toInt()
        icon.layoutParams = layoutParams
        icon.setPadding(0, 0, 0, 0)

        text.setTextColor(Color.parseColor("#A0C4D3")) // Set text to pale blue
    }

    private fun highlightTabUI(tabLayout: LinearLayout) {
        val icon = tabLayout.getChildAt(0) as ImageView
        val text = tabLayout.getChildAt(1) as TextView

        icon.setBackgroundResource(R.drawable.bg_nav_active) // Add white pill background
        icon.setColorFilter(Color.parseColor("#22708E")) // Set icon to dark blue

        // Expand icon container slightly to fit the pill background nicely (48x40dp)
        val layoutParams = icon.layoutParams
        layoutParams.width = (48 * tabLayout.resources.displayMetrics.density).toInt()
        layoutParams.height = (40 * tabLayout.resources.displayMetrics.density).toInt()
        icon.layoutParams = layoutParams
        val padding = (8 * tabLayout.resources.displayMetrics.density).toInt()
        icon.setPadding(padding, padding, padding, padding)

        text.setTextColor(Color.WHITE) // Set text to white
    }
}