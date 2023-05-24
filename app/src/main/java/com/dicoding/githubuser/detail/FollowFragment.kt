package com.dicoding.githubuser.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.adapter.DataUserAdapter
import com.dicoding.githubuser.databinding.FragmentFollowBinding
import com.dicoding.githubuser.data.retrofit.ItemsItem

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private var position: Int? = null
    private var username: String? = null
//    private val detailViewModel by viewModels<DetailViewModel>{
//        ViewModelFactory.getInstance(requireActivity())
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvDataFollow.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvDataFollow.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvDataFollow.addItemDecoration(itemDecoration)

        val followViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1){
            followViewModel.setFollowers(username.toString())
            followViewModel.getFollowers().observe(requireActivity(),{
                setDataFollow(it)
            })
        } else {
            followViewModel.setFollowing(username.toString())
            followViewModel.getFollowing().observe(requireActivity(),{
                setDataFollow(it)
            })
        }
        followViewModel.isLoading.observe(requireActivity(), {
            showLoading(it)
        })
    }

    private fun setDataFollow(item: List<ItemsItem>) {
        val adapter = DataUserAdapter(item)
        binding.rvDataFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_USERNAME = "arg_username"
        const val ARG_POSITION = "arg_position"
    }
}