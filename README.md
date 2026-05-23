# IskOut

> *Iskolar* + *Scout* — Find the best budget eats near your campus.

IskOut is an Android app built for Filipino students who want to discover nearby karenderias, cafes, and food spots that are actually worth it — sorted by distance, rating, and price, not by who posted loudest in a group chat. Every listing is from a verified source, every deal is real, and the map is always centered on where you are.

---

## The Problem

When you're a student on a budget, finding a good place to eat near campus means asking in group chats, getting ten different answers, and ending up at a place that's either overpriced, mediocre, or closed. There's no reliable, student-focused way to find cheap, well-rated food nearby.

## The Solution

IskOut puts a live map of verified nearby spots in your pocket. Filter by distance, sort by rating or price, browse active flash deals, and go — no noise, no irrelevant posts, just the spots that matter to students.

---

## Features

### Live Map Dashboard
An interactive map centered on your current location showing nearby verified karenderias, cafes, and food stalls as pins. Category chips let you filter between Karinderya, Café, and Inumin instantly. Advanced filters let you narrow by minimum rating, maximum price, and active deals.

### Spots List with Smart Sorting
A list view of nearby spots, sorted and re-ranked dynamically based on your preference:
- **Nearest** — default, closest spots first
- **Top Rated** — highest community rating first
- **Cheapest** — lowest average price first
- **Live Deals** — only spots with active discounts

Each spot card shows its rating, average price, distance, busyness level, and any active deal tag at a glance.

### Flash Deals
A dedicated Deals tab showing all active student discounts across verified establishments. A featured trending card highlights the hottest deal right now with a live countdown timer. All other active deals are listed below with a Save option.

### Profile
Your personal iskolar hub — shows your initials avatar, school info, review count, saved spots count, and iskolar points. Quick access to your saved spots, claimed deals, reviews, and recent visits from one screen.

### Verification-First Access
New users register as a Student or Business Owner and submit their School ID or Business Permit for admin review. Access to the map and all features is only unlocked after approval.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Platform | Android (Native Kotlin) |
| Min SDK | API 24 (Android 7.0) |
| Target SDK | API 36 |
| UI | XML Layouts, Material3, ConstraintLayout |
| Architecture | MVP (Model-View-Presenter) |
| Database | Room (SQLite) |
| Map | Leaflet.js via WebView |

---

## Project Structure

```
app/src/main/java/com/project/iskout/
├── core/
│   ├── SessionManager.kt
│   └── database/
│       ├── DatabaseConnection.kt
│       ├── UserDao.kt
│       └── entities/
│           ├── users/
│           ├── establishments/
│           ├── popups/
│           └── moderation/
├── login/
├── register/
├── verification/
├── confirmation/
├── homepage/
│   ├── map/
│   └── list/
├── deals/
├── profile/
│   └── list/
└── utils/
```

---

## Getting Started

### Prerequisites
- Android Studio (latest stable)
- Android SDK API 24+
- JDK 11+

### Setup
1. Clone the repository
   ```bash
   git clone https://github.com/neaterpoint/IskOut.git
   ```
2. Open the project in **Android Studio**
3. Let Gradle sync finish
4. Run on an emulator or physical device (API 24+)

### Test Accounts

| Role | Email | Password |
|---|---|---|
| Admin | `123` | `123` |
| Student | `1` | `1` |
| Merchant | `2` | `2` |

---

## Roadmap

- [x] Project setup and base architecture
- [x] Room database with full schema and seed data
- [x] Login screen with session management
- [x] Register flow with role selection
- [x] Verification document submission screen
- [x] Interactive Leaflet map with category filters and advanced filters
- [x] Spots list with smart sorting (Nearest, Top Rated, Cheapest, Live Deals)
- [x] Flash Deals tab with featured trending card
- [x] Profile screen with stats and sub-list navigation
- [x] Bottom navigation across all main screens
- [ ] Live database integration for map pins and deals
- [ ] Establishment detail screen with menu and reviews
- [ ] Student pop-up events
- [ ] Admin moderation dashboard
- [ ] Push notifications for verification and reports
- [ ] Community ratings and review submission

---

## Target Users

| User | Need |
|---|---|
| **Students** | Find nearby budget-friendly food sorted by price, rating, and distance |
| **Business Owners** | Reach verified student customers with menus and live discounts |
| **Student Admins** | Keep the platform trustworthy through document verification and moderation |

---

## Entity Relationship Diagram

![IskOut_ERD](IskOut_ERD.png)

---

## License

Copyright (c) 2026 Vince Mathew L. Silva — All Rights Reserved.

This project and its source code may be viewed for portfolio and evaluation purposes only. No part of this project may be copied, modified, distributed, or used as the basis for another product without explicit written permission from the author.