package com.britishbroadcast.gitto.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.britishbroadcast.gitto.R
import com.britishbroadcast.gitto.databinding.SplashScreenLayoutBinding
import com.britishbroadcast.gitto.view.ui.fragment.LoginScreenFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_fragment_layout.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SplashScreenFragment: Fragment() {

    private lateinit var binding: SplashScreenLayoutBinding
    private val loginScreenFragment = LoginScreenFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SplashScreenLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            logoImageView.setImageResource(R.drawable.logo)
        }

        //    Coroutine
        GlobalScope.launch {
            delay(2000L)
//           // Thi is where we will call the login page
//            // For now it is only popBackStack

            parentFragmentManager.popBackStack()

            parentFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                )
                .add(R.id.main_frameLayout, loginScreenFragment)
                .addToBackStack(null)
        }

    }
}





