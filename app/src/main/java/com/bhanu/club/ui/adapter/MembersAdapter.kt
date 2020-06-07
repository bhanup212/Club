package com.bhanu.club.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bhanu.club.R
import com.bhanu.club.databinding.MemberRowItemBinding
import com.bhanu.club.model.Club


/**
 * Created by Bhanu Prakash Pasupula on 07,Jun-2020.
 * Email: pasupula1995@gmail.com
 */
class MembersAdapter(private val members:ArrayList<Club.Member>):RecyclerView.Adapter<MembersAdapter.ViewHolder>(),Filterable {

    private var filteredMembers = members

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = MemberRowItemBinding.inflate(inflater,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return filteredMembers.size
    }

    override fun onBindViewHolder(holder: MembersAdapter.ViewHolder, position: Int) {
       val member = filteredMembers[position]
        holder.bindData(member)

        holder.binding.favoriteMem.setOnClickListener {
            member.isFavorite = !member.isFavorite
            notifyItemChanged(position)
        }
    }

    inner class ViewHolder(val binding:MemberRowItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bindData(member:Club.Member){
            member.run {
                binding.emailTv.text = email
                binding.phoneNumberTv.text = phone
                binding.name.text = "${name.first} ${name.last}"
                binding.age.text = "$age years"
            }

            val imgResId = if (member.isFavorite){
                R.drawable.ic_baseline_star_24
            }else{
                R.drawable.ic_baseline_star_border
            }
            binding.favoriteMem.setImageResource(imgResId)
        }
    }
    fun sortByName(isAscending:Boolean=true){
        filteredMembers = if (isAscending){
            ArrayList(members.sortedBy { it.name.first })
        }else{
            ArrayList(members.sortedByDescending { it.name.first })
        }
        notifyDataSetChanged()
    }

    fun sortByAge(isAscending:Boolean=true){
        filteredMembers = if (isAscending){
            ArrayList(members.sortedBy { it.age })
        }else{
            ArrayList(members.sortedByDescending { it.age })
        }
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}