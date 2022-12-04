package com.optic.kotlinudemydelivery.activities.delivery.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.optic.kotlinudemydelivery.R
import com.optic.kotlinudemydelivery.activities.MainActivity
import com.optic.kotlinudemydelivery.fragments.client.ClientCategoriesFragment
import com.optic.kotlinudemydelivery.fragments.client.ClientProfileFragment
import com.optic.kotlinudemydelivery.fragments.delivery.DeliveryOrdersFragment
import com.optic.kotlinudemydelivery.fragments.restaurant.RestaurantCategoryFragment
import com.optic.kotlinudemydelivery.fragments.restaurant.RestaurantOrdersFragment
import com.optic.kotlinudemydelivery.fragments.restaurant.RestaurantProductFragment
import com.optic.kotlinudemydelivery.models.User
import com.optic.kotlinudemydelivery.utils.SharedPref


class DeliveryHomeActivity : AppCompatActivity() {

    private val TAG = "DeliveryHomeActivity"
    //var buttonLogout: Button? = null
    var sharedPref: SharedPref? = null

    var bottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_home)
        sharedPref = SharedPref(this)
        //buttonLogout = findViewById(R.id.btn_logout)
        //buttonLogout?.setOnClickListener{ logout() }

        openFragment(DeliveryOrdersFragment())

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.setOnItemSelectedListener {
            when(it.itemId) {

                R.id.item_orders -> {
                    openFragment(DeliveryOrdersFragment())
                    true
                }

                R.id.item_profile -> {
                    openFragment(ClientProfileFragment())
                    true
                }

                else -> false

            }
        }

        getUserFromSession()
    }

    private fun openFragment(fragment: Fragment) {
        val  transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun logout(){
        sharedPref?.remove("user")
        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
    }

    private fun getUserFromSession() {

        val gson = Gson()

        if (!sharedPref?.getData("user").isNullOrBlank()){
            // SI EL USUARIO EXISTE EN SESION
            val user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
            Log.d(TAG, "Usuario: $user")
        }
    }
}