package com.esprit.tunify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.esprit.tunify.databinding.ActivityMainBinding
import io.paperdb.Paper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Paper.init(applicationContext)


        setupProductsFragment()

        binding.apply {
            toggle = ActionBarDrawerToggle(this@MainActivity, drawer, R.string.open, R.string.close)
            drawer.addDrawerListener(toggle)
            toggle.syncState()


            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navigation.setNavigationItemSelectedListener {
                drawer.closeDrawers()
                when (it.itemId) {
                    R.id.home -> {
                        setupProductsFragment()
                    }
                    R.id.profile -> {
                        val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.cart -> {
                        setupCartFragment()
                    }
                    R.id.orders -> {
                        setupOrderFragment()
                    }
                    R.id.settings -> {
                        Toast.makeText(this@MainActivity, "Settings clicked", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                true
            }

        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun setupProductsFragment() {
        val fragment = ProductListFragment.newInstance()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    private fun setupCartFragment() {
        val fragment = CartFragment.newInstance()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    private fun setupOrderFragment() {
        val fragment = OrderFragment.newInstance()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}
