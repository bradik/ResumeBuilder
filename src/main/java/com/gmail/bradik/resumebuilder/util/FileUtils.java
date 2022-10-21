package com.gmail.bradik.resumebuilder.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Brad on 03.06.2018.
 */
public class FileUtils {

    private static final boolean isJarFile;

    static {

        final File jarFile = new File(FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        isJarFile = jarFile.isFile();

    }

    public static URL getResource(String name) {

        URL url = FileUtils.class.getResource("/" + name);

        return url;
    }

    public static InputStream getResourceAsStream(String name) {

        InputStream in = FileUtils.class.getResourceAsStream("/" + name);

        return in;
    }

    public static void copyFile(URL in, Path dest) throws IOException, URISyntaxException {

        Files.createDirectories(dest.getParent());

        Files.copy(Paths.get(in.toURI()), dest, REPLACE_EXISTING);
    }

    public static void copyFile(InputStream in, Path dest) throws IOException, URISyntaxException {

        Files.createDirectories(dest.getParent());

        Files.copy(in, dest, REPLACE_EXISTING);
    }

    public static void copyResoursToFile(String in, String dest) throws IOException, URISyntaxException {

        if (isJarFile) {  // Run with JAR file

            copyFile(getResourceAsStream(in), Paths.get(dest));

        } else { // Run with IDE
            copyFile(getResource(in), Paths.get(dest));
        }

    }

    public static void copyFile(Path in, Path dest) throws IOException {

        Files.createDirectories(dest.getParent());

        Files.copy(in, dest, REPLACE_EXISTING);
    }

    public static void copyFile(String in, String dest) throws IOException {

        copyFile(Paths.get(in), Paths.get(dest));


    }

}
