package com.project.iskout.homepage.map

import com.project.iskout.homepage.list.SpotListItem

interface MapContract {
    interface View {
        fun initializeMap()
        fun showSpotsOnMap(spots: List<Spot>)
        fun centerMapOnUserLocation()
        fun updateCategoryUI(selectedType: String)
        fun updateSpotsCount(count: Int)
        fun updateBottomSheetDetails(spot: Spot)
        fun centerMapOnCoordinates(lat: Double, lng: Double)
        fun showNearestSpotsBottomSheet(spots: List<SpotListItem>)
        fun showError(message: String)
    }

    interface Presenter {
        fun onMapReady()
        fun onCategoryClicked(type: String)
        fun onAdvancedFiltersApplied(minRating: Double, discountsOnly: Boolean, hideBusy: Boolean, maxPrice: Int)
        fun onMarkerClicked(spotId: String)
        fun onNearestSpotsRequested()
        fun onDestroy()
    }
}