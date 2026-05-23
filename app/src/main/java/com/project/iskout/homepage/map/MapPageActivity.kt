package com.project.iskout.homepage.map

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.project.iskout.R
import com.project.iskout.homepage.list.SpotListItem
import com.project.iskout.homepage.list.SpotsAdapter
import com.project.iskout.homepage.list.SpotsListActivity
import com.project.iskout.utils.BottomNavManager
import com.project.iskout.utils.NavTab

class MapPageActivity : AppCompatActivity(), MapContract.View {

    private lateinit var presenter: MapContract.Presenter
    private lateinit var webView: WebView

    private lateinit var tvSpotName: TextView
    private lateinit var tvSpotDetails: TextView

    private lateinit var chipAll: TextView
    private lateinit var chipKarinderya: TextView
    private lateinit var chipCafe: TextView
    private lateinit var chipInumin: TextView
    private lateinit var tvSpotsNearbyTitle: TextView

    private var activeMinRating = 0.0
    private var activeDiscountsOnly = false
    private var activeHideBusy = false
    private var activeMaxPrice = 200

    private lateinit var loadingOverlay: View

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_maphomepage)

        BottomNavManager.setup(this, NavTab.MAP)

        loadingOverlay = findViewById(R.id.loadingOverlay)
        webView = findViewById(R.id.mapWebView)
        tvSpotName = findViewById(R.id.tvSpotName)
        tvSpotDetails = findViewById(R.id.tvSpotDetails)
        chipAll = findViewById(R.id.chipAll)
        chipKarinderya = findViewById(R.id.chipKarinderya)
        chipCafe = findViewById(R.id.chipCafe)
        chipInumin = findViewById(R.id.chipInumin)
        tvSpotsNearbyTitle = findViewById(R.id.tvSpotsNearbyTitle)

        chipAll.setOnClickListener { presenter.onCategoryClicked("All") }
        chipKarinderya.setOnClickListener { presenter.onCategoryClicked("Karinderya") }
        chipCafe.setOnClickListener { presenter.onCategoryClicked("Cafe") }
        chipInumin.setOnClickListener { presenter.onCategoryClicked("Inumin") }

        presenter = MapPresenter(this, MapModel())

        findViewById<ImageView>(R.id.llNearestButton).setOnClickListener {
            presenter.onNearestSpotsRequested()
        }

        setupAdvancedFiltersModal()
        initializeMap()

        if (intent.hasExtra("LATITUDE") && intent.hasExtra("LONGITUDE")) {
            val lat = intent.getDoubleExtra("LATITUDE", 0.0)
            val lng = intent.getDoubleExtra("LONGITUDE", 0.0)

            webView.postDelayed({
                centerMapOnCoordinates(lat, lng)
            }, 1000)
        }
    }

    override fun showNearestSpotsBottomSheet(spots: List<SpotListItem>) {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.layout_bottom_sheet_nearest)

        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.behavior.skipCollapsed = true

        val rvNearest = dialog.findViewById<RecyclerView>(R.id.rvNearestSpots)
        rvNearest?.layoutManager = LinearLayoutManager(this)
        rvNearest?.adapter = SpotsAdapter(spots) { clickedSpotItem ->
            dialog.dismiss()

            val originalSpot = MapModel().getNearbySpots().find { it.id == clickedSpotItem.id }
            if (originalSpot != null) {
                centerMapOnCoordinates(originalSpot.latitude, originalSpot.longitude)
                updateBottomSheetDetails(originalSpot)
            }
        }

        dialog.findViewById<MaterialButton>(R.id.btnOpenFullList)?.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, SpotsListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }

        dialog.show()
    }

    override fun centerMapOnCoordinates(lat: Double, lng: Double) {
        webView.evaluateJavascript("map.setView([$lat, $lng], 18);", null)
    }

    private fun setupAdvancedFiltersModal() {
        val btnFilterSearch = findViewById<ImageView>(R.id.btnFilterSearch)

        btnFilterSearch.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet_filters)

            // FIXED: Using `.behavior` bypasses the red 'R' error completely!
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetDialog.behavior.skipCollapsed = true

            val chipAny = bottomSheetDialog.findViewById<TextView>(R.id.chipRatingAny)
            val chip3 = bottomSheetDialog.findViewById<TextView>(R.id.chipRating3)
            val chip4 = bottomSheetDialog.findViewById<TextView>(R.id.chipRating4)
            val chip45 = bottomSheetDialog.findViewById<TextView>(R.id.chipRating45)
            val chip48 = bottomSheetDialog.findViewById<TextView>(R.id.chipRating48)

            val switchDiscounts = bottomSheetDialog.findViewById<SwitchMaterial>(R.id.switchDiscounts)
            val switchBusy = bottomSheetDialog.findViewById<SwitchMaterial>(R.id.switchBusy)
            val etMaxPrice = bottomSheetDialog.findViewById<EditText>(R.id.etMaxPrice)

            var tempMinRating = activeMinRating
            switchDiscounts?.isChecked = activeDiscountsOnly
            switchBusy?.isChecked = activeHideBusy
            etMaxPrice?.setText(activeMaxPrice.toString())

            val ratingChips = mapOf(
                0.0 to chipAny,
                3.0 to chip3,
                4.0 to chip4,
                4.5 to chip45,
                4.8 to chip48
            )

            fun updateModalRatingUI(selected: Double) {
                tempMinRating = selected
                ratingChips.forEach { (rating, chip) ->
                    if (rating == selected) {
                        chip?.setBackgroundResource(R.drawable.bg_chip_active)
                        chip?.setTextColor(android.graphics.Color.WHITE)
                    } else {
                        chip?.setBackgroundResource(R.drawable.bg_chip_inactive)
                        chip?.setTextColor(android.graphics.Color.BLACK)
                    }
                }
            }

            updateModalRatingUI(tempMinRating)

            chipAny?.setOnClickListener { updateModalRatingUI(0.0) }
            chip3?.setOnClickListener { updateModalRatingUI(3.0) }
            chip4?.setOnClickListener { updateModalRatingUI(4.0) }
            chip45?.setOnClickListener { updateModalRatingUI(4.5) }
            chip48?.setOnClickListener { updateModalRatingUI(4.8) }

            bottomSheetDialog.findViewById<MaterialButton>(R.id.btnApplyFilters)?.setOnClickListener {
                activeMinRating = tempMinRating
                activeDiscountsOnly = switchDiscounts?.isChecked ?: false
                activeHideBusy = switchBusy?.isChecked ?: false

                val maxPriceText = etMaxPrice?.text.toString()
                activeMaxPrice = maxPriceText.toIntOrNull() ?: 200

                presenter.onAdvancedFiltersApplied(activeMinRating, activeDiscountsOnly, activeHideBusy, activeMaxPrice)
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.findViewById<MaterialButton>(R.id.btnReset)?.setOnClickListener {
                activeMinRating = 0.0
                activeDiscountsOnly = false
                activeHideBusy = false
                activeMaxPrice = 200

                presenter.onAdvancedFiltersApplied(activeMinRating, activeDiscountsOnly, activeHideBusy, activeMaxPrice)
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.findViewById<ImageView>(R.id.btnClose)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initializeMap() {
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.addJavascriptInterface(WebAppInterface(), "AndroidInterface")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                presenter.onMapReady()

                loadingOverlay.postDelayed({
                    loadingOverlay.animate()
                        .alpha(0f)
                        .setDuration(400)
                        .withEndAction {
                            loadingOverlay.visibility = View.GONE
                        }
                }, 500)
            }
        }
        webView.loadUrl("file:///android_asset/leaflet_map.html")
    }

    override fun showSpotsOnMap(spots: List<Spot>) {
        webView.evaluateJavascript("clearAllMarkers();", null)
        for (spot in spots) {
            val jsCommand = "addSpotMarker('${spot.id}', ${spot.latitude}, ${spot.longitude}, '${spot.name}', '${spot.type}', '${spot.priceTag}');"
            webView.evaluateJavascript(jsCommand, null)
        }
    }

    override fun centerMapOnUserLocation() {
        webView.evaluateJavascript("centerOnUser();", null)
    }

    override fun updateCategoryUI(selectedType: String) {
        val allChips = mapOf(
            "All" to chipAll,
            "Karinderya" to chipKarinderya,
            "Cafe" to chipCafe,
            "Inumin" to chipInumin
        )

        allChips.forEach { (type, chip) ->
            if (type == selectedType) {
                chip.setBackgroundResource(R.drawable.bg_chip_active)
                chip.setTextColor(android.graphics.Color.WHITE)
            } else {
                chip.setBackgroundResource(R.drawable.bg_chip_inactive)
                chip.setTextColor(android.graphics.Color.BLACK)
            }
        }
    }

    override fun updateSpotsCount(count: Int) {
        tvSpotsNearbyTitle.text = "$count SPOTS NEARBY"
    }

    override fun updateBottomSheetDetails(spot: Spot) {
        tvSpotName.text = spot.name
        tvSpotDetails.text = "⭐ ${spot.rating} · ${spot.distanceInfo}"
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun onMarkerClickedFromWeb(spotId: String) {
            runOnUiThread {
                presenter.onMarkerClicked(spotId)
            }
        }
    }
}