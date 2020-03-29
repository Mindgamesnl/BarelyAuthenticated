#!/bin/sh

# Waterfall - rewrite below to extend platform support

if [[ "$OSTYPE" == "darwin"* ]]; then
  # brew install mbedtls zlib
  PREFIX="osx-"
  CXX_ARGS="/usr/local/lib/libmbedcrypto.a -lz -I$JAVA_HOME/include/ -I$JAVA_HOME/include/darwin/ -I/usr/local/include -L/usr/local/lib"
else
  # apt-get install libmbedtls-dev zlib1g-dev
  CXX_ARGS="-lcrypto -lz -I$JAVA_HOME/include/ -I$JAVA_HOME/include/linux/"
fi

CXX="g++ -shared -fPIC -O3 -Wall -Werror"

$CXX src/main/c/NativeCipherImpl.cpp -o src/main/resources/${PREFIX}native-cipher.so $CXX_ARGS
$CXX src/main/c/NativeCompressImpl.cpp -o src/main/resources/${PREFIX}native-compress.so $CXX_ARGS
