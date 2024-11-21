/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AnnotationType.java to edit this template
 */
package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author fursov.ga
 */
@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.METHOD)
public @interface set_class {

    String name_param();

    /**
     * Тип данных.
     * <br>class:class
     * <br>object:object
     * <br>primitive(default):
     * <br>string
     * <br>char
     * <br>int
     * <br>bigint
     * <br>float
     * <br>double
     * <br>date
     * <br>bytes
     * <br>boolean
     *
     *
     */
    

}
