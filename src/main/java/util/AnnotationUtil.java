package util;

import Annotation.Column;
import Annotation.Entity;
import Annotation.PK;
import model.Trip;

import java.lang.reflect.Field;
import java.util.Arrays;

public class AnnotationUtil {
     public static String getTableName(Class<?> tClass){
         return tClass.getDeclaredAnnotation(Entity.class).name();
     }
     public static String getPrimarykey(Class<?> tClass , String fieldName) throws NoSuchFieldException {
         return tClass.getDeclaredField(fieldName).getDeclaredAnnotation(PK.class).value();
     }
     public static String getFields(Class<?> tClass , String fieldName) throws NoSuchFieldException {
         return tClass.getDeclaredField(fieldName).getDeclaredAnnotation(Column.class).value();
     }
        public static void main(String[] args) throws NoSuchFieldException {
        AnnotationUtil annotationUtil = new AnnotationUtil();
        Field[] fields = Trip.class.getDeclaredFields();
            Arrays.stream(fields).forEach(field -> {
                if (!field.getName().equals("id")){
                    try {
                        System.out.println(annotationUtil.getFields(Trip.class, field.getName()));
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            });

    }
}
