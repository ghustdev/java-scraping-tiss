import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class MavenWrapperDownloader {

    public static void main(String[] args) throws Exception {
        Path projectDir = args.length > 0 ? Paths.get(args[0]) : Paths.get("").toAbsolutePath();
        Path propertiesPath = projectDir.resolve(".mvn/wrapper/maven-wrapper.properties");
        Path jarPath = projectDir.resolve(".mvn/wrapper/maven-wrapper.jar");

        if (Files.exists(jarPath)) {
            System.out.println("maven-wrapper.jar already exists.");
            return;
        }

        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(propertiesPath)) {
            properties.load(inputStream);
        }

        String wrapperUrl = properties.getProperty("wrapperUrl");
        if (wrapperUrl == null || wrapperUrl.isBlank()) {
            throw new IllegalStateException("Property wrapperUrl not found in " + propertiesPath);
        }

        Files.createDirectories(jarPath.getParent());
        downloadFile(wrapperUrl, jarPath);
        System.out.println("Downloaded Maven wrapper to " + jarPath);
    }

    private static void downloadFile(String url, Path destination) throws IOException {
        try (InputStream inputStream = new URL(url).openStream();
             OutputStream outputStream = Files.newOutputStream(destination)) {
            inputStream.transferTo(outputStream);
        }
    }
}
