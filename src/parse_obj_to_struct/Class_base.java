/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parse_obj_to_struct;

import parse_xml_to_odj.obj_;
import annotation.parameter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Locale;
import annotation.set_class;
import annotation.value;

/**
 *
 * @author fursov.ga
 */
public abstract class Class_base {

    public Class_base() throws Exception {
    }

    public Class_base(obj_ obj_XO) throws Exception {

        for (Method meth : this.getClass().getMethods()) {
            invoke_method(meth, obj_XO);
        }

        HashMap<String, Field> Filed_val = new HashMap<String, Field>();
        for (Field field : this.getClass().getDeclaredFields()) {

            //Смотрим, есть ли у параметра нужная нам Аннотация @Command
            if (field.isAnnotationPresent(value.class)) {
                set_value(field, obj_XO);
            }
            if (field.isAnnotationPresent(parameter.class)) {
                set_parametr(field, obj_XO);
            }

        }
    }

    public void set_value(Field field, obj_ obj_XO) throws Exception {
        value val_ant = field.getAnnotation(value.class);
        Class<?> type = field.getType();
        if (val_ant.name().equals(obj_XO.name)) {
            if (obj_XO.value != null && !obj_XO.value.equalsIgnoreCase("NULL")) {
                field.setAccessible(true);
                if (type == Integer.class) {
                    field.set(this, Integer.valueOf(obj_XO.value.toString()));
                }
                if (type == int.class) {
                    field.set(this, Integer.parseInt(obj_XO.value.toString()));
                }
                if (type == String.class) {
                    field.set(this, obj_XO.value.toString());
                }
                if (type == Float.class) {
                    field.set(this, Float.valueOf(obj_XO.value.toString()));
                }
                if (type == float.class) {
                    field.set(this, Float.parseFloat(obj_XO.value.toString()));
                }
                if (type == Double.class) {
                    field.set(this, Double.valueOf(obj_XO.value.toString()));
                }
                if (type == Double.class) {
                    field.set(this, Double.parseDouble(obj_XO.value.toString()));
                }
                if (type == Boolean.class) {
                    field.set(this, Boolean.valueOf(obj_XO.value.toString()));
                }
                if (type == boolean.class) {
                    field.set(this, Boolean.parseBoolean(obj_XO.value.toString()));
                }

            }
        }
    }

    public void set_parametr(Field field, obj_ obj_XO) throws Exception {

        parameter parameter_ant = field.getAnnotation(parameter.class);
        Class<?> type = field.getType();
        if (obj_XO.attributes != null) {
            if (obj_XO.attributes.get(parameter_ant.name()) != null) {
                field.setAccessible(true);
                if (type == Integer.class) {
                    field.set(this, Integer.valueOf(obj_XO.attributes.get(parameter_ant.name()).toString()));
                }
                if (type == int.class) {
                    field.set(this, Integer.parseInt(obj_XO.attributes.get(parameter_ant.name()).toString()));
                }
                if (type == String.class) {
                    field.set(this, obj_XO.attributes.get(parameter_ant.name()));
                }
                if (type == Float.class) {
                    field.set(this, Float.valueOf(obj_XO.attributes.get(parameter_ant.name()).toString()));
                }
                if (type == float.class) {
                    field.set(this, Float.parseFloat(obj_XO.attributes.get(parameter_ant.name()).toString()));
                }
                if (type == Double.class) {
                    field.set(this, Double.valueOf(obj_XO.attributes.get(parameter_ant.name()).toString()));
                }
                if (type == Double.class) {
                    field.set(this, Double.parseDouble(obj_XO.attributes.get(parameter_ant.name()).toString()));
                }
                if (type == Boolean.class) {
                    field.set(this, Boolean.valueOf(obj_XO.attributes.get(parameter_ant.name()).toString()));
                }
                if (type == boolean.class) {
                    field.set(this, Boolean.parseBoolean(obj_XO.attributes.get(parameter_ant.name()).toString()));
                }
            }

        }
    }

    public void invoke_method(Method meth, obj_ obj_XO) throws Exception {
        if (meth.isAnnotationPresent(set_class.class)) {

            set_class method_ant = meth.getAnnotation(set_class.class);
            if (meth.getParameterTypes().length == 1) {
                Class<?> type = meth.getParameterTypes()[0];
                meth.setAccessible(true);
                Constructor nativeConstructor = type.getConstructor(obj_XO.getClass());
                nativeConstructor.setAccessible(true);

                for (obj_ obj_XO_child : obj_XO.obj_child) {

                    if (method_ant.name_param().equals(obj_XO_child.name)) {
                        meth.invoke(this, new Object[]{nativeConstructor.newInstance(obj_XO_child)});
                    }
                }

            }
        }
    }

}
