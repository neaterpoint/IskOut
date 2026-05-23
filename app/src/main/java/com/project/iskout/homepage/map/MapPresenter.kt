package com.project.iskout.homepage.map

class MapPresenter(
    private var view: MapContract.View?,
    private val model: MapModel
) : MapContract.Presenter {

    private var currentSpots: List<Spot> = emptyList()

    // Maintain state of all selected filters
    private var currentCategory = "All"
    private var currentMinRating = 0.0
    private var currentDiscountsOnly = false
    private var currentHideBusy = false
    private var currentMaxPrice = 200 // Added max price state

    override fun onMapReady() {
        applyAllFilters()
    }

    override fun onCategoryClicked(type: String) {
        currentCategory = type
        view?.updateCategoryUI(type)
        applyAllFilters()
    }

    override fun onAdvancedFiltersApplied(minRating: Double, discountsOnly: Boolean, hideBusy: Boolean, maxPrice: Int) {
        currentMinRating = minRating
        currentDiscountsOnly = discountsOnly
        currentHideBusy = hideBusy
        currentMaxPrice = maxPrice // Save new max price
        applyAllFilters()
    }

    // Master filter function
    private fun applyAllFilters() {
        val allSpots = model.getNearbySpots()

        currentSpots = allSpots.filter { spot ->
            val matchCategory = if (currentCategory == "All") true else spot.type == currentCategory
            val matchRating = spot.rating >= currentMinRating
            val matchDiscount = if (currentDiscountsOnly) spot.hasDiscount else true
            val matchBusy = if (currentHideBusy) !spot.isBusy else true

            // Extract the raw number from the "₱45" string to do the math
            val rawPrice = spot.priceTag.replace("₱", "").trim().toIntOrNull() ?: 0
            val matchPrice = rawPrice <= currentMaxPrice

            matchCategory && matchRating && matchDiscount && matchBusy && matchPrice
        }

        view?.showSpotsOnMap(currentSpots)
        view?.updateSpotsCount(currentSpots.size)
    }

    override fun onMarkerClicked(spotId: String) {
        val allSpots = model.getNearbySpots()
        val clickedSpot = allSpots.find { it.id == spotId }

        if (clickedSpot != null) {
            // 1. Show the details in the bottom sheet
            view?.updateBottomSheetDetails(clickedSpot)

            // 2. NEW: Tell the map to smoothly pan to these coordinates!
            view?.centerMapOnCoordinates(clickedSpot.latitude, clickedSpot.longitude)
        }
    }

    override fun onNearestSpotsRequested() {
        val allSpots = model.getNearbySpots()

        // Sort by distance (extracts the number from "₱45 avg · 120m")
        val sortedSpots = allSpots.sortedBy { spot ->
            spot.distanceInfo.split("·").last().replace("m", "").trim().toIntOrNull() ?: Int.MAX_VALUE
        }

        // Take only the top 5 nearest and format them for the reusable SpotsAdapter
        val listItems = sortedSpots.take(5).mapIndexed { index, spot ->
            val stableReviewCount = 50 + ((spot.id.toIntOrNull() ?: 1) * 17 % 250)

            // Reusing the SpotListItem from your list package
            com.project.iskout.homepage.list.SpotListItem(
                id = spot.id,
                rank = index + 1,
                name = spot.name,
                categoryDistance = "${spot.type} · ${spot.distanceInfo.split("·").last().trim()}",
                rating = spot.rating,
                reviewCount = stableReviewCount,
                priceText = spot.priceTag,
                statusText = if (spot.isBusy) "Busy" else "Chill",
                dealText = if (spot.hasDiscount) "Special Deal Available" else null
            )
        }

        view?.showNearestSpotsBottomSheet(listItems)
    }

    override fun onDestroy() {
        view = null
    }



}