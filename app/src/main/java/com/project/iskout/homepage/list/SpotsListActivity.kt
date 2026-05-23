package com.project.iskout.homepage.list

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.iskout.R
import com.project.iskout.homepage.map.MapModel
import com.project.iskout.utils.BottomNavManager
import com.project.iskout.utils.NavTab

class SpotsListActivity : AppCompatActivity(), SpotsListContract.View {

    private lateinit var rvSpots: RecyclerView
    private lateinit var presenter: SpotsListContract.Presenter

    private lateinit var chipNearest: TextView
    private lateinit var chipTopRated: TextView
    private lateinit var chipCheapest: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spots_list)

        BottomNavManager.setup(this, NavTab.SPOTS)

        rvSpots = findViewById(R.id.rvSpots)
        rvSpots.layoutManager = LinearLayoutManager(this)

        chipNearest = findViewById(R.id.chipNearest)
        chipTopRated = findViewById(R.id.chipTopRated)
        chipCheapest = findViewById(R.id.chipCheapest)

        // Initialize Presenter with the universal MapModel
        presenter = SpotsListPresenter(this, MapModel())

        // Set Filter Click Listeners
        chipNearest.setOnClickListener { presenter.onFilterClicked("Nearest") }
        chipTopRated.setOnClickListener { presenter.onFilterClicked("Top rated") }
        chipCheapest.setOnClickListener { presenter.onFilterClicked("Cheapest") }

        // Initial Data Fetch
        presenter.loadSpots()
    }

    override fun showSpots(spots: List<SpotListItem>) {
        rvSpots.adapter = SpotsAdapter(spots)
    }

    override fun updateFilterUI(selectedFilter: String) {
        val chips = mapOf(
            "Nearest" to chipNearest,
            "Top rated" to chipTopRated,
            "Cheapest" to chipCheapest
        )

        val activeColor = Color.WHITE
        val inactiveColor = Color.parseColor("#0F172A")

        chips.forEach { (type, chip) ->
            if (type == selectedFilter) {
                // Set to Blue Active State
                chip.setBackgroundResource(R.drawable.bg_chip_active)
                chip.setTextColor(activeColor)
                chip.compoundDrawableTintList = ColorStateList.valueOf(activeColor)
            } else {
                // Set to White/Grey Inactive State
                chip.setBackgroundResource(R.drawable.bg_chip_inactive)
                chip.setTextColor(inactiveColor)
                chip.compoundDrawableTintList = ColorStateList.valueOf(inactiveColor)
            }
        }
    }

    override fun showError(message: String) {
        // Handle error
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}