package com.project.iskout.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.project.iskout.core.database.entities.establishments.Discount
import com.project.iskout.core.database.entities.establishments.Establishment
import com.project.iskout.core.database.entities.establishments.EstablishmentCategory
import com.project.iskout.core.database.entities.establishments.MenuItem
import com.project.iskout.core.database.entities.moderation.Image
import com.project.iskout.core.database.entities.moderation.Notification
import com.project.iskout.core.database.entities.moderation.Report
import com.project.iskout.core.database.entities.moderation.ReportImage
import com.project.iskout.core.database.entities.moderation.Review
import com.project.iskout.core.database.entities.popups.PopupCategory
import com.project.iskout.core.database.entities.popups.PopupImage
import com.project.iskout.core.database.entities.popups.PopupReview
import com.project.iskout.core.database.entities.popups.StudentPopup
import com.project.iskout.core.database.entities.users.MerchantProfile
import com.project.iskout.core.database.entities.users.Session
import com.project.iskout.core.database.entities.users.StudentProfile
import com.project.iskout.core.database.entities.users.User
import com.project.iskout.core.database.entities.users.VerificationDocument

@Database(
    entities = [
        User::class, VerificationDocument::class, Session::class,
        StudentProfile::class, MerchantProfile::class,
        EstablishmentCategory::class, Establishment::class, MenuItem::class, Discount::class,
        PopupCategory::class, StudentPopup::class, PopupImage::class, PopupReview::class,
        Review::class, Image::class,
        Report::class, ReportImage::class, Notification::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DatabaseConnection : RoomDatabase() {

    abstract fun userDao(): UserDao
    // You can add more DAOs here later (e.g., abstract fun establishmentDao(): EstablishmentDao)

    companion object {
        @Volatile
        private var INSTANCE: DatabaseConnection? = null

        fun getDatabase(context: Context): DatabaseConnection {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseConnection::class.java,
                    "iskout_database"
                )
                    .allowMainThreadQueries()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            // Grab the current time to pass into the raw SQL
                            val time = System.currentTimeMillis()

                            // 1. Seed Establishment Categories
                            db.execSQL("INSERT INTO establishment_categories (name) VALUES ('Karenderia'), ('Cafe'), ('Snack Shop'), ('Bakery'), ('Food Stall')")

                            // 2. Seed Popup Categories
                            db.execSQL("INSERT INTO popup_categories (name) VALUES ('Food'), ('Clothing'), ('School Supplies'), ('Electronics'), ('Collectibles'), ('Others')")

                            // 3. Seed Users (Added 'created_at' and the '$time' variable)
                            db.execSQL("INSERT INTO users (user_id, email, username, password_hash, full_name, role, account_status, created_at) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '123', 'admin_test', '123', 'Test Admin', 'admin', 'approved', $time)")
                            db.execSQL("INSERT INTO users (user_id, email, username, password_hash, full_name, role, account_status, created_at) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '1', 'ecn', '1', 'Vince Mathew L. Silva', 'student', 'approved', $time)")
                            db.execSQL("INSERT INTO users (user_id, email, username, password_hash, full_name, role, account_status, created_at) VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', '2', 'merchant_test', '2', 'Test Merchant', 'merchant', 'approved', $time)")

                            // 4. Seed Profiles (Added 'community_rating' and '0.0')
                            db.execSQL("INSERT INTO student_profiles (profile_id, user_id, school_name, student_number, community_rating) VALUES ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Cebu Institute of Technology - University', '2024-00001', 0.0)")
                            db.execSQL("INSERT INTO merchant_profiles (profile_id, user_id, business_name, permit_number, phone_number, community_rating) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'Test Karenderia', 'BP-2026-0001', '09171234567', 0.0)")
                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}