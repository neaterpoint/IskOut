package com.project.iskout.deals

import com.project.iskout.homepage.map.MapModel

class DealsPresenter(
    private var view: DealsContract.View?,
    private val model: MapModel
) : DealsContract.Presenter {

    override fun loadActiveDeals() {
        val allSpots = model.getNearbySpots()

        // FILTER: Only keep spots where hasDiscount == true
        val discountedSpots = allSpots.filter { it.hasDiscount }

        // Convert them to DealItems for the RecyclerView
        val dealItems = discountedSpots.map { spot ->
            DealItem(
                id = spot.id,
                spotName = spot.name,
                dealDescription = "₱20 off with Iskolar ID" // Mocking a standard deal description
            )
        }

        view?.showDeals(dealItems)
    }

    fun onDestroy() {
        view = null
    }
}