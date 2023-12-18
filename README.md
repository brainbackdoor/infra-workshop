<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/brainbackdoor/infra-workshop/main/images/main_logo.png"/>
</p>

<h2 align="middle">인프라공방</h2>
<p align="middle">컨퍼런스 신청 플랫폼</p>
<p align="middle">

<p align="center">
  <img src="https://img.shields.io/badge/version-1.0.0-blue?style=flat-square" alt="template version"/>
  <img src="https://img.shields.io/badge/language-kotlin-red.svg?style=flat-square"/>
  <img src="https://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat-square"/>
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

- 미션과 관련한 사항은 각 미션의 가이드 문서를 참고하시길 바랍니다.

### 1. 로컬 DB 구성

#### 준비

- [도커 다운로드](https://www.docker.com/products/docker-desktop)
- `[module]/docker/db/mysql/init`에 dump 파일을 넣은 상태로 실행하면 자동 INSERT
- `sql/[module].sql` 파일을 활용하여 샘플 데이터를 추가

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

- [컨벤션 문서](https://www.brainbackdoor.com/infra-workshop/convention)
- [테스트 작성 기준](https://www.brainbackdoor.com/infra-workshop/test-criteria)


<br>

## 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/brainbackdoor/infra-workshop/issues) 에 등록해주세요.

<br>

## 📝 License

This project is [MIT](https://github.com/brainbackdoor/infra-workshop/blob/main/LICENSE) licensed.