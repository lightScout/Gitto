package com.britishbroadcast.gitto.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.britishbroadcast.gitto.R
import com.britishbroadcast.gitto.databinding.SettingsFragmentLayoutBinding
import com.britishbroadcast.gitto.view.ui.MainActivity
import com.britishbroadcast.gitto.viewmodel.GittoViewModel

class SettingsFragment: Fragment() {

    private lateinit var binding: SettingsFragmentLayoutBinding

    private val gittoViewModel by activityViewModels<GittoViewModel>()

    interface SettingAccount {

    }

    private lateinit var settingAccount: SettingAccount

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            gitHubLogoImageview.setImageResource(R.drawable.github_icon)
            backArrowImageview.setImageResource(R.drawable.left_arrow)

            this.logoutButton.setOnClickListener {

            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        settingAccount = (context as MainActivity)
    }
}