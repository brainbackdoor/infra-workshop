### 🐳 로컬 DB 구성

- [도커 다운로드](https://www.docker.com/products/docker-desktop)
- `docker/db/mysql/init`에 dump 파일을 넣은 상태로 실행하면 자동 INSERT

#### 실행 방법

1. IntelliJ IDEA에서 `docker-compose.yml`로 이동 후, Run 버튼 클릭
2. 혹은 프로젝트 디렉터리에서 아래의 명령어를 터미널에 입력

```bash
cd docker
docker-compose up -d
```