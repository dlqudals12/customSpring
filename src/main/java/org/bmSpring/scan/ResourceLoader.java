package org.bmSpring.scan;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class ResourceLoader {
    private static Set<Class<?>> allClasses = new CopyOnWriteArraySet<>();

    public static Set<Class<?>> getAllClasses() {
        return allClasses;
    }

    public static void register(Class<?> main) {
        Reflections reflections = new Reflections(main.getPackageName(), new SubTypesScanner(false));

        allClasses = reflections.getSubTypesOf(Object.class);
    }

    public static Set<Class<?>> classesByAnnotation(Class<? extends Annotation> annotation) {
        if (allClasses.isEmpty()) throw new RuntimeException();

        return allClasses.stream().filter(c -> c.isAnnotationPresent(annotation) && !c.isAnnotation()).collect(Collectors.toSet());
    }

    public static Set<Class<?>> classesContainsAnnotation(Class<? extends Annotation> annotation) {
        if (allClasses.isEmpty()) throw new NullPointerException();

        Reflections reflections = new Reflections(allClasses);

        return reflections.getTypesAnnotatedWith(annotation);
    }


}
