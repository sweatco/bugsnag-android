FROM openjdk:8-jdk-stretch

RUN mkdir /android
WORKDIR /android

ENV ANDROID_HOME="/android/android-sdk-linux" \
  SDK_URL="https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip"

# Download Android SDK
RUN mkdir $ANDROID_HOME /root/.android \
  && cd $ANDROID_HOME \
  && curl -s -o sdk.zip $SDK_URL \
  && unzip sdk.zip > /dev/null \
  && rm sdk.zip > /dev/null \
  && yes | $ANDROID_HOME/tools/bin/sdkmanager --licenses > /dev/null

ENV ANDROID_NDK="/android/android-ndk-r16b" \
  ANDROID_NDK_HOME="/android/android-ndk-r16b" \
  NDK_URL="https://dl.google.com/android/repository/android-ndk-r16b-linux-x86_64.zip"

# Download Android NDK
RUN curl -s -o ndk.zip $NDK_URL \
  && unzip ndk.zip > /dev/null \
  && rm ndk.zip > /dev/null

RUN yes | $ANDROID_HOME/tools/bin/sdkmanager --install "build-tools;29.0.2" > /dev/null
RUN yes | $ANDROID_HOME/tools/bin/sdkmanager --install "platforms;android-29" > /dev/null
RUN yes | $ANDROID_HOME/tools/bin/sdkmanager --install "cmake;3.6.4111459" > /dev/null
RUN yes | $ANDROID_HOME/tools/bin/sdkmanager --install "platform-tools" > /dev/null

ENV PATH="${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/build-tools/29.0.2:${PATH}"
