![bangwool-logo](bangwool_temp_logo.png)
> 내가 키우는 POMO Timer 방울이

<br>

![version](https://img.shields.io/badge/version-demo-orange)
[![CodeFactor](https://www.codefactor.io/repository/github/bangwoolz/bangwool-frontend-android/badge/develop)](https://www.codefactor.io/repository/github/bangwoolz/bangwool-frontend-android/overview/develop)

# 🍅 방울이 : 내가 키우는 POMO Timer
방울이는 기존에 있던 Pomodoro 타이머 기법을 더 즐겁게 활용하여 자신의 집중시간을 관리하는 서비스입니다.
#### 📃 [랜딩 페이지(개발중)]()
#### 🎥 [시연영상(제작중)]()

<br>

# 🏷️ 목차
### 1. 프로젝트 소개
###### &nbsp;&nbsp;1.1 overview
###### &nbsp;&nbsp;1.2 문제인식
###### &nbsp;&nbsp;1.3 해결방안
###### &nbsp;&nbsp;1.4 유저스토리
###### &nbsp;&nbsp;1.5 대상유저
### 2. 기능
###### &nbsp;&nbsp;2.1 로그인을 통해 개인 설정 저장(일반, 소셜)
###### &nbsp;&nbsp;2.2 타이머 추가, 편집, 삭제
###### &nbsp;&nbsp;2.3 오늘의 집중 시간
###### &nbsp;&nbsp;2.4 주간, 월간의 집중 시간 통계
###### &nbsp;&nbsp;2.5 일간, 주간 집중 시간 랭킹
###### &nbsp;&nbsp;2.6 계정 설정
### 3. 기대효과
###### &nbsp;&nbsp;3.1 즐거운 집중 시간
###### &nbsp;&nbsp;3.2 시간관리기법 공유
### 4. 설치안내
###### &nbsp;&nbsp;4.1 스토어 출시 이전(현재)
###### &nbsp;&nbsp;4.2 스토어 출시 이후(예정)
### 5. 기술스택
###### &nbsp;&nbsp;5.1 프론트
###### &nbsp;&nbsp;5.2 백엔드
###### &nbsp;&nbsp;5.3 협업
### 6. 팀정보

<br>

# 🍅 1. 프로젝트 소개
## &nbsp;&nbsp;🎯 1.1 overview
(사진, 타이머 사진?)

방울이는 기존에 존재하던 뽀모도로 시간관리 기법을 더 즐겁게 활용할 수 있도록 도와주는 서비스입니다.

서비스 이용자는 여러 종류의 타이머를 추가하고 실행하여 자신의 집중시간을 관리할 수 있습니다.

또한 자신의 집중 시간에 대한 통계, 랭킹을 확인할 수 있습니다.

귀여운 캐릭터와 쉬운 난이도, 기록 공유를 통한 동기부여로 더 즐겁게 기존의 시간관리법을 적용하면서 성장해나가는 자신을 발견할 수 있습니다.

주변을 둘러보면 시간관리에 관심이 있지만 시작하거나 지속하는데 어려움을 겪는 사람들을 쉽게 찾아볼 수 있습니다.

방울이는 그런 사람들에게 도움이 되고 더 나아가 시간관리 문화를 전파하고자 합니다.

## &nbsp;&nbsp;🤔 1.2 문제인식

(사진 플레이 스토어, 통계 그래프)

시간관리기법을 위한 서비스는 이미 많이 찾아볼 수 있습니다.

어플리케이션의 형태로 된 것들도 이미 다수 존재합니다.

또한 이미 시간관리에 대한 수요도 그만큼 많이 존재하고 있습니다.

경험적으로 많이 느끼셨겠지만 그러한 수요와 서비스의 규모에도 불구하고

성공적으로 시간관리를 할 수 있게된 사람은 그렇게 많지 않습니다.

통계(13번의 시도 - https://blog.gitnux.com/time-management-statistics/, https://www.acuitytraining.co.uk/news-tips/time-management-statistics-2022-research/)

많은 이유가 있겠지만 가장 많은 이유로는 실패에 대한 두려움 즉 부담감과 동기부여, 흥미제공 요인이 부족한 것을 꼽을 수 있겠습니다.

통계(main reason for pro.. is fear of failure - https://pubmed.ncbi.nlm.nih.gov/29845359/)

그래서 방울이는 느리지만 꾸준히 사람자체가 시간관리를 하는 성격으로 바꿀 수 있도록

가장 쉽고, 재밌고, 꾸준히 자극되는 방식을 추구하기로 했습니다.

## &nbsp;&nbsp;✅ 1.3 해결방안

(사진, 타이머, 통계, 랭킹, 귀여운 캐릭터, 방울 뽀모도로)

방울이는 이런 문제를 해결하고자 시간관리습관을 천천히 그러나 확실하게 만들어나가는데 초점이 맞춰진 솔루션이 되고자 합니다.

우선 사람을 기분좋게 만들어 부담감을 덜어주는 긍정적인 무드를 형성할 수 있는 귀여운 캐릭터와 디자인을 제공합니다.

그리고 역시 부담감을 줄이기 위한 방울 뽀모도로 컨셉을 가지고 있습니다.

방울 뽀모도로 컨셉이란 일반적인 뽀모도로 기법이 25분의 집중시간, 5분의 휴식시간을 갖는 것과 달리 15분의 집중시간과 5분의 휴식시간을 가지는 기법을 의미합니다.

서비스 이용자는 자신의 타이머 시간을 커스텀할 수 있지만 기본적으로 제공되는 타이머의 시간은 집중시간 15분과 휴식시간 5분으로 설정되어있고,

일일 통계인 오늘의 뽀모 또한 15분이 지날 때마다 나무에 토마토가 1개씩 추가됩니다.

이렇게 줄어든 부담감으로 첫 시작을 이끌어 내는데 성공했다면

더 나아가 통계와 랭킹, 공유(개발예정) 등을 통해 시간관리습관을 지속하는데에도 도움을 주고자 합니다.

## &nbsp;&nbsp;📖 1.4 유저스토리

난 왜 이렇게 의지가 부족할까?라고 생각하는 사람

항상 매번 자기계발 서적과 가기개발에 열중하지만 항상 작심삼일에 빠져 실패하곤 하는 김방울

쉽게 시작하고 어쩌고 저쩌고

## &nbsp;&nbsp;👤 1.5 대상유저

자기개발과 시간관리에 열정이 있지만 항상 실패하곤 하는 사람(과욕으로 인해)

시간관리에 동기부여가 좀 필요한 사람

귀여운 걸 좋아하는 사람?

<br>

# 🛠️ 2. 기능

## &nbsp;&nbsp;🚪 2.1 로그인을 통해 개인 설정 저장(일반, 소셜)

(사진, 로그인, 회원가입 gif)

- 자체적인 회원가입 로그인을 지원하고
- 카카오톡을 통한 소셜로그인도 지원합니다


## &nbsp;&nbsp;⏰ 2.2 타이머 추가, 편집, 삭제

(사진, 타이머 추가 편집 삭제 gif, 타이머 진행 gif)

- 타이머를 추가하고 삭제하고 편집할 수 있습니다
- 타이머를 진행할 수 있습니다


## &nbsp;&nbsp;🍅 2.3 오늘의 집중 시간

(사진, 오늘의 뽀모 구경 gif)

- 오늘의 토마토 수확량을 확인할 수 있습니다
- 공유도 할 수 있습니다(예정)

## &nbsp;&nbsp;📊 2.4 주간, 월간의 집중 시간 통계

(사진, 통계 구경하는, 목표시간 설정하는 gif)

- 주간, 월간 자신의 집중시간을 정리해서 볼 수 있습니다.
- 설정한 목표시간에 따라 다르게 보입니다.

## &nbsp;&nbsp;🏅 2.5 일간, 주간 집중 시간 랭킹

(사진, 랭킹 확인하는 gif)

- 주간, 월간 집중시간 랭킹을 볼 수 있습니다.
- 자신의 랭킹 확인 가능(예정)

## &nbsp;&nbsp;⚙️ 2.6 계정 설정

(사진, 여러 곳 확인하는 gif)

- 자신의 개인정보를 변경하거나 공지사항, 앱정보 등을 확인할 수 있습니다.
- 로그아웃이나 탈퇴진행이 가능합니다.

<br>

# 🏆 3. 기대효과
## &nbsp;&nbsp;😋 3.1 즐거운 집중 시간

방울이는 서비스의 취지에 맞게 집중시간에 대한 부담을 줄이고 동기를 부여할 수 있습니다.

결과적으로 사용자는 집중시간이 전보다 더 즐겁다는 것을 느끼게 됩니다.

## &nbsp;&nbsp;🏯 3.2 시간관리문화 활성화

방울이는 오늘의 뽀모 공유(개발중)와 랭킹 기능을 통해 자신의 집중시간을 공유할 수 있습니다.

이는 더 지속적인 집중습관개발이 가능하도록 도우며 많은 이들이 시간관리를 추구하도록 만들 수 있습니다.

<br>

# 💿 4. 설치안내
## &nbsp;&nbsp;📦 4.1 스토어 출시 이전(현재)

2023.08.10 현재 방울이는 스토어 미출시 상태이며, 출시를 준비하고 있습니다.

따라서 현재 방울이 서비스를 이용하기 위해서는 apk를 따로 다운로드 받아 설치하셔야 합니다.

[방울이 demo version 다운로드]()

## &nbsp;&nbsp;🛍️ 4.2 스토어 출시 이후(예정)

추후 방울이가 앱스토어에 출시된다면 해당 경로로 이동하여 설치하시면 됩니다.

<br>

# 💻 5. 기술스택

<div align=left> 
  <img src="https://img.shields.io/badge/android-3DDC84?style=for-the-badge&logo=android&logoColor=white"> 
  <img src="https://img.shields.io/badge/ios-000000?style=for-the-badge&logo=ios&logoColor=white">
  <img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"> 
  <br>
  
  <img src="https://img.shields.io/badge/spring%20boot-6DB33F?style=for-the-badge&logo=spring%20boot&logoColor=white"> 
  <img src="https://img.shields.io/badge/amazon%20aws-232F3E?style=for-the-badge&logo=amazon%20aws&logoColor=white">
  <br>

  
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> 
  <img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">
  <img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jira&logoColor=white">
</div>

<br>
  
# 👨‍👩‍👧‍👦 6. 팀정보
<table>
 <tr>
  <td align='center'>사진</td>
  <td align='center'>이름</td>
  <td align='center'>역할</td>
  <td align='center'>GitHub</td>
  <td align='center'>E-Mail</td>
 </tr>
   
 <tr>
  <td align='center'><img src="" width="50" height="50"></td>
  <td align='center'>이현희</td>
  <td align='center'>팀장<br>PM<br>FE-android 팀장<br>BE 개발</td>
  <td align='center'><a href="https://github.com/nonaninona"><img src="http://img.shields.io/badge/nonaninona-green?style=social&logo=github"/></a></td>
  <td align='center'><a href="mailto:starcraft0529@gmail.com"><img src="https://img.shields.io/badge/starcraft0529@gmail.com-green?logo=gmail&style=social"/></a></td>
 </tr>

 <tr>
  <td align='center'><img src="" width="50" height="50"></td>
  <td align='center'>김가영</td>
  <td align='center'>디자이너<br>앱 화면, 로고, 캐릭터 디자인<br>프로젝트 기획</td>
  <td align='center'><a href=""><img src="http://img.shields.io/badge/gayoung-green?style=social&logo=designer"/></a></td>
  <td align='center'><a href="mailto:gsu1620@naver.com"><img src="https://img.shields.io/badge/gsu1620@naver.com-green?logo=naver&style=social"/></a></td>
 </tr>
 
 <tr>
  <td align='center'><img src="" width="50" height="50"></td>
  <td align='center'>김태현</td>
  <td align='center'>FE-android 개발<br>timer, statistics 화면/기능 제작<br>프로젝트 기획</td>
  <td align='center'><a href="https://github.com/Unique0902"><img src="http://img.shields.io/badge/Unique0902-green?style=social&logo=github"/></a></td>
  <td align='center'><a href="mailto:rla0591@gmail.com"><img src="https://img.shields.io/badge/rla0591@gmail.com-green?logo=gmail&style=social"/></a></td>
 </tr>

 <tr>
  <td align='center'><img src="" width="50" height="50"></td>
  <td align='center'>김지원</td>
  <td align='center'>FE-android 개발<br>login, timer list, day statistics 화면/기능 제작</td>
  <td align='center'><a href="https://github.com/Kjiw0n"><img src="http://img.shields.io/badge/Kjiw0n-green?style=social&logo=github"/></a></td>
  <td align='center'><a href="mailto:rwd0904@naver.com"><img src="https://img.shields.io/badge/rwd0904@naver.com-green?logo=naver&style=social"/></a></td>
 </tr>

 <tr>
  <td align='center'><img src="" width="50" height="50"></td>
  <td align='center'>문현준</td>
  <td align='center'>FE-android 개발<br>register, ranking, mypage 화면/기능 제작</td>
  <td align='center'><a href="https://github.com/Mouon"><img src="http://img.shields.io/badge/Mouon-green?style=social&logo=github"/></a></td>
  <td align='center'><a href="mailto:ahemsapsldk@konkuk.ac.kr"><img src="https://img.shields.io/badge/ahemsapsldk@konkuk.ac.kr-green?logo=konkuk&style=social"/></a></td>
 </tr>
   
 <tr>
  <td align='center'><img src="" width="50" height="50"></td>
  <td align='center'>황재상</td>
  <td align='center'>FE-ios 팀장<br>ios 총개발</td>
  <td align='center'><a href="https://github.com/jxx-sx"><img src="http://img.shields.io/badge/jxx--sx-green?style=social&logo=github"/></a></td>
  <td align='center'><a href="mailto:jaesang00@kakao.com"><img src="https://img.shields.io/badge/jaesang00@kakao.com-green?logo=kakao&style=social"/></a></td>
 </tr>

  <tr>
  <td align='center'><img src="" width="50" height="50"></td>
  <td align='center'>김정우</td>
  <td align='center'>BackEnd 팀장<br>ERD 구상<br>서버 배포, CI/CD 구성<br>ppomodoro, Login 기능 구현</td>
  <td align='center'><a href="https://github.com/jung-woo-kim"><img src="http://img.shields.io/badge/jung--woo--kim-green?style=social&logo=github"/></a></td>
  <td align='center'><a href="mailto:rlawjddn103@naver.com"><img src="https://img.shields.io/badge/rlawjddn103@naver.com-green?logo=naver&style=social"/></a></td>
 </tr>

 <tr>
  <td align='center'><img src="" width="50" height="50"></td>
  <td align='center'>신희을</td>
  <td align='center'>BackEnd 개발<br>ERD 구상<br>work, member 기능 구현</td>
  <td align='center'><a href="https://github.com/ShinHeeEul"><img src="http://img.shields.io/badge/ShinHeeEul-green?style=social&logo=github"/></a></td>
  <td align='center'><a href="mailto:sheshe7015@naver.com"><img src="https://img.shields.io/badge/sheshe7015@naver.com-green?logo=naver&style=social"/></a></td>
 </tr>

  <tr>
  <td align='center'><img src="" width="50" height="50"></td>
  <td align='center'>이영선</td>
  <td align='center'>BackEnd 개발<br>ERD 구상<br>kakao login, login 기능 구현</td>
  <td align='center'><a href="https://github.com/lyouxsun"><img src="http://img.shields.io/badge/lyouxsun-green?style=social&logo=github"/></a></td>
  <td align='center'><a href="mailto:leedrkr323@naver.com"><img src="https://img.shields.io/badge/leedrkr323@naver.com-green?logo=naver&style=social"/></a></td>
 </tr>
</table>

