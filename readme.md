## 2022학년도 2학기 모바일 프로그래밍 개인 과제
### 소프트웨어학부 20191598 박정명
<hr />

<div>
  <img src="https://user-images.githubusercontent.com/50445943/199218409-327fd20c-8523-4d7d-9416-c0ebe0e28704.png" width="200px"/>
  <img src="https://user-images.githubusercontent.com/50445943/199218535-83240f96-6243-46d6-9253-de970a9cc020.png" width="200px"/>
</div>

### 목차
- 개발 환경 및 실행 환경
- 애플리케이션 설치 방법
- 기능 설명

### 개발환경 및 실행 환경
- 개발 환경
  - IDE: Android Studio 11.0.13
  - SDK: API 32 (Android 12)
- 실행 환경
  - Android Virtual Device (AVD): Pixel 2 API 31 (Android 12)
  - Real Device: Galaxy S21+ (Android 12)
  - SQLite 사용 및 내부저장소 접근

### 애플리케이션 설치 방법 (방법 택 중 1)
- Github Repository 최상단 경로의 `app-debug.apk` 설치
  - 디버깅 용 애플리케이션으로 빌드됨.
- Github Repository `Clone` 및 Android Studio `Run 'app'`

### 기능 설명
- 초기 화면 (Splash) 
  <div>
    <img src="https://user-images.githubusercontent.com/50445943/199218409-327fd20c-8523-4d7d-9416-c0ebe0e28704.png" width="200px"/>
  </div>
  - MainActivity가 동작하기 전에 애플리케이션의 로고가 출력되는 화면이다.
  - Android의 Splash Screen API (in Android 31 ~) 을 활용하였다.
- 로그인 화면 (MainActivity)
  <div>
    <img src="https://user-images.githubusercontent.com/50445943/199218535-83240f96-6243-46d6-9253-de970a9cc020.png" width="200px"/>
    <img src="https://user-images.githubusercontent.com/50445943/199218904-d602ddaa-4443-4309-9bb0-47e1d0caf0a0.png" width="200px"/>
    <img src="https://user-images.githubusercontent.com/50445943/199218897-80694ef0-3615-4687-8fc1-71da5fedbad6.png" width="200px"/> 
  </div>
  - MainActivity로 로그인 / 회원가입 / 비회원 접속 중 선택할 수 있다.
  - 로그인은 SQLite를 통해 내부 저장소에 저장되어 있는 정보인지 확인하여 작동한다.
  - 회원가입 클릭 시, SignupActivity로 이동한다.
  - 비회원 접속 클릭 시, HomeActivity로 이동한다.
  - 뒤로 키 클릭 시, 두 번 이상 클릭하여야 종료한다는 `Toast` 메시지가 나타난다.
    - 모든 화면에 공통적으로 들어가있으며, 처음 클릭했던 시간에 2000ms (2초) 이내로 다시 누르면 Activity가 종료된다.
- 회원가입 화면 (SignupActivity)
  <div>
    <img src="https://user-images.githubusercontent.com/50445943/199219076-2021ab28-7d46-4eae-a06e-f486c2158ee4.png" width="200px"/>
    <img src="https://user-images.githubusercontent.com/50445943/199219070-1c50b8a9-8297-46d1-a4b6-47442406da99.png" width="200px"/>
    <img src="https://user-images.githubusercontent.com/50445943/199219074-a9f98c76-e005-4a54-b413-00f72de8e1f5.png" width="200px"/>
    <img src="https://user-images.githubusercontent.com/50445943/199219063-c372146a-67ab-40d7-8364-1b57515ae96c.png" width="200px"/>
  </div>
  - ID / PW / Name / Phone / Address를 입력받아 조건에 맞을 시, SQLite에 저장한다.
  - 조건은 비밀번호는 가입되지 않았던 ID에 8자 이상의 영문자 또는 숫자이며, 모든 입력란이 채워져있고 회원 약관에 동의하였을 시 이다.
  - 조건에 만족하지 않는다면, 각각의 에러 메시지를 `Toast`를 통해 출력한다.
  - 조건에 만족한다면, 가입하기 버튼 클릭 시, MainActivity로 이동한다.
  - 뒤로가기 클릭 시, MainActivity로 이동한다.
- 상품 화면 (HomeActivity)
  <div>
    <img src="https://user-images.githubusercontent.com/50445943/199219468-52547e1e-37e1-406a-9dc5-93357ebf954b.png" width="200px"/>
    <img src="https://user-images.githubusercontent.com/50445943/199219458-ee036523-8f07-44ad-96a8-74072e349da0.png" width="200px"/>
    <img src="https://user-images.githubusercontent.com/50445943/199219480-0f65a3e6-d18f-409a-9973-8d7b034ca1f0.png" width="200px"/>
  </div>
  - 상품 (이미지 + 라벨) 세트를 총 6개 보여준다.
  - 오른쪽 상단 옵션메뉴 선택 시, Dialog를 통해 유저 정보를 확인할 수 있다.
  - 만약, 비회원 접속이었을 경우, 회원가입을 유도하는 Dialog와 함께 SignupActivity로 이동할 수 있다.
- 그 외 코드 (Utils)
  - Z2DBHelper.java
    - SQLite가 정상적으로 동작하기 위해 작성된 코드이다.
  - BackKeyHandler.java
    - 뒤로가기 키를 2번 눌러야 종료되도록 만든 코드이다.
- 모든 기능의 자료화면은 Repository의 `/screenshot`에 저장해두었다.
