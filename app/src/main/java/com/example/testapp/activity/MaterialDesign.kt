package com.example.testapp.activity

import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testapp.base.BaseActivity
import com.example.testapp.databinding.ActivityMaterialDesignBinding
import com.example.testapp.fragment.FragmentTabOne
import com.example.testapp.fragment.FragmentTabThree
import com.example.testapp.fragment.FragmentTabTwo
import com.google.android.material.tabs.TabLayoutMediator

class MaterialDesign : BaseActivity() {

    private lateinit var binding: ActivityMaterialDesignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialDesignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list1 = intent.getStringArrayListExtra("text1") ?: arrayListOf()
        val list2 = intent.getStringArrayListExtra("text2") ?: arrayListOf()
        val list3 = intent.getStringArrayListExtra("text3") ?: arrayListOf()

        val fragmentList = mutableListOf<Pair<String, androidx.fragment.app.Fragment>>()

        if (list1.isNotEmpty()) {
            fragmentList.add("Tab 1" to FragmentTabOne.newInstance(list1))
        }
        if (list2.isNotEmpty()) {
            fragmentList.add("Tab 2" to FragmentTabTwo.newInstance(list2))
        }
        if (list3.isNotEmpty()) {
            fragmentList.add("Tab 3" to FragmentTabThree.newInstance(list3))
        }

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragmentList.size
            override fun createFragment(position: Int) = fragmentList[position].second
        }

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragmentList[position].first
        }.attach()
    }
}