# KartSearch - 카트라이더 전적 검색 서비스

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/a768e840-f2b0-4f8c-b639-798453893928/Surface_Pro_8_-_1.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221017%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221017T014926Z&X-Amz-Expires=86400&X-Amz-Signature=c4c60c19e879e225584d2a84913c1f432ee67124b16610c0441c1df318181c97&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Surface_Pro_8_-_1.png%22&x-id=GetObject">

<br>

# 👨‍💻 개발자

|<img src="https://avatars.githubusercontent.com/u/63153516?v=4" width="100"/>|
|:--:|
|[지민혁](https://github.com/MinHyukJi1226)|

<br>

# ⏰ 개발 기간

2021.11 ~ 2022.04, 2022.09 ~ (리팩토링)

<br>

# 📝 기능 소개

- 유저 전적 검색
    - 닉네임 검색해 나의 게임 전적을 검색 가능
- 유저 통계 분석
    - 완주율과 승률, 평균 순위, 선호 트랙 통계 등 모드별 통계 제공
- 더 자세한 게임 내용 분석
    - 내가 했던 게임의 승패 여부, 참가했던 유저의 순위, 당시 플레이 했던 맵 등의 게임 정보를 볼 수 있음

<br>

# 🛠️ 사용 기술

### 리펙토링 기준

| 내용 | 기술 |
|---|:---:|
| 언어 | Kotlin |
|아키텍처| MVVM , Clean-Architecture|
|비동기 처리| Coroutines |
| API 통신 | Retrofit2 |
| DI | Dagger-Hilt |
| Jetpack | Navigation, DataBinding, Room, LiveData, ViewModel |
| Server | Firebase |
| 기타 | Jsoup, MPAndroidChart, Glide |
<br>

# 📁 파일구조

### 리펙토링 기준

```
Data
├─dataSource
│  ├─local
│  └─remote
├─local
│  ├─database
│  ├─model
│  └─service
├─mapper
├─remote
│  ├─model
│  └─service
└─repository

Domain
├─model
│  ├─local
│  └─remote
├─repository
└─useCase
    ├─local
    │  ├─bookmark
    │  └─search
    └─remote
        ├─match
        └─user

Presentation
├─app
├─di
├─view
│  ├─activity
│  ├─adapter
│  │  ├─data
│  │  └─listener
│  ├─base
│  ├─bindingAdapter
│  ├─decoration
│  └─fragment
└─viewModel
    └─data // For Jsoup
```
