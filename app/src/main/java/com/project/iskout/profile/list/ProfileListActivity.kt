package com.project.iskout.profile.list

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.iskout.R
import com.project.iskout.deals.DealItem
import com.project.iskout.deals.DealsAdapter
import com.project.iskout.homepage.list.SpotListItem
import com.project.iskout.homepage.list.SpotsAdapter
import com.project.iskout.homepage.map.MapModel
import com.project.iskout.profile.ReviewsAdapter

class ProfileListActivity : AppCompatActivity(), ProfileListContract.View {

    private lateinit var rvList: RecyclerView
    private lateinit var tvPageTitle: TextView
    private lateinit var presenter: ProfileListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_list)

        tvPageTitle = findViewById(R.id.tvPageTitle)
        rvList = findViewById(R.id.rvProfileList)
        rvList.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish() // Goes back to the main Profile screen
        }

        presenter = ProfileListPresenter(this, MapModel())

        // Grab the type passed from the menu click
        val listType = intent.getStringExtra("LIST_TYPE") ?: "SAVED"
        presenter.loadList(listType)
    }

    override fun setPageTitle(title: String) {
        tvPageTitle.text = title
    }

    override fun showSpotsList(spots: List<SpotListItem>) {
        rvList.adapter = SpotsAdapter(spots)
    }

    override fun showDealsList(deals: List<DealItem>) {
        rvList.adapter = DealsAdapter(deals)
    }

    override fun showReviewsList(reviews: List<ReviewItem>) {
        rvList.adapter = ReviewsAdapter(reviews)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}