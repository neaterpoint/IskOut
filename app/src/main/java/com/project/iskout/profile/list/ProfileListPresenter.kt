package com.project.iskout.profile.list

import com.project.iskout.deals.DealItem
import com.project.iskout.homepage.list.SpotListItem
import com.project.iskout.homepage.map.MapModel

class ProfileListPresenter(
    private var view: ProfileListContract.View?,
    private val model: MapModel
) : ProfileListContract.Presenter {

    override fun loadList(type: String) {
        val allSpots = model.getNearbySpots()

        when (type) {
            "SAVED" -> {
                view?.setPageTitle("Saved Spots")
                // Mock: Grab first 3 spots as "Saved"
                val savedSpots = allSpots.take(3).mapIndexed { index, spot ->
                    SpotListItem(spot.id, index + 1, spot.name, "${spot.type} · Saved", spot.rating, 120, spot.priceTag, "Chill", null)
                }
                view?.showSpotsList(savedSpots)
            }
            "DEALS" -> {
                view?.setPageTitle("Claimed Deals")
                // Mock: Grab first 2 discounted spots
                val claimedDeals = allSpots.filter { it.hasDiscount }.take(2).map { spot ->
                    DealItem(spot.id, spot.name, "Claimed: 20% off meals")
                }
                view?.showDealsList(claimedDeals)
            }
            "REVIEWS" -> {
                view?.setPageTitle("My Reviews")
                // Mock: Generate reviews based on existing spots
                val reviews = listOf(
                    ReviewItem("1", allSpots[0].name, 5, "Amazing food! Super cheap and fast service.", "2 days ago"),
                    ReviewItem("2", allSpots[11].name, 4, "Great coffee, but a bit crowded during lunch hours.", "1 week ago"),
                    ReviewItem("3", allSpots[20].name, 5, "Best place to chill after classes.", "2 weeks ago")
                )
                view?.showReviewsList(reviews)
            }
            "RECENT" -> {
                view?.setPageTitle("Recent Visits")
                // Mock: Grab 2 random spots
                val recentSpots = allSpots.shuffled().take(2).mapIndexed { index, spot ->
                    SpotListItem(spot.id, index + 1, spot.name, "${spot.type} · Visited recently", spot.rating, 80, spot.priceTag, "Busy", null)
                }
                view?.showSpotsList(recentSpots)
            }
        }
    }

    override fun onDestroy() {
        view = null
    }
}