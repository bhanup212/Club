package com.bhanu.club.model


/**
 * Created by Bhanu Prakash Pasupula on 04,Jun-2020.
 * Email: pasupula1995@gmail.com
 */
data class Club(
    val _id:String,
    val company:String,
    val website:String,
    val logo:String,
    val about:String,
    var isFollowing:Boolean=false,
    var isFavorite:Boolean=false,
    val members:List<Member>
){
    data class Member(
        val _id: String,
        val age:Int,
        val email:String,
        val phone:String,
        val name: Name,
        var isFavorite:Boolean=false
    ){
        data class Name(
            val first:String,
            val last:String
        )
    }
}