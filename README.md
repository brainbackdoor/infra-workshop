<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/brainbackdoor/infra-workshop/main/images/main_logo.png"/>
</p>

<h2 align="middle">인프라공방</h2>
<p align="middle">컨퍼런스 신청 플랫폼</p>
<p align="middle">

<p align="center">
  <img alt="npm" src="https://img.shields.io/badge/npm-%3E%3D%205.5.0-blue">
  <img alt="node" src="https://img.shields.io/badge/node-%3E%3D%209.3.0-blue">
  <a href="https://edu.nextstep.camp/c/VI4PhjPA" alt="infra workshop">
    <img alt="Website" src="https://img.shields.io/website?url=https://edu.nextstep.camp/c/VI4PhjPA">
  </a>
  <img alt="GitHub" src="https://img.shields.io/github/license/brainbackdoor/infra-workshop">
</p>

<br>

## 💡 Introduction

인프라공방 강의 학습을 위한 예제 코드입니다. 아래와 같은 모듈들로 구성되어 있습니다.<br>
- 공통적으로 사용하는 코드를 관리하는 core
- 컨퍼런스 관리, 신청 및 이벤트 등을 담당하는 conference
- 회원 정보를 담당하는 member
- 설문 정보를 담당하는 analysis
- BFF 패턴이 적용되어 있는 front

<br>

## 🚀 Getting Started

### 1. 로컬 DB 구성

#### 준비

- [도커 다운로드](https://www.docker.com/products/docker-desktop)
- `[module]/docker/db/mysql/init`에 dump 파일을 넣은 상태로 실행하면 자동 INSERT

#### 실행 방법

1. IntelliJ IDEA에서 `docker-compose.yml`로 이동 후, Run 버튼 클릭
2. 혹은 프로젝트 디렉터리에서 아래의 명령어를 터미널에 입력

    ```bash
    # 가령 member 모듈의 경우,
    cd member/docker
    docker-compose up -d
    ```

<br>

### 2.️ 로컬 서버 시작시 config location 설정

- IntelliJ -> Run -> Edit Configurations...
- com.brainbackdoor.xxApplicaion.kt 추가 또는 선택 후 VM Options에 다음 설정 추가

    ```
    -Dspring.config.location=classpath:/config/
    ```

- Test 수행을 위하여 Template -> JUnit 선택 후 VM options에도 추가



<br>

<br>

## 🙋🏻‍♂️ Reference

- 컨벤션 문서
- 이벤트스토밍
- 테스트 작성 기준


<br>

## 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/brainbackdoor/infra-workshop/issues) 에 등록해주세요.

<br>

## 📝 License

This project is [MIT](https://github.com/brainbackdoor/infra-workshop/blob/main/LICENSE) licensed.