# Juggernaut Method Basic - Android

### About

[Juggernaut Basic Method Base Template SpreadSheet](https://liftvault.com/programs/strength/juggernaut-method-base-template-spreadsheet/) 의 Android 버전입니다.

번거롭게 일일이 스프레드 시트에 기록할 필요가 **전혀** 없습니다. 클릭 몇 번이면 자동으로 마이크로 사이클에 맞는 루틴이 생성됩니다.

### Screen

![onboarding_header_light](https://user-images.githubusercontent.com/50101902/161043182-28fb7b48-74d9-468b-89c1-8c7b2963c4b7.png)
![onboarding_form_input](https://user-images.githubusercontent.com/50101902/161041979-6ba218c2-a92a-48d3-a1e7-8a360d25776d.png)
![home_overview_light](https://user-images.githubusercontent.com/50101902/161042092-975cd902-e65f-48d6-8fde-0a498b397e24.png)
![home_detail_light](https://user-images.githubusercontent.com/50101902/161042087-b84c0965-5286-4c1f-855b-8d99e0f3f5c6.png)
![overall_1_light](https://user-images.githubusercontent.com/50101902/161042343-fbb6b9be-4fa3-418e-a002-01739e94d70f.png)
![record_2_light](https://user-images.githubusercontent.com/50101902/161042516-f21fa0a8-d164-4b2c-a9fe-9f68704c66f1.png)
![accomplishement_1_light](https://user-images.githubusercontent.com/50101902/161042564-8ba4077d-05f4-4393-ae02-8ce17b7e52d8.png)
![statistic_light](https://user-images.githubusercontent.com/50101902/161042737-c67947fd-7836-4305-b51a-daafafed7125.png)

눈이 편안한 다크모드도 준비되어있습니다.

![home_detail_dark](https://user-images.githubusercontent.com/50101902/161044143-cfea6324-b300-4fd4-8795-db8b583c46b2.png)
![overall_2_dark](https://user-images.githubusercontent.com/50101902/161044158-dd34bd71-2529-4edb-abe7-2df98f61f639.png)
![statistic_dark](https://user-images.githubusercontent.com/50101902/161044174-00efe96b-d56b-431b-a7c3-7544d463012a.png)
![accomplishment_1_dark](https://user-images.githubusercontent.com/50101902/161044217-572be77f-f8cc-45a7-883c-4c159e66b176.png)

### MAD Scores

![summary](https://user-images.githubusercontent.com/50101902/161045296-4e84d7c0-683c-476d-9c74-05e46b1858c5.png)
![jetpack](https://user-images.githubusercontent.com/50101902/161045291-6b7c741b-1b53-48ba-96ec-ffc7eaf59f84.png)
![kotlin](https://user-images.githubusercontent.com/50101902/161045294-688e95f4-3f55-4228-ab71-f22ef5a92df0.png)

### Development

[공식 가이드 라인](https://developer.android.com/jetpack/guide)에서 권장하는 아키텍처를 적용하여 개발하였습니다.

- UseCase 기반으로 각각의 domain들을 정의하였고, UseCase를 조합하여 **Contract**라는 더 큰 단위로 domain의 문제들을 해결하였습니다.

https://github.com/valiantwarrior/juggernaut-method-base/blob/a09f6e7d50c90c7ed5dc893f28201db896c4be97/android/app/src/main/java/kr/valor/juggernaut/domain/progression/usecase/usecase/LoadProgressionStateUseCase.kt#L8-L15

https://github.com/valiantwarrior/juggernaut-method-base/blob/a09f6e7d50c90c7ed5dc893f28201db896c4be97/android/app/src/main/java/kr/valor/juggernaut/domain/session/usecase/contract/SynchronizeSessionsContract.kt#L11-L15


- 앱의 메인 로직들은 대부분 Contract 기반으로 실행됩니다.

https://github.com/valiantwarrior/juggernaut-method-base/blob/a09f6e7d50c90c7ed5dc893f28201db896c4be97/android/app/src/main/java/kr/valor/juggernaut/ui/home/overview/OverviewViewModel.kt#L52-L56

- [Screaming architecture](https://blog.cleancoder.com/uncle-bob/2011/09/30/Screaming-Architecture.html) 를 [지향](https://proandroiddev.com/why-you-need-use-cases-interactors-142e8a6fe576)하였습니다.

![image](https://user-images.githubusercontent.com/50101902/161050300-1ed5496f-dc34-4f1c-9daa-087123aa069a.png)
![image](https://user-images.githubusercontent.com/50101902/161049611-e7c63f2c-394b-4d89-8c40-d2fcee7b3157.png)


### Development Stack
- Kotlin (Coroutines API, Flow API)
- Android AAC (LifeCycle, LiveData, etc.) 
- Android MVVM with DataBinding
- Android Jetpack (Navigation, Room, ViewModel, DataStore etc.)
- Material UI (Material 2)
- Hilt
