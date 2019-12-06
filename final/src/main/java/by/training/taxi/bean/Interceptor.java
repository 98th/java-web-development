package by.training.taxi.bean;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Interceptor {

    Class<? extends Annotation> clazz();
}