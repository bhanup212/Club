package com.bhanu.club.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RadioButton
import androidx.navigation.fragment.navArgs
import com.bhanu.club.R
import com.bhanu.club.databinding.FragmentClubMembersBinding
import com.bhanu.club.model.Club
import com.bhanu.club.ui.adapter.MembersAdapter


class ClubMembersFragment : Fragment() {

    private val args:ClubMembersFragmentArgs by navArgs()

    private lateinit var binding: FragmentClubMembersBinding
    private lateinit var adapter:MembersAdapter

    private var selectedSortType = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClubMembersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }
    private fun setUpRecyclerView(){
        adapter = MembersAdapter(args.club.members)
        binding.membersRv.adapter=adapter
        adapter.sortByAge(false)
    }
    private fun showSortByPopUp(){
        val popupWindow = PopupWindow(requireContext())
        val layout = LayoutInflater.from(requireContext()).inflate(R.layout.filter_member_layout,binding.filterLayout,false)
        popupWindow.apply {
            contentView = layout
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
            isFocusable = true
            elevation = 4.0f
        }
    }
}