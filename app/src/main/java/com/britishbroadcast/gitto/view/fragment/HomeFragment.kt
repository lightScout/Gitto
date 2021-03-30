package com.britishbroadcast.gitto.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.britishbroadcast.gitto.R
import com.britishbroadcast.gitto.databinding.HomeFragmentLayoutBinding
import com.britishbroadcast.gitto.model.data.GitResponse
import com.britishbroadcast.gitto.view.adapter.UserItemAdapter
import com.britishbroadcast.gitto.view.ui.MainActivity
import com.britishbroadcast.gitto.viewmodel.GittoViewModel

class HomeFragment: Fragment(), UserFragment.UserFragmentInterface, RepositoriesFragment.RepositoryInterface{

    private lateinit var binding: HomeFragmentLayoutBinding
    private var userFragment = UserFragment(this)
    private var repositoryFragment = RepositoriesFragment(this)
    private var commitsFragment = CommitsFragment()
    private lateinit var encryptedSharedPreferences: SharedPreferences
    private val gittoViewModel by activityViewModels<GittoViewModel>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val masterKey =
            MasterKey.Builder(this).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        encryptedSharedPreferences = EncryptedSharedPreferences.create(
            this,
            packageName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val git_token = encryptedSharedPreferences.getString("GIT_HUB_TOKEN", "")
        if (git_token != null) {
            gittoViewModel.getRepository().getGitUserPrivateRepo(git_token)
        }

        parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                ).add(binding.homeFrameLayout.id, userFragment)
                .addToBackStack(null)
                .commit()



    }

    override fun displayRepositoriesFragment(login: String) {
        parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                )
                .replace(binding.homeFrameLayout.id, repositoryFragment.also{
                    val bundle = Bundle()
                    bundle.putString("USER", login)
                    it.arguments = bundle
                })
                .addToBackStack(null)
                .commit()
    }

    override fun displayCommitsFragment() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .replace(binding.homeFrameLayout.id, commitsFragment)
            .addToBackStack(null)
            .commit()
    }


}
