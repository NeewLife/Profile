# 결재시스템 예제 구현

## 사용 환경


Java 8 , Spring maven 5.2.1.RELEASE

DB - Oracle 11g ojdbc6 사용

라이브러리 - Lombok, Bootstrap

<br>

### 개발기간

---

2023.08.23 ~ 2023.09.12 (21일)

<br>

### DB 테이블

---

- 사원정보
    - ID : 유저의 고유 식별번호
    - USER_ID : 유저의 아이디
    - USER_PW : 유저의 비밀번호
    - USER_NAME : 유저의 이름
    - USER_RANK : 유저의 직급(EMP - 사원 , VI - 대리, EXA - 부장, DH - 과장)
        
        ![Untitled](https://github.com/NeewLife/Profile/assets/107593357/fcbfe29a-0023-439b-a951-6372d3e1fddc)

        
<br>

- 대리권한정보
    - ID : 대리권한 부여받은자의 식별번호
    - ST_DATE : 대리권한 부여한 시간
    - END_DATE : 대리권한이 끝나는 시간 (ST_DATE + 1일)
    - PROXY_ID : 대리권한 부여자의 식별번호
        
        ![Untitled 1](https://github.com/NeewLife/Profile/assets/107593357/9f8e47da-be13-487b-91f8-cda5625083a0)
        

<br>

- 게시판 정보
    - POST_ID : 게시글의 식별번호
    - TITLE : 게시글의 제목
    - WRITER_PK_NUM : 작성자의 식별번호
    - WRITE_DATE : 게시글의 작성 시간
    - CONTENT : 게시글의 내용
    - CONFIRM_DATE : 게시글의 결재 날짜
    - CONFIRM_PERSON : 결재자의 식별번호
    - CONFIRM_STATUS : 게시글의 결재상태
    - PROXY_CONFIRM_PERSON : 대리권한 부여자의 식별번호
        
        ![Untitled 2](https://github.com/NeewLife/Profile/assets/107593357/e3ed2509-3f96-4e89-b7ff-cf28ab790bd4)

        
<br>

- 게시판 히스토리
    - POST_ID : 게시글의 식별번호
    - SEQ : 히스토리의 번호
    - CONFIRM_DATE : 해당 히스토리 생성시의 결재 날짜
    - CONFIRM_STATUS : 해당 히스토리 생성시의 결재 상태
    - CONFIRM_PERSON : 해당 히스토리 생성시의 결재자의 식별번호
    - PROXY_CONFIRM_PERSON : 해당 히스토리 생성시의 대리권한 부여자의 식별번호
        
        ![Untitled 3](https://github.com/NeewLife/Profile/assets/107593357/d743bdd8-42ae-40a3-af77-c6374bac3e8c)
        

### DB 관계도

---

![Untitled 4](https://github.com/NeewLife/Profile/assets/107593357/78950aa4-a922-4cc0-9dac-e99ffa1a5657)

<br>

## 페이지 설명

### 로그인 화면

---

![Untitled 5](https://github.com/NeewLife/Profile/assets/107593357/80917423-c1a6-4384-babe-c91f05145fa0)

아이디와 비밀번호를 조회해서 로그인을 진행하는 화면

없는 회원일 경우, 비밀번호가 틀렸을 경우 메시지를 띄우는 방식이다.

<br>

### 게시글 페이지

---

![Untitled 6](https://github.com/NeewLife/Profile/assets/107593357/896da266-e639-4cc8-b106-8f12b52d871e)

- 사원, 대리 직급일 경우 자신이 결재하거나 임시저장한 글만 조회가 가능하고, 다른 사람들이 작성한 글은 조회되지 않는다.
- 과장 직급일 경우 추가로 자신이 결재한 글은 조회할 수 있지만, 이미 다른 사람이 결재한 글은 조회할 수 없다.
- 부장 직급은 타인의 임시저장글을 제외한 모든 게시글을 조회할 수 있다.
    
    ![Untitled 7](https://github.com/NeewLife/Profile/assets/107593357/c3547a51-681f-4b4d-a711-b19d1ba7eb5d)

    
<br>

### 글 작성 페이지( and 수정 페이지)

---

![Untitled 8](https://github.com/NeewLife/Profile/assets/107593357/b977ebd5-5938-4ff6-bd2d-9a07714eb028)

번호, 작성자 부분은 자동으로 입력되고, 제목, 내용을 입력 후 임시저장 및 결재요청을 진행할 수 있다.

임시저장을 누르면 게시글이 임시저장상태로, 결재요청을 누르면 결재대기 상태로 작성된다.

임시저장을 제외한 모든 게시글 작업은 히스토리가 생성된다.

<br>

### 결재 및 반려

---

![Untitled 9](https://github.com/NeewLife/Profile/assets/107593357/c35f3942-a728-47a2-9344-e55e52180713)

직급이 부장이나 과장인 경우 ‘결재승인 및 반려’ 를 진행할 수 있다. 결재승인시 부장일 경우는 결재중 상태로, 과장은 결재완료 상태로 변경된다.

반려할 경우는 반려 상태로 변경된다.

![Untitled 10](https://github.com/NeewLife/Profile/assets/107593357/0032badc-29f2-4de5-a56c-12b66ccd5fe3)

![Untitled 11](https://github.com/NeewLife/Profile/assets/107593357/597b3011-82c5-4340-9881-06797d4baed2)

![Untitled 12](https://github.com/NeewLife/Profile/assets/107593357/6a891a19-f1ae-4929-86e2-a2c54f4e1968)

![Untitled 13](https://github.com/NeewLife/Profile/assets/107593357/e1f44606-2f7d-498c-a567-62097f660304)

<br>

### 대리권한 부여 기능

---

과장, 부장 직급은 대리결재 버튼을 눌러 대리권한을 부하직원에게 부여할 수 있다.

권한부여자의 직급은 자동 입력되고, 부하직원을 선택하면 해당 부하직원의 직급이 자동으로 입력된다.

대리권한 기간은 1일로 설정했다.

![Untitled 14](https://github.com/NeewLife/Profile/assets/107593357/cfd5a81b-81a4-42b9-b32e-91d78a1c3957)

![Untitled 15](https://github.com/NeewLife/Profile/assets/107593357/ec93d33c-6eaf-4d07-8be2-7d1611aa4a45)

![Untitled 16](https://github.com/NeewLife/Profile/assets/107593357/a0f06b91-6b01-4123-9b3b-534a426594e9)

권한을 부여받은 직원은 권한유효기간이 표시되고, 대리권한직급의 기능들을 이용할 수 있다.

결재를 진행할 경우, 부하직원(대리자) 의 형식으로 표기된다.

![Untitled 17](https://github.com/NeewLife/Profile/assets/107593357/eb4ed2f8-b974-4c5c-9d84-2c469068bb87)

<br>

### 검색기능

---

검색방식, 검색어를 변경할 시 비동기방식으로 목록을 가져오도록 구현했다.

![Untitled 18](https://github.com/NeewLife/Profile/assets/107593357/76577052-5e4d-4045-b8fc-7c04eba43518)

![Untitled 19](https://github.com/NeewLife/Profile/assets/107593357/b56e994d-b7c3-4f89-8d72-df8d3e34252a)

<br>

### 기능명세서

---

[기능명세서 정리.xlsx](https://github.com/NeewLife/Practice/raw/main/%EA%B8%B0%EB%8A%A5%EB%AA%85%EC%84%B8%EC%84%9C%20%EC%A0%95%EB%A6%AC.xlsx)
