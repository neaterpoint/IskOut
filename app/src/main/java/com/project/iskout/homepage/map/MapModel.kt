package com.project.iskout.homepage.map

data class Spot(
    val id: String,
    val name: String,
    val type: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    val distanceInfo: String,
    val priceTag: String
)

class MapModel {
    fun getNearbySpots(): List<Spot> {
        return listOf(
            // Karinderyas (10)
            Spot("1", "Aling Nena's", "Karinderya", 14.6549, 121.0646, 4.8, "₱45 avg · 120m", "₱45"),
            Spot("2", "Mang Larry's", "Karinderya", 14.6580, 121.0640, 4.9, "₱35 avg · 500m", "₱35"),
            Spot("3", "Area 2 Food", "Karinderya", 14.6565, 121.0695, 4.7, "₱50 avg · 400m", "₱50"),
            Spot("4", "Rodic's Diner", "Karinderya", 14.6540, 121.0655, 4.8, "₱100 avg · 250m", "₱100"),
            Spot("5", "Beach House", "Karinderya", 14.6535, 121.0670, 4.6, "₱90 avg · 300m", "₱90"),
            Spot("6", "Lutong Bahay", "Karinderya", 14.6510, 121.0630, 4.3, "₱55 avg · 800m", "₱55"),
            Spot("7", "Isko's Eatery", "Karinderya", 14.6595, 121.0685, 4.2, "₱40 avg · 600m", "₱40"),
            Spot("8", "Kantong Karinderya", "Karinderya", 14.6550, 121.0610, 4.1, "₱30 avg · 900m", "₱30"),
            Spot("9", "UP Food Hub", "Karinderya", 14.6520, 121.0650, 4.4, "₱70 avg · 450m", "₱70"),
            Spot("10", "Dorm Foodie", "Karinderya", 14.6570, 121.0670, 4.0, "₱60 avg · 550m", "₱60"),
            // Cafes (10)
            Spot("11", "Kape Isko", "Cafe", 14.6530, 121.0680, 4.5, "₱65 avg · 300m", "₱65"),
            Spot("12", "Chocokiss", "Cafe", 14.6575, 121.0625, 4.4, "₱150 avg · 550m", "₱150"),
            Spot("13", "Yellow Tag", "Cafe", 14.6555, 121.0630, 4.3, "₱120 avg · 200m", "₱120"),
            Spot("14", "Brew Hub", "Cafe", 14.6525, 121.0690, 4.6, "₱130 avg · 350m", "₱130"),
            Spot("15", "Study Bean", "Cafe", 14.6585, 121.0615, 4.7, "₱110 avg · 700m", "₱110"),
            Spot("16", "Java Joint", "Cafe", 14.6515, 121.0665, 4.2, "₱95 avg · 400m", "₱95"),
            Spot("17", "Caffeine Fix", "Cafe", 14.6560, 121.0685, 4.1, "₱105 avg · 500m", "₱105"),
            Spot("18", "Campus Cup", "Cafe", 14.6545, 121.0620, 4.0, "₱85 avg · 300m", "₱85"),
            Spot("19", "Thesis Cafe", "Cafe", 14.6535, 121.0650, 4.8, "₱140 avg · 250m", "₱140"),
            Spot("20", "Midnight Brew", "Cafe", 14.6550, 121.0675, 4.3, "₱115 avg · 450m", "₱115"),
            // Inumin (10)
            Spot("21", "Sarah's", "Inumin", 14.6525, 121.0620, 4.6, "₱80 avg · 600m", "₱80"),
            Spot("22", "Sari-Sari CHK", "Inumin", 14.6590, 121.0660, 4.0, "₱20 avg · 650m", "₱20"),
            Spot("23", "Juice Corner", "Inumin", 14.6540, 121.0635, 4.2, "₱40 avg · 200m", "₱40"),
            Spot("24", "Tea Time", "Inumin", 14.6560, 121.0665, 4.5, "₱60 avg · 300m", "₱60"),
            Spot("25", "Cool Sips", "Inumin", 14.6510, 121.0680, 4.1, "₱35 avg · 500m", "₱35"),
            Spot("26", "Healthy Blends", "Inumin", 14.6550, 121.0690, 4.7, "₱75 avg · 400m", "₱75"),
            Spot("27", "Shake Spot", "Inumin", 14.6585, 121.0645, 4.3, "₱55 avg · 550m", "₱55"),
            Spot("28", "Buko Hub", "Inumin", 14.6530, 121.0610, 4.4, "₱30 avg · 450m", "₱30"),
            Spot("29", "Milk Tea Lab", "Inumin", 14.6565, 121.0655, 4.6, "₱90 avg · 350m", "₱90"),
            Spot("30", "Lemonade Stand", "Inumin", 14.6520, 121.0630, 3.9, "₱25 avg · 500m", "₱25")
        )
    }

    fun filterSpots(type: String): List<Spot> {
        val allSpots = getNearbySpots()
        if (type == "All") return allSpots
        return allSpots.filter { it.type == type }
    }
}