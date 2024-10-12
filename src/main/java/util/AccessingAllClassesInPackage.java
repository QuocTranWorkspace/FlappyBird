package main.java.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class AccessingAllClassesInPackage {

    private AccessingAllClassesInPackage() {
        throw new IllegalStateException("Utility class");
    }

    public static Class<?>[] getFilteredClasses(String packageName, String filterClass)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(decodeUrl(resource.getFile())));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName, filterClass));
        }
        return classes.toArray(Class<?>[]::new);
    }

    private static List<Class<?>> findClasses(File directory, String packageName, String filterClass)
            throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    if (file.getName().compareToIgnoreCase(filterClass) != 0) {
                        classes.addAll(findClasses(file, packageName + "." + file.getName(), filterClass));
                    }
                } else if (file.getName().endsWith(".class") && file.getName().substring(0, file.getName().length() - 6)
                        .compareToIgnoreCase(filterClass) != 0) {
                    classes.add(Class
                            .forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classes;
    }

    private static String decodeUrl(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, "UTF-8");
    }
}
