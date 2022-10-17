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

- 일기를 간단하게 작성
    - 매일 쓸 수 있는 일기는 태그와 함께 저장할 수 있습니다. 그날의 일기를 태그로 요약해보세요
- 앱을 원하는 색으로
    - 사람마다 원하는 색이 다를 수 있습니다. 테마 설정을 통해 자신이 원하는 스타일로 일기 앱을 이용할 수 있습니다.
- 검색은 간편하게
    - 일기와 함께 태그를 작성했다면, 그 태그에 맞게 검색할 수 있습니다. 또한, 같은 태그로 이루어진 일기들을 모아 볼 수 있습니다.
- 잠금으로 안전하게
    - 사용자의 프라이버시는 중요합니다. 서버를 통해 암호화를 저장되는 것은 물론, 설정화면에서 제공되는 앱 잠금을 통해 일기를 안전하게 보호하세요.

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
