package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes what can be exposed to the user to import data.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exportable {

    String name();

    String description() default "";

    String nameInDB();

}
