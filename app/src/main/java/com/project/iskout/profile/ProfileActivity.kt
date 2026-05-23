package com.project.iskout.profile

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.project.iskout.R
import com.project.iskout.core.SessionManager
import com.project.iskout.profile.list.ProfileListActivity
import com.project.iskout.utils.BottomNavManager
import com.project.iskout.utils.NavTab

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        BottomNavManager.setup(this, NavTab.PROFILE)

        // 1. Bind Session Data to UI
        val tvName = findViewById<TextView>(R.id.tvName) // Make sure to add this ID to activity_profile.xml
        val tvSchool = findViewById<TextView>(R.id.tvSchoolInfo)
        val tvAvatarInitials = findViewById<TextView>(R.id.tvAvatar)

        val user = SessionManager.currentUser
        if (user != null) {
            tvName.text = SessionManager.getDisplayName()
            tvSchool.text = SessionManager.getAcademicInfo()

            // Set initials
            val initials = user.full_name.split(" ").map { it.take(1) }.take(2).joinToString("").uppercase()
            tvAvatarInitials.text = initials
        }

        // Menu Click Listeners...
        findViewById<LinearLayout>(R.id.llSavedSpots).setOnClickListener { openSubList("SAVED") }
        findViewById<LinearLayout>(R.id.llClaimedDeals).setOnClickListener { openSubList("DEALS") }
        findViewById<LinearLayout>(R.id.llMyReviews).setOnClickListener { openSubList("REVIEWS") }
        findViewById<LinearLayout>(R.id.llRecentVisits).setOnClickListener { openSubList("RECENT") }
    }

    private fun openSubList(listType: String) {
        val intent = Intent(this, ProfileListActivity::class.java)
        intent.putExtra("LIST_TYPE", listType)
        startActivity(intent)
    }
}