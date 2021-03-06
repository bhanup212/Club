package com.bhanu.club.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bhanu.club.R
import com.bhanu.club.databinding.FragmentClubsBinding
import com.bhanu.club.model.Club
import com.bhanu.club.ui.adapter.ClubsAdapter
import com.bhanu.club.ui.viewmodel.ClubViewModel
import kotlinx.android.synthetic.main.fragment_clubs.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ClubsFragment : Fragment(),ClubsAdapter.ClickListener {

    private val viewModel:ClubViewModel by sharedViewModel()

    private lateinit var binding: FragmentClubsBinding
    private lateinit var adapter: ClubsAdapter

    private var selectedSort = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, Callback())
    }
    inner class Callback : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            activity?.finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClubsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModels()
        fetchClubs()
        setClubsRv()
        clickListeners()
    }
    private fun fetchClubs(){
        if (viewModel.clubs.value == null){
            viewModel.getClubs()
        }
    }
    private fun observeViewModels(){
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.progressbar.isVisible=it
        })
        viewModel.clubs.observe(viewLifecycleOwner, Observer {
                updateClubList(it)
        })
        viewModel.errorMsg.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
        })
    }
    private fun setClubsRv(){
        adapter = ClubsAdapter()
        adapter.setListener(this)
        binding.clubsRv.adapter = adapter
        binding.clubsRv.setHasFixedSize(true)
    }
    private fun updateClubList(list:List<Club>){
        adapter.updateClubs(list as ArrayList<Club>)
    }
    private fun clickListeners(){
        binding.sortBy.setOnClickListener {
            showSortByPopUp()
        }

        binding.searchEdt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                adapter.filter.filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }
    private fun showSortByPopUp(){
        val popupWindow = PopupWindow(requireContext())
        val layout = LayoutInflater.from(requireContext()).inflate(R.layout.sort_company_layout,binding.filterLayout,false)
        popupWindow.apply {
            contentView = layout
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
            isFocusable = true
            elevation = 4.0f
        }
        val ascendingRb:RadioButton = layout.findViewById(R.id.ascending)
        val descending:RadioButton = layout.findViewById(R.id.descending)

        popupWindow.showAsDropDown(binding.sortBy)


        ascendingRb.setOnClickListener {
            if (selectedSort != 1){
                selectedSort = 1
                adapter.sortByAscending()
            }
            popupWindow.dismiss()
        }
        descending.setOnClickListener {
            if (selectedSort != 2){
                selectedSort = 2
                adapter.sortByDescending()
            }
            popupWindow.dismiss()
        }

        when(selectedSort){
            1 -> ascendingRb.isChecked = true
            2 -> descending.isChecked = true
        }
    }

    override fun onClick(club: Club) {
        val navDir = ClubsFragmentDirections.actionClubFragmentToClubMemberFragment(club)
        findNavController().navigate(navDir)
    }
}