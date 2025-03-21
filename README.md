# 뉴스피드 프로젝트
> 이 프로젝트는 회원가입 및 로그인 후 포스트와 댓글을 작성하고 조회할 수 있는 뉴스피드 애플리케이션입니다. 사용자는 다른 사용자를 팔로우할 수 있으며, 포스트 및 댓글에 좋아요를 남길 수 있습니다. 또한, 자신의 포스트 및 댓글을 수정하거나 삭제할 수 있고, 프로필 조회 기능도 제공합니다.

<br>

## 주요 기능

### 1. 회원 관리

- 회원가입

- 로그인 / 로그아웃

- 프로필 조회

### 2. 포스트 (게시글) 관리

- 포스트 작성

- 포스트 수정

- 포스트 삭제

- 전체 포스트 조회 (페이지네이션 적용)

- 팔로우한 유저의 포스트 조회

### 3. 댓글 관리

- 댓글 작성

- 댓글 수정

- 댓글 삭제

- 특정 포스트의 댓글 조회

### 4. 좋아요 기능

- 포스트 좋아요 및 취소

- 댓글 좋아요 및 취소

### 5. 팔로우 기능

- 유저 팔로우 및 팔로우 취소

- 팔로우한 유저의 포스트 목록 조회



<br>

## 패키지 구조

```
com.example.springbasicnewspeed
├── common
│   ├── annotation
│   ├── dto
│   ├── entity
│   ├── exception
├── config
│   ├── argument
│   ├── filter
└── domain
    ├── auth
    ├── comment
    ├── follow
    ├── post
    ├── user
```

<br>

## API 동작 

### 회원가입

![image](https://github.com/user-attachments/assets/1ef89f89-8474-41bd-aa55-7e439027e6c1)

<br>

### 로그인

![image](https://github.com/user-attachments/assets/518d2970-df9d-4c27-9695-142dcc5ee87c)

<br>

### 전체 유저 조회(admin)
![image](https://github.com/user-attachments/assets/c05d2516-c070-4393-881a-9929d106b848)

<br>

### 회원 프로필 단건 조회(user)
![image](https://github.com/user-attachments/assets/35441f83-5b46-4eca-a637-53333d3d0845)

<br>

### 유저 프로필 수정(이메일, 닉네임)
![image](https://github.com/user-attachments/assets/38016111-081b-48d5-b3cf-245fddf8d498)

<br>

### 유저 프로필 수정(비밀번호)
![image](https://github.com/user-attachments/assets/6a3e16f8-8039-41e6-a0cc-0add4ec45235)

<br>

### 포스트 생성

![image](https://github.com/user-attachments/assets/5026bb73-91b9-4599-82b7-84dc23f1dc84)

<br>

### 포스트 조회

![image](https://github.com/user-attachments/assets/fb9a0f0b-fb8b-46f7-8c4a-24d601110a21)


<br>

### 포스트 수정

![image](https://github.com/user-attachments/assets/95daa742-089e-4575-93bd-70fdd9e3ce1f)


<br>


### 포스트 삭제


![image](https://github.com/user-attachments/assets/3d8de07e-4f88-45f9-94b1-1716b1f7a511)

<br>

### 댓글 생성

![image](https://github.com/user-attachments/assets/138029a5-d527-4576-b23f-8d81c5c9b1e2)


<br>

### 댓글 조회

![image](https://github.com/user-attachments/assets/f65f1238-983b-4692-9509-e494c4a16e73)

<br>

### 댓글 수정

![image](https://github.com/user-attachments/assets/af29563e-ad3a-476a-af10-dc30c24efe47)


<br>

### 댓글 삭제

![image](https://github.com/user-attachments/assets/26271a2d-7c4b-47ab-bd7b-940c68025ba1)


<br>

### 포스트 좋아요

![image](https://github.com/user-attachments/assets/5119842d-d503-4f93-8590-575044899e25)


<br>

### 댓글 좋아요

![image](https://github.com/user-attachments/assets/5bc65396-7c69-4112-83b5-e9846ded8f07)


<br>

### 팔로우

![image](https://github.com/user-attachments/assets/fe7cc507-e19f-433f-af62-e366729d435e)

<br>

### 팔로우 포스트 조회

![image](https://github.com/user-attachments/assets/c7fb844e-b0c4-45c9-afba-8e842b938d8f)


<br>

## 테스트 커버리지


![스크린샷(2150)](https://github.com/user-attachments/assets/ce3ff983-d409-40d2-9316-0ed85c6f551d)



