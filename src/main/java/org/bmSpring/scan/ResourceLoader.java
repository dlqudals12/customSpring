package org.bmSpring.scan;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
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

    public static HashMap<Class<?>, Class<? extends Annotation>> classesContainsAnnotation(Class<? extends Annotation> annotation) {
        if (allClasses.isEmpty()) throw new NullPointerException();

        Set<Class<? extends Annotation>> annotations = new HashSet<>();

        annotations.add(annotation);

        absClassesContainsAnnotation(annotation, annotations);

        HashMap<Class<?>, Class<? extends Annotation>> result = new HashMap<>();

        for (Class<?> allClass : allClasses) {

            if (!allClass.isAnnotation()) {
                for (Class<? extends Annotation> ano : annotations) {
                    if (allClass.isAnnotationPresent(ano)) {
                        result.put(allClass, ano);
                    }
                }
            }
        }

        return result;
    }

    @SuppressWarnings(value = "unchecked")
    private static void absClassesContainsAnnotation(Class<? extends Annotation> annotation, Set<Class<? extends Annotation>> annotations) {

        Set<Class<? extends Annotation>> ano = allClasses.stream().filter(c -> c.isAnnotationPresent(annotation) && c.isAnnotation()).map(c -> (Class<? extends Annotation>) c).collect(Collectors.toSet());

        if (!ano.isEmpty()) {
            annotations.addAll(ano);

            for (Class<? extends Annotation> aClass : ano) {
                absClassesContainsAnnotation(aClass, annotations);
            }
        }
    }


}
