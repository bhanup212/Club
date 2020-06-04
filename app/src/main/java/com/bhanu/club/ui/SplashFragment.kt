package com.bhanu.club.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bhanu.club.R


class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gotoClubsScreen()
    }
    private fun gotoClubsScreen(){
        Handler().postDelayed({
            navigateToClubs()
        },SPLASH_DELAY)
    }
    private fun navigateToClubs(){
        try {
            findNavController().navigate(R.id.action_splashFragment_to_clubFragment)
        } catch (e: Exception) {
        }
    }

    companion object{
        private const val SPLASH_DELAY:Long = 2000
    }
}