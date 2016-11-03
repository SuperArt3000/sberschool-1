package ru.sbt.Lesson7_proxy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class InterfaceStreamer {


    static Class<?>[] getAllClassesByPredicate(Package basePackage, File basePath) throws IOException, ClassNotFoundException {

         List<Class<?>> clslist = new ArrayList<>(Files.find(basePath.toPath(), Integer.MAX_VALUE,
                (path, attr) -> path.toString().endsWith(".class"))
                .map(asStringOfCLass(basePackage, basePath))
                .map(toClass()).filter(s -> s.isInterface())
                 .collect(Collectors.toList()));
        return clslist.toArray(new Class<?>[clslist.size()]);
    }

    static private Function<? super String, ? extends Class<?>> toClass() {
        return s -> {
            try {
                return Class.forName(s);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        };
    }

    static private Function<? super Path, ? extends String> asStringOfCLass(Package basePackage, File basePath) {
        return path -> basePackage.getName() + "." + path.toString()
                .substring(basePath.toString().length())
                .replace(".class", "")
                .replace(File.separator, ".").substring(1);
    }

}
