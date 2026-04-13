#!/bin/sh

set -eu

BASE_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER_DIR="$BASE_DIR/.mvn/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/maven-wrapper.jar"
DOWNLOADER_JAVA="$WRAPPER_DIR/MavenWrapperDownloader.java"

if [ -n "${JAVA_HOME:-}" ]; then
  JAVA_CMD="$JAVA_HOME/bin/java"
  JAVAC_CMD="$JAVA_HOME/bin/javac"
else
  JAVA_CMD="java"
  JAVAC_CMD="javac"
fi

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "maven-wrapper.jar not found, downloading..."
  "$JAVAC_CMD" "$DOWNLOADER_JAVA"
  "$JAVA_CMD" -cp "$WRAPPER_DIR" MavenWrapperDownloader "$BASE_DIR"
fi

exec "$JAVA_CMD" \
  -Dmaven.multiModuleProjectDirectory="$BASE_DIR" \
  -classpath "$WRAPPER_JAR" \
  org.apache.maven.wrapper.MavenWrapperMain "$@"
