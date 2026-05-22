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

    override fun onAdvancedFiltersApplied(minRating: Double, discountsOnly: Boolean, hideBusySpots: Boolean, maxPrice: Int) {
        currentMinRating = minRating
        currentDiscountsOnly = discountsOnly
        currentHideBusy = hideBusySpots
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
        val selectedSpot = currentSpots.find { it.id == spotId }
        if (selectedSpot != null) {
            view?.updateBottomSheetDetails(selectedSpot)
        }
    }

    override fun onMyLocationClicked() {
        view?.centerMapOnUserLocation()
    }

    override fun onDestroy() {
        view = null
    }
}