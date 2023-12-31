package com.example.bangwool

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.databinding.ActivityMainBinding
import com.example.bangwool.ui.home.HomeFragment
import com.example.bangwool.ui.mypage.MyPageFragment
import com.example.bangwool.ui.ppomo.TodayPpomoFragment
import com.example.bangwool.ui.ranking.RankingFragment
import com.example.bangwool.ui.statistic.StatisticFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.setOnItemSelectedListener {
            onNavigationItemSelected(it)
        }
        binding.bottomNav.selectedItemId = R.id.navigation_home
        binding.fab.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.main_frm, TodayPpomoFragment()).commit()
            binding.bottomNav.selectedItemId = R.id.navigation_ppomo
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commit()
            }
            R.id.navigation_statistic -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frm, StatisticFragment()).commit()
            }
            R.id.navigation_ranking -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frm, RankingFragment()).commit()
            }
            R.id.navigation_mypage -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frm, MyPageFragment()).commit()
            }
        }
        return true
    }
}