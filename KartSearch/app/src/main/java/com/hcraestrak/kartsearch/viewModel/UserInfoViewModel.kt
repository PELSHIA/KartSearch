package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.viewModel.data.InfoData
import com.hcraestrak.kartsearch.viewModel.data.RecordData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class UserInfoViewModel: ViewModel() {
    private val URL: String = "https://kart.nexon.com/Garage/Main?strRiderID="
    private val recordURL: String = "https://kart.nexon.com/Garage/Record?strRiderID="

    fun getProfileData(nickName: String): Single<InfoData> {
        return Single.fromObservable (
            Observable.create {
                val profileData: InfoData
                val doc: Document = Jsoup.connect(URL + nickName).get()
                val profileImg: String = doc.select("#RiderImg img").attr("src")
                val levelImg: String = doc.select("#GloveImg img").attr("src")
                val gulid: String = doc.select("#GuildName").text()
                profileData = InfoData(profileImg, levelImg, gulid)
                it.onNext(profileData)
                it.onComplete()
            }
        )
    }

    fun getRecordData(nickName: String): Single<RecordData> {
        return Single.fromObservable (
            Observable.create {
                var recordData: RecordData
                var playTime: String
                var startTime: String
                val doc: Document = Jsoup.connect(recordURL + nickName).get()
                val time: Elements = doc.select(".CntRecord21 dd")
                startTime = time[0].text()
                playTime = time[1].text()
                recordData = RecordData(playTime, startTime)
                it.onNext(recordData)
                it.onComplete()
            }
        )
    }
}