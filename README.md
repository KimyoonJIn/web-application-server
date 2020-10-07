# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
http 요청 정보에서 요청 URL을 추출해서 Index.html을 body로 넘겨주는 과정이 조금 비효율적이라고 느낌.
controller를 사용해서 url에 대한 정보 추출 과정 없이 바로 Index.html을 넘겨줄 수 있을텐데, 간단히 할 수 있는 일을 돌려서 하는 느낌.

byte로 바디를 넘겨주는데 web에서는 글자들이 호로록 나와서 신기 
멀티쓰레드 구현 방법도 새로운 지식 

힌트는 힌트일 뿐, 요구사항에 맞춰 구현하자  


### 요구사항 2 - get 방식으로 회원가입
MVC 방식이면 진짜 간단하게 구현할 수 있는데 ㅜㅠㅠ 
진짜 백지부터 시작하는 기분이다. 
그래도 http header를 파싱하다보니 이렇게 자세하게 오래 본 적은 처음인 것 같다.  


### 요구사항 3 - post 방식으로 회원가입
위와 동일..

### 요구사항 4 - redirect 방식으로 이동
Redirect는 302번!!
새로운 걸 아는 건 재밌기도 한데 왜 이제야 알았나 싶기도 하다.
redirect 방식을 이해하면서 브라우저 단을 보기 시작!

### 요구사항 5 - cookie
쿠키 구현 처음 해보는데 재밌다.
근데 강의처럼 브라우저에서 cookie 정보가 넘어오지 않는 것을 확인했는데 기능은 동작한다.   
브라우저 버전이 다른 건지 잘 모르겠지만.. 이게 넘어오지 않아서 삽질 오조오억번..  

### 요구사항 6 - stylesheet 적용
이제야 눈이 편안해졌다..
I LOVE CSS

### heroku 서버에 배포 후
* 