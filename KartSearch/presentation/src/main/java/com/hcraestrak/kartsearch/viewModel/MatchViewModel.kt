package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcraestrak.domain.model.remote.DetailSingle
import com.hcraestrak.domain.model.remote.DetailTeam
import com.hcraestrak.domain.model.remote.Match
import com.hcraestrak.domain.useCase.remote.match.AccessIdMatchInquiryUseCase
import com.hcraestrak.domain.useCase.remote.match.AllMatchInquiryUseCase
import com.hcraestrak.domain.useCase.remote.match.SpecificSingleMatchInquiryUseCase
import com.hcraestrak.domain.useCase.remote.match.SpecificTeamMatchInquiryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val accessIdMatchInquiryUseCase: AccessIdMatchInquiryUseCase,
    private val allMatchInquiryUseCase: AllMatchInquiryUseCase,
    private val specificSingleMatchInquiryUseCase: SpecificSingleMatchInquiryUseCase,
    private val specificTeamMatchInquiryUseCase: SpecificTeamMatchInquiryUseCase
    ) : ViewModel() {

    private var _matchResponse = MutableLiveData<Match>()
    private val _detailSingle = MutableLiveData<DetailSingle>()
    private val _detailTeam = MutableLiveData<DetailTeam>()

    val matchResponse: LiveData<Match>
        get() = _matchResponse
    val detailSingle: LiveData<DetailSingle>
        get() = _detailSingle
    val detailTeam: LiveData<DetailTeam>
        get() = _detailTeam

    fun accessIdMatchInquiry(access_Id: String, matchType: String, limit: Int = 100) {
        viewModelScope.launch(Dispatchers.IO) {
            val match = accessIdMatchInquiryUseCase.execute(access_Id, matchType, limit = limit)
            _matchResponse.postValue(match)
        }
    }

    fun specificSingleMatchInquiry(matchId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val match = specificSingleMatchInquiryUseCase.execute(matchId)
            _detailSingle.postValue(match)
        }
    }

    fun specificTeamMatchInquiry(matchId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val match = specificTeamMatchInquiryUseCase.execute(matchId)
            _detailTeam.postValue(match)
        }
    }
}