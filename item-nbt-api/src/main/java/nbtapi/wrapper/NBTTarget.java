package nbtapi.wrapper;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface NBTTarget {
    public String value();

    public Type type() default Type.AUTOMATIC;
    
    public enum Type {
        AUTOMATIC, GET, SET, HAS
    }
}
