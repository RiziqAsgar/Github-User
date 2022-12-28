package com.example.githubusernavigationdanapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusernavigationdanapi.adapter.UserListAdapter
import com.example.githubusernavigationdanapi.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private lateinit var userViewModel : UserViewModel
    private lateinit var followingAdapter: UserListAdapter
    private lateinit var username: String

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFollowingBinding.bind(view)

        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()

        followingAdapter = UserListAdapter()

        Log.d("TAG", "cek $username")

        binding?.apply {
            rvList.layoutManager = LinearLayoutManager(requireContext())
            rvList.adapter = followingAdapter
            rvList.setHasFixedSize(true)
        }

        showLoading(true)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.setDetailFollowing(username)
        userViewModel.getDetailFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                showLoading(false)
                Log.d("TAG", "cek api $it")
                binding?.rvList?.visibility = View.VISIBLE
                followingAdapter.setData(it)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.progressCircular?.visibility = View.VISIBLE
        } else {
            binding?.progressCircular?.visibility = View.GONE
        }
    }

}