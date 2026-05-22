package com.project.iskout.homepage.map

interface MapContract {
    interface View {
        fun initializeMap()
        fun showSpotsOnMap(spots: List<Spot>)
        fun updateCategoryUI(selectedType: String)
        fun updateSpotsCount(count: Int)
        fun updateBottomSheetDetails(spot: Spot)
        fun centerMapOnUserLocation()
        fun showError(message: String)
    }

    interface Presenter {
        fun onMapReady()
        fun onCategoryClicked(type: String)
        fun onAdvancedFiltersApplied(minRating: Double, discountsOnly: Boolean, hideBusySpots: Boolean, maxPrice: Int)
        fun onMarkerClicked(spotId: String)
        fun onMyLocationClicked()
        fun onDestroy()
    }
}