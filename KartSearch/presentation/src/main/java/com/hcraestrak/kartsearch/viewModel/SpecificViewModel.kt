package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailPlayer
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailTeam
import com.hcraestrak.kartsearch.model.repo.SpecificRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SpecificViewModel @Inject constructor(private val repo: SpecificRepository): ViewModel() {

    lateinit var job: Job

    private val _matchDetail = MutableLiveData<MatchDetailPlayer>()
    private val _matchTeamDetail = MutableLiveData<MatchDetailTeam>()

    val matchDetail: LiveData<MatchDetailPlayer>
        get() = _matchDetail
    val matchTeamDetail: LiveData<MatchDetailTeam>
        get() = _matchTeamDetail

    fun specificMatchInquiry(matchId: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repo.specificMatchInquiry(matchId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _matchDetail.postValue(response.body())
                } else {
                    Log.d("Error", response.message())
                    _matchDetail.postValue(null)
                }
            }
        }
    }

    fun specificTeamMatchInquiry(matchId: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repo.specificTeamMatchInquiry(matchId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _matchTeamDetail.postValue(response.body())
                } else {
                    Log.d("Error", response.message())
                    _matchTeamDetail.postValue(null)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}