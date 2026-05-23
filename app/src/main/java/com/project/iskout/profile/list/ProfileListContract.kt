package com.project.iskout.profile.list

import com.project.iskout.deals.DealItem
import com.project.iskout.homepage.list.SpotListItem

interface ProfileListContract {
    interface View {
        fun setPageTitle(title: String)
        fun showSpotsList(spots: List<SpotListItem>)
        fun showDealsList(deals: List<DealItem>)
        fun showReviewsList(reviews: List<ReviewItem>)
    }

    interface Presenter {
        fun loadList(type: String)
        fun onDestroy()
    }
}

// Data Model specific to Reviews
data class ReviewItem(
    val id: String,
    val spotName: String,
    val rating: Int,
    val comment: String,
    val date: String
)