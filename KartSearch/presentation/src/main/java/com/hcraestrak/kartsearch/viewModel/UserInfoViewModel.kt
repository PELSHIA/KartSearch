package com.hcraestrak.kartsearch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.viewModel.data.InfoData
import com.hcraestrak.kartsearch.viewModel.data.RecordData
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class UserInfoViewModel: ViewModel() {
    private lateinit var job: Job

    private val _infoData = MutableLiveData<InfoData>()
    val infoData: LiveData<InfoData>
        get() = _infoData

    private val _recordData = MutableLiveData<RecordData>()
    val recordData: LiveData<RecordData>
        get() = _recordData

    private val URL: String = "https://kart.nexon.com/Garage/Main?strRiderID="
    private val recordURL: String = "https://kart.nexon.com/Garage/Record?strRiderID="

    fun getProfileData(nickName: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val doc: Document = Jsoup.connect(URL + nickName).get()
            val profileImg: String = doc.select("#RiderImg img").attr("src")
            val levelImg: String = doc.select("#GloveImg img").attr("src")
            val gulid: String = doc.select("#GuildName").text()
            withContext(Dispatchers.Main) {
                _infoData.postValue(InfoData(profileImg, levelImg, gulid))
            }
        }
    }

    fun getRecordData(nickName: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            var playTime: String
            var startTime: String
            val doc: Document = Jsoup.connect(recordURL + nickName).get()
            val time: Elements = doc.select(".CntRecord21 dd")
            startTime = time[0].text()
            playTime = time[1].text()
            withContext(Dispatchers.Main) {
                _recordData.postValue(RecordData(playTime, startTime))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}