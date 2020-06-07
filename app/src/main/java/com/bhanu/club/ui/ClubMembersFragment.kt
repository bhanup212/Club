package com.bhanu.club.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RadioButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bhanu.club.MainActivity
import com.bhanu.club.R
import com.bhanu.club.databinding.FragmentClubMembersBinding
import com.bhanu.club.model.Club
import com.bhanu.club.ui.adapter.MembersAdapter
import kotlinx.android.synthetic.main.fragment_club_members.*


class ClubMembersFragment : Fragment() {

    private val args: ClubMembersFragmentArgs by navArgs()

    private lateinit var binding: FragmentClubMembersBinding
    private lateinit var adapter: MembersAdapter

    private var memberSort: MemberSort = MemberSort.NONE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClubMembersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableToobar()
        setUpRecyclerView()
        clickListeners()
    }

    private fun enableToobar() {
        binding.toolbar.title = args.club.company
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
    }

    private fun setUpRecyclerView() {
        adapter = MembersAdapter(args.club.members)
        binding.membersRv.adapter = adapter
        adapter.sortByAge(false)
    }

    private fun clickListeners() {
        binding.sortBy.setOnClickListener {
            showSortByPopUp()
        }
        binding.searchEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                adapter.filter.filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showSortByPopUp() {
        val popupWindow = PopupWindow(requireContext())
        val layout = LayoutInflater.from(requireContext())
            .inflate(R.layout.filter_member_layout, binding.filterLayout, false)
        popupWindow.apply {
            contentView = layout
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
            isFocusable = true
            elevation = 4.0f
        }

        val nameAsce = layout.findViewById<RadioButton>(R.id.sort_name_ascending)
        val nameDesc = layout.findViewById<RadioButton>(R.id.sort_name_descending)
        val ageAsce = layout.findViewById<RadioButton>(R.id.sort_age_ascending)
        val ageDesc = layout.findViewById<RadioButton>(R.id.sort_age_descending)

        when (memberSort) {
            MemberSort.NAME_ASCEN -> nameAsce.isChecked = true
            MemberSort.NAME_DESC -> nameDesc.isChecked = true
            MemberSort.AGE_ASCEN -> ageAsce.isChecked = true
            MemberSort.AGE_DESC -> ageDesc.isChecked = true
        }

        nameAsce.setOnClickListener {
            if (memberSort != MemberSort.NAME_ASCEN) {
                memberSort = MemberSort.NAME_ASCEN
                adapter.sortByName()
            }
            popupWindow.dismiss()
        }
        nameDesc.setOnClickListener {
            if (memberSort != MemberSort.NAME_DESC) {
                memberSort = MemberSort.NAME_DESC
                adapter.sortByName(false)
            }
            popupWindow.dismiss()
        }
        ageAsce.setOnClickListener {
            if (memberSort != MemberSort.AGE_ASCEN) {
                memberSort = MemberSort.AGE_ASCEN
                adapter.sortByAge()
            }
            popupWindow.dismiss()
        }
        ageDesc.setOnClickListener {
            if (memberSort != MemberSort.AGE_DESC) {
                memberSort = MemberSort.AGE_DESC
                adapter.sortByAge(false)
            }
            popupWindow.dismiss()
        }
        popupWindow.showAsDropDown(binding.sortBy)

    }

    enum class MemberSort {
        NAME_ASCEN,
        NAME_DESC,
        AGE_ASCEN,
        AGE_DESC,
        NONE
    }
}