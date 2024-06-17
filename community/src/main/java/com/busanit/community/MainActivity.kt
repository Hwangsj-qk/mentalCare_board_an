package com.busanit.community

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.busanit.community.databinding.ActivityMainBinding
import com.busanit.community.databinding.FragmentCommonBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val fragmentList = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 사용할 프래그먼트
        fragmentList.add(CommonFragment())
        fragmentList.add(MentalFragment())
        fragmentList.add(CheeringFragment())

        binding.pager2.adapter = TabAdapter(this, fragmentList)

        // 탭 이름 설정
        val tabName = arrayOf("일반 고민", "정신건강", "응원")

        binding.run {
            TabLayoutMediator(tabLayout, pager2) { tab, position ->
                tab.text = tabName[position]        // 탭 이름 설정
            }.attach()
        }


    }

    class TabAdapter(fragmentActivity: FragmentActivity, val fragmentList: MutableList<Fragment>) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }
}