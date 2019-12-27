package com.example.androidarchitecture.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidarchitecture.R
import com.example.androidarchitecture.ui.blog.BlogFragment
import com.example.androidarchitecture.ui.image.ImageFragment
import com.example.androidarchitecture.ui.kin.KinFragment
import com.example.androidarchitecture.ui.movie.MovieFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 반드시 탭보다 먼저 선언.
        pager.adapter = ViewPagerAdapter(this)

        tab_layout.tabGravity = TabLayout.GRAVITY_FILL
        TabLayoutMediator(
            tab_layout,
            pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = when (position) {
                    0 -> "Movie"
                    1 -> "Image"
                    2 -> "Blog"
                    3 -> "Kin"
                    else -> ""
                }
            }).attach()
    }


    class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val fragmentList = listOf(MovieFragment(), ImageFragment(), BlogFragment(), KinFragment())
        override fun getItemCount() = fragmentList.size
        override fun createFragment(position: Int) = fragmentList[position] as Fragment
    }

}