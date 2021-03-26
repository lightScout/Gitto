package com.britishbroadcast.gitto.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.britishbroadcast.gitto.databinding.ActivityMainBinding
import com.britishbroadcast.gitto.databinding.RepositoriesFragmentLayoutBinding
import com.britishbroadcast.gitto.model.data.GitResponse
import com.britishbroadcast.gitto.view.adapter.RepositoriesItemAdapter
import com.britishbroadcast.gitto.view.ui.MainActivity
import com.britishbroadcast.gitto.viewmodel.GittoViewModel

class RepositoriesFragment: Fragment(), RepositoriesItemAdapter.RepositoryItemDelegate {

    interface  RepositoryInterface{

    }

    private lateinit var binding: RepositoriesFragmentLayoutBinding
    private val repositoriesItemAdapter = RepositoriesItemAdapter(GitResponse(), this)
    private lateinit var repositoryInterface: RepositoryInterface
    private val gittoViewModel by activityViewModels<GittoViewModel>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = RepositoriesFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repositoriesRecyclerview.adapter = repositoriesItemAdapter
        arguments?.let{bundle ->
            val username  = bundle.getString("USER")
            gittoViewModel.getRepository().gitRepositoryLiveData.observe(viewLifecycleOwner, Observer {
                it.forEach{ gitResp ->

                    if(gitResp[0].owner.login == username)
                        repositoriesItemAdapter.updateRepositories(gitResp)
                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        repositoryInterface = (context as MainActivity)
    }

    override fun showCommits() {

    }

    override fun onDestroy() {
        super.onDestroy()
        gittoViewModel.getRepository().gitRepositoryLiveData.removeObservers(this)

    }

}