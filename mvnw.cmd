@ECHO OFF
SETLOCAL

SET "BASE_DIR=%~dp0"
IF "%BASE_DIR:~-1%"=="\" SET "BASE_DIR=%BASE_DIR:~0,-1%"
SET "WRAPPER_DIR=%BASE_DIR%\.mvn\wrapper"
SET "WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar"
SET "DOWNLOADER_JAVA=%WRAPPER_DIR%\MavenWrapperDownloader.java"

IF NOT "%JAVA_HOME%"=="" (
  SET "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
  SET "JAVAC_CMD=%JAVA_HOME%\bin\javac.exe"
) ELSE (
  SET "JAVA_CMD=java"
  SET "JAVAC_CMD=javac"
)

IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO maven-wrapper.jar not found, downloading...
  "%JAVAC_CMD%" "%DOWNLOADER_JAVA%"
  "%JAVA_CMD%" -cp "%WRAPPER_DIR%" MavenWrapperDownloader "%BASE_DIR%"
)

"%JAVA_CMD%" -Dmaven.multiModuleProjectDirectory="%BASE_DIR%" -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
ENDLOCAL
