#! /bin/bash

set -x

check_for_positive_result() {
  if [ $? != 0 ]; then
    >&2  echo "\"$1\" failed"
    report_build_failure "$2"
    exit 1
  else
    echo "\"$1\" succeeded"
  fi
}

COMMIT_SHA=$(git rev-parse HEAD)
DOCKER_IMAGE_TAG="swc-bugsnag-android-build:$COMMIT_SHA"

docker build . -f SWC.Dockerfile -t "$DOCKER_IMAGE_TAG"
check_for_positive_result "Build docker image"

SOURCES_DIR="/android/src"
docker run -v "$PWD":"$SOURCES_DIR" -w "$SOURCES_DIR" "$DOCKER_IMAGE_TAG" ./gradlew assembleRelease
check_for_positive_result "Build libraries"
