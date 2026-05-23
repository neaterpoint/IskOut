package com.project.iskout.deals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.iskout.R
import com.project.iskout.homepage.map.MapModel
import com.project.iskout.utils.BottomNavManager
import com.project.iskout.utils.NavTab

class DealsActivity : AppCompatActivity(), DealsContract.View {

    private lateinit var rvDeals: RecyclerView
    private lateinit var presenter: DealsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deals)

        BottomNavManager.setup(this, NavTab.DEALS)

        rvDeals = findViewById(R.id.rvDeals)
        rvDeals.layoutManager = LinearLayoutManager(this)

        // Initialize Presenter with the universal MapModel
        presenter = DealsPresenter(this, MapModel())

        // Ask presenter to fetch only the discounted data
        presenter.loadActiveDeals()
    }

    override fun showDeals(deals: List<DealItem>) {
        rvDeals.adapter = DealsAdapter(deals)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}