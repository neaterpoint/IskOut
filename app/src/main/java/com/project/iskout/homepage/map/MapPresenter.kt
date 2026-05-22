package com.project.iskout.homepage.map

class MapPresenter(
    private var view: MapContract.View?,
    private val model: MapModel
) : MapContract.Presenter {

    private var currentSpots: List<Spot> = emptyList()

    override fun onMapReady() {
        // When the map finishes loading, fetch all spots by default
        currentSpots = model.getNearbySpots()
        view?.showSpotsOnMap(currentSpots)
    }

    override fun onFilterClicked(type: String) {
        view?.updateFilterUI(type)
        currentSpots = model.filterSpots(type)
        view?.showSpotsOnMap(currentSpots)
    }

    override fun onMarkerClicked(spotId: String) {
        // Find the spot that was clicked and update the bottom sheet
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