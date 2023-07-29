#!/bin/bash
DEPLOY_PATH="./build/libs/"

# 현재 실행 중인 애플리케이션 pid 확인
echo "$(date '+%Y-%m-%d %H:%M:%S') > 현재 실행 중인 애플리케이션 pid 확인" | tee -a ./scripts/deploy.log
CURRENT_PID=$(lsof -t -i:8080)

if [ -z $CURRENT_PID ]
then
  echo "$(date '+%Y-%m-%d %H:%M:%S') > 포트 8080에서 애플리케이션이 실행 중이지 않으므로 종료되지 않습니다." | tee -a ./scripts/deploy.log
else
  echo "$(date '+%Y-%m-%d %H:%M:%S') > 현재 실행 중인 애플리케이션을 종료합니다. kill -15 $CURRENT_PID" | tee -a ./scripts/deploy.log
  kill -15 $CURRENT_PID
  sleep 5
fi

# 기존 파일 확인 및 삭제
BUILD_WAR=$(ls ./build/libs/*.war)
WAR_NAME=$(basename $BUILD_WAR)
if [ -f "$DEPLOY_PATH$WAR_NAME" ]; then
  echo "$(date '+%Y-%m-%d %H:%M:%S') > 기존 파일 삭제: $DEPLOY_PATH$WAR_NAME" | tee -a ./scripts/deploy.log
  rm -f "$DEPLOY_PATH$WAR_NAME"
fi

# 재빌드
echo "$(date '+%Y-%m-%d %H:%M:%S') clean build " | tee -a ./scripts/deploy.log
./gradlew clean build

# 새로운 WAR 이름 가져오기
BUILD_WAR=$(ls ./build/libs/*.war)
WAR_NAME=$(basename $BUILD_WAR)

# 로그 디렉토리 확인 및 생성
LOG_DIR="./scripts"
if [ ! -d "$LOG_DIR" ]; then
  mkdir -p $LOG_DIR
  echo "$(date '+%Y-%m-%d %H:%M:%S') 로그 디렉토리 생성 중..." | tee -a ./scripts/deploy.log
fi

echo "$(date '+%Y-%m-%d %H:%M:%S') > 빌드 파일 이름: $WAR_NAME" | tee -a ./scripts/deploy.log

echo "$(date '+%Y-%m-%d %H:%M:%S') > DEPLOY_WAR 배포 주소 $DEPLOY_PATH 배포 파일 $DEPLOY_PATH$WAR_NAME" | tee -a ./scripts/deploy.log
nohup java -jar $DEPLOY_PATH$WAR_NAME

