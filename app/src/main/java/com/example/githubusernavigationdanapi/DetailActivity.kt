package com.example.githubusernavigationdanapi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusernavigationdanapi.adapter.SectionPagerAdapter
import com.example.githubusernavigationdanapi.adapter.UserListAdapter
import com.example.githubusernavigationdanapi.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var detailActivity: UserListAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        detailActivity = UserListAdapter()
        setActionBar()
        showLoading(true)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        username?.let { viewModel.setDetailUser(it) }
        showTabLayout()

        viewModel.getUserDetail().observe(this) {
            binding.tvItemUsername.text = "@${it.login}"
            binding.tvItemName.text = it.name
            Glide.with(this)
                .load(it.avatar_url)
                .into(binding.imgItemPhoto)
            binding.tvItemFollowers.text = "${it.followers} Followers"
            binding.tvItemFollowing.text = "${it.following} Following"
            binding.tvItemCompany.text = "Company : ${it.company}"
            binding.tvItemRepository.text = "${it.public_repos} Repository"
            binding.tvItemLocation.text = "Location : ${it.location}"
                if (it != null) {
                    showLoading(false)
                    Log.d("TAG", "cek api $it")
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_detail, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_close -> {
               onBackPressed()
                true
            }
            else->true
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun setActionBar() {
        supportActionBar?.title = getString(R.string.app_title_detail)
    }
    private fun showTabLayout(){
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        val sectionsPagerAdapter = SectionPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
    companion object{
        const val EXTRA_USERNAME = "username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.txt_followers,
            R.string.txt_following
        )
    }
}

