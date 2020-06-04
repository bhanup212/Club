package com.bhanu.club.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.bhanu.club.R
import com.bhanu.club.databinding.CompanyRowItemBinding
import com.bhanu.club.model.Club
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Bhanu Prakash Pasupula on 04,Jun-2020.
 * Email: pasupula1995@gmail.com
 */
class ClubsAdapter:RecyclerView.Adapter<ClubsAdapter.ViewHolder>(),Filterable {
    private var clubList = ArrayList<Club>()
    private var originalClubList = ArrayList<Club>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CompanyRowItemBinding.inflate(inflater,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return clubList.size
    }

    override fun onBindViewHolder(holder: ClubsAdapter.ViewHolder, position: Int) {
        val club = clubList[position]
        holder.bindData(club)

        holder.binding.favorite.setOnClickListener{
            club.isFavorite = !club.isFavorite
            notifyItemChanged(position)
        }
        holder.binding.follow.setOnClickListener {
            club.isFollowing = !club.isFollowing
            notifyItemChanged(position)
        }
    }
    fun updateClubs(list:ArrayList<Club>){
        clubList.clear()
        clubList.addAll(list)
        originalClubList = list
        notifyDataSetChanged()
    }
    fun sortByAscending(){
        clubList = ArrayList(clubList.sortedBy { it.company })
        notifyDataSetChanged()
    }
    fun sortByDescending(){
        clubList = ArrayList(clubList.sortedByDescending { it.company })
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: CompanyRowItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bindData(club: Club){
            binding.apply {
                companyName.text = club.company
                companyLogo.load(club.logo){
                    placeholder(R.drawable.ic_baseline_apartment)
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                companyDesc.text = club.about

                follow.text = if (club.isFollowing){
                    "unfollow"
                }else{
                    "follow"
                }

                favorite.text = if (club.isFavorite){
                    "unfavorite"
                }else{
                    "favorite"
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                clubList = if (constraint?.isNullOrEmpty()!!){
                    originalClubList
                }else {
                    val tempList = ArrayList<Club>()
                    for (company in originalClubList) {
                        if (company.company.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tempList.add(company)
                        }
                    }
                    tempList
                }


                val filteredResults = FilterResults()
                filteredResults.values = clubList

                return filteredResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clubList = results?.values as ArrayList<Club>
                notifyDataSetChanged()
            }

        }
    }
}