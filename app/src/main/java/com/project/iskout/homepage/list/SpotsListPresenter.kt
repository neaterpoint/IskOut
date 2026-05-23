package com.project.iskout.homepage.list

import com.project.iskout.homepage.map.MapModel

class SpotsListPresenter(
    private var view: SpotsListContract.View?,
    private val model: MapModel
) : SpotsListContract.Presenter {

    // Default starting filter
    private var currentFilter = "Nearest"

    override fun loadSpots() {
        val allSpots = model.getNearbySpots()

        // 1. Perform the Sorting Logic based on the active filter
        val sortedSpots = when (currentFilter) {
            "Top rated" -> allSpots.sortedByDescending { it.rating }

            "Cheapest" -> allSpots.sortedBy { spot ->
                // Extracts integer from "₱45"
                spot.priceTag.replace("₱", "").trim().toIntOrNull() ?: Int.MAX_VALUE
            }

            "Nearest" -> allSpots.sortedBy { spot ->
                // Extracts integer from "₱45 avg · 120m"
                spot.distanceInfo.split("·").last().replace("m", "").trim().toIntOrNull() ?: Int.MAX_VALUE
            }

            else -> allSpots
        }

        // 2. Convert to the List UI Model
        val listItems = sortedSpots.mapIndexed { index, spot ->

            // Generate a stable pseudo-random review count so it doesn't change every time we sort
            val stableReviewCount = 50 + ((spot.id.toIntOrNull() ?: 1) * 17 % 250)

            SpotListItem(
                id = spot.id,
                rank = index + 1, // Updates the rank badge based on the new sort order!
                name = spot.name,
                categoryDistance = "${spot.type} · ${spot.distanceInfo.split("·").last().trim()}",
                rating = spot.rating,
                reviewCount = stableReviewCount,
                priceText = spot.priceTag,
                statusText = if (spot.isBusy) "Busy" else "Chill",
                dealText = if (spot.hasDiscount) "Special Deal Available" else null
            )
        }

        // 3. Send results to the View
        view?.showSpots(listItems)
        view?.updateFilterUI(currentFilter)
    }

    override fun onFilterClicked(filterType: String) {
        currentFilter = filterType
        loadSpots() // Re-runs the load pipeline with the new filter
    }

    override fun onSpotClicked(spotId: String) {
        // Handle click later (e.g., open a details screen)
    }

    override fun onDestroy() {
        view = null
    }
}