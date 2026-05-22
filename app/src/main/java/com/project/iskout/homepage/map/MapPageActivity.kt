package com.project.iskout.homepage.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.iskout.R

class MapPageActivity : AppCompatActivity(), MapContract.View {

    private lateinit var presenter: MapContract.Presenter
    private lateinit var webView: WebView

    private lateinit var tvSpotName: TextView
    private lateinit var tvSpotDetails: TextView

    private lateinit var chipAll: TextView
    private lateinit var chipKarinderya: TextView
    private lateinit var chipCafe: TextView
    private lateinit var chipInumin: TextView

    private var selectedType = "All"
    private lateinit var tvSpotsNearbyTitle: TextView

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_maphomepage)

        webView = findViewById(R.id.mapWebView)

        // 1. Find the Views
        tvSpotName = findViewById(R.id.tvSpotName)
        tvSpotDetails = findViewById(R.id.tvSpotDetails)

        chipAll = findViewById(R.id.chipAll)
        chipKarinderya = findViewById(R.id.chipKarinderya)
        chipCafe = findViewById(R.id.chipCafe)
        chipInumin = findViewById(R.id.chipInumin)

        // FIX: Added the missing connection here!
        tvSpotsNearbyTitle = findViewById(R.id.tvSpotsNearbyTitle)

        // 2. Set Listeners
        chipAll.setOnClickListener { presenter.onFilterClicked("All") }
        chipKarinderya.setOnClickListener { presenter.onFilterClicked("Karinderya") }
        chipCafe.setOnClickListener { presenter.onFilterClicked("Cafe") }
        chipInumin.setOnClickListener { presenter.onFilterClicked("Inumin") }

        // 3. Initialize MVP and Map
        presenter = MapPresenter(this, MapModel())
        initializeMap()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initializeMap() {
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.addJavascriptInterface(WebAppInterface(), "AndroidInterface")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                presenter.onMapReady()
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

    override fun updateFilterUI(selectedType: String) {
        this.selectedType = selectedType

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

        val count = if (selectedType == "All") 30 else 10
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