package com.project.iskout.homepage.map

interface MapContract {
    interface View {
        fun initializeMap()
        fun showSpotsOnMap(spots: List<Spot>)
        fun updateFilterUI(selectedType: String)
        fun updateBottomSheetDetails(spot: Spot)
        fun centerMapOnUserLocation()
        fun showError(message: String)
    }

    interface Presenter {
        fun onMapReady()
        fun onFilterClicked(type: String)
        fun onMarkerClicked(spotId: String)
        fun onMyLocationClicked()
        fun onDestroy()
    }
}