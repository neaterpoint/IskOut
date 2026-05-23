package com.project.iskout.homepage.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.iskout.R
import com.project.iskout.homepage.map.MapModel
import com.project.iskout.homepage.map.MapPageActivity
import com.project.iskout.utils.BottomNavManager
import com.project.iskout.utils.NavTab

class SpotsListActivity : AppCompatActivity(), SpotsListContract.View {

    private lateinit var rvSpots: RecyclerView
    private lateinit var presenter: SpotsListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spots_list)

        BottomNavManager.setup(this, NavTab.SPOTS)

        rvSpots = findViewById(R.id.rvSpots)
        rvSpots.layoutManager = LinearLayoutManager(this)

        presenter = SpotsListPresenter(this, MapModel())
        presenter.loadSpots()
    }

    override fun showSpots(spots: List<SpotListItem>) {
        // FIX: The adapter now expects the lambda function here
        rvSpots.adapter = SpotsAdapter(spots) { spotItem ->
            val fullSpotList = MapModel().getNearbySpots()
            val selectedSpot = fullSpotList.find { it.id == spotItem.id }

            selectedSpot?.let {
                val intent = Intent(this, MapPageActivity::class.java)
                intent.putExtra("LATITUDE", it.latitude)
                intent.putExtra("LONGITUDE", it.longitude)
                // Clear backstack to ensure the map centers correctly
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun updateFilterUI(selectedFilter: String) {
        // ... (Keep your existing filter UI code)
    }

    override fun showError(message: String) {
        // Handle error
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}