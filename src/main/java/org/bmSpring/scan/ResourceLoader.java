package org.bmSpring.scan;

import lombok.Getter;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class ResourceLoader {
    
    @Getter
    private static Set<Class<?>> allClasses = new CopyOnWriteArraySet<>();

    public static void register(Class<?> main) {
        Reflections reflections = new Reflections(main.getPackageName(), new SubTypesScanner(false));

        allClasses = reflections.getSubTypesOf(Object.class);
    }


    public static Set<Class<?>> classesContainsAnnotation(Class<? extends Annotation> annotation) {
        if (allClasses.isEmpty()) throw new NullPointerException();

        Reflections reflections = new Reflections(allClasses);

        return reflections.getTypesAnnotatedWith(annotation).stream().filter(c -> !c.isAnnotation()).collect(Collectors.toSet());
    }


}
