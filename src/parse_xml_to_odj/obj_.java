/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parse_xml_to_odj;

import java.util.ArrayList;
import java.util.HashMap;
import org.xml.sax.Attributes;

/**
 *
 * @author fursov.ga
 */
public class obj_ {

    public String name;
    public String value;

    public HashMap<String, String> attributes;
    public ArrayList<obj_> obj_child;
    public obj_ obj_parent;
    public HashMap<String, Boolean> many = new HashMap<String, Boolean>();
    HashMap<String, obj_> obj_child_map_temp = new HashMap<String, obj_>();
    public String prefix;

    public obj_(obj_ obj_io_tm) {
        this.obj_child = obj_io_tm.obj_child;
        this.name = obj_io_tm.name;
        this.value = obj_io_tm.value;
        this.attributes = obj_io_tm.attributes;

    }

    public obj_(String name, Attributes attributes) {
        this.name = name;
        this.obj_child = new ArrayList<obj_>();
        if (attributes != null) {
            if (attributes.getLength() > 0) {
                this.attributes = new HashMap<String, String>();
                for (int i = 0; i < attributes.getLength(); i++) {
                    this.attributes.put(attributes.getLocalName(i), attributes.getValue(i));
                }
            }
        }
    }

    public obj_(obj_ obj_io_p, String name, Attributes attributes) {
        this.obj_parent = obj_io_p;
        this.obj_child = new ArrayList<obj_>();
        this.name = name;
        if (attributes.getLength() > 0) {
            this.attributes = new HashMap<String, String>();
            for (int i = 0; i < attributes.getLength(); i++) {
                this.attributes.put(attributes.getLocalName(i), attributes.getValue(i));
            }
        }

    }

    public void setObj_child(obj_ obj_child) {
        this.obj_child.add(obj_child);
    }

    //Создаем шаблон (избавляем класс от повтояющихся значений)
    public void obj_ret_template() {

        if (this.obj_child == null) {
            this.obj_child = new ArrayList<obj_>();
        }

        for (obj_ obj_TM : this.obj_child) {
            many.put(obj_TM.name, false);
            if (obj_child_map_temp.get(obj_TM.name) == null) {
                obj_child_map_temp.put(obj_TM.name, obj_TM);
            } else {
                many.put(obj_TM.name, true);
            }
        }

        this.obj_child = new ArrayList<obj_>();

        for (String key : obj_child_map_temp.keySet()) {
            this.obj_child.add(obj_child_map_temp.get(key));
        }
    }

    //Создаем шаблон
    public void obj_ret_template(obj_ obj_add) {

        this.name = obj_add.name;

        if (this.attributes == null) {
            this.attributes = new HashMap<String, String>();
        }

        if (this.obj_child == null) {
            this.obj_child = new ArrayList<obj_>();
        }

//        HashMap<String, obj_> obj_child_map_temp = new HashMap<String, obj_>();
        if (obj_add.attributes != null) {
            for (String key : obj_add.attributes.keySet()) {
                if (attributes.get(key) == null) {
                    if (attributes.size() == 0) {
                        this.attributes = new HashMap<String, String>();
                    }
                    attributes.put(key, obj_add.attributes.get(key));
                }
            }
        }

        if (obj_add.obj_child != null) {
            for (obj_ obj_TM : obj_add.obj_child) {
                if (obj_child_map_temp.get(obj_TM.name) == null) {
                    obj_child_map_temp.put(obj_TM.name, obj_TM);
                }

            }
        }

        for (String key : obj_add.many.keySet()) {
            if (obj_add.many.get(key)) {
                many.put(key, true);
            } else {
                if (many.get(key) != null) {
                    if (many.get(key) != true) {
                        many.put(key, false);
                    }
                } else {
                    many.put(key, false);
                }
            }

        }

        this.obj_child = new ArrayList<obj_>();
        for (String key : obj_child_map_temp.keySet()) {
            this.obj_child.add(obj_child_map_temp.get(key));
        }

    }

}
