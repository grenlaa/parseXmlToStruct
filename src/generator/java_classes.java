/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generator;

import java.util.ArrayList;
import java.util.HashMap;
import parse_xml_to_odj.obj_;

/**
 *
 * @author fursov.ga
 */
public class java_classes {

//    private static obj_ obj_base = new obj_("base", null);
    public HashMap<String, obj_> dictionary_classes = new HashMap<String, obj_>();

    public void add_class(obj_ obj_external) {

        for (obj_ obj_I : get_array_obj_child(obj_external)) {

            obj_ yung = new obj_(obj_I);

            yung.obj_ret_template();

            if (dictionary_classes.get(yung.name) != null) {

                dictionary_classes.get(yung.name).obj_ret_template(yung);
            } else {
                dictionary_classes.put(yung.name, yung);
            }

        }
//        System.out.print(dictionary_classes);

    }

    private ArrayList<obj_> get_array_obj_child(obj_ obj_) {
        ArrayList<obj_> obj_crt_ = new ArrayList<obj_>();
        obj_crt_.add(obj_);
        if (obj_.obj_child != null) {
            for (obj_ obj_ch1 : obj_.obj_child) {
                for (obj_ obj_ch2 : get_array_obj_child(obj_ch1)) {
                    obj_crt_.add(obj_ch2);
                }
            }
        }
        return obj_crt_;
    }

    public HashMap<String, String> get_gen_java_class() {

        HashMap<String, String> files_text = new HashMap<String, String>();

        for (String key : dictionary_classes.keySet()) {
            files_text.put(key, get_classes_to_string(dictionary_classes.get(key)));
        }

        return files_text;
    }

    private String get_classes_to_string(obj_ obj_) {
        String value_str = "";
        if (obj_.value != null) {
            value_str = "\n"
                    + "    @value(name = \"" + obj_.name + "\")\n"
                    + "    public String pr_" + obj_.name + ";\n";
        }
        String psrams_str = "";
        if (obj_.attributes != null) {
            for (String key : obj_.attributes.keySet()) {
                psrams_str += "\n"
                        + "    @parameter(name = \"" + key + "\")\n"
                        + "    private String " + key + ";\n";

            }
        }
        String clazz_ch_str = "";
        if (obj_.obj_child != null) {

            for (obj_ obj_child : obj_.obj_child) {

                if (obj_.many.get(obj_child.name)) {

                    clazz_ch_str += "\n"
                            + "     public ArrayList<" + obj_child.name + "> " + obj_child.name + "s;\n\n"
                            + "     @set_class(name_param = \"" + obj_child.name + "\")\n"
                            + "     public void setParents(" + obj_child.name + " " + obj_child.name + ") {\n"
                            + "        if (" + obj_child.name + "s == null) {\n"
                            + "            " + obj_child.name + "s = new ArrayList<" + obj_child.name + ">();\n"
                            + "        }\n"
                            + "        this." + obj_child.name + "s.add(" + obj_child.name + ");\n"
                            + "     }\n";
                } else {

                    clazz_ch_str += "\n"
                            + "     public " + obj_child.name + " " + obj_child.name + ";\n\n"
                            + "     @set_class(name_param = \"" + obj_child.name + "\")\n"
                            + "     public void setParents(" + obj_child.name + " " + obj_child.name + ") {\n"
                            + "        this." + obj_child.name + " = " + obj_child.name + ";\n"
                            + "     }\n";
                }
            }

        }

        String clazz_str = "import java.util.ArrayList;\n"
                + "\n"
                + "import parse_xml_to_odj.obj_;\n"
                + "import parse_obj_to_struct.Class_base;\n"
                + "\n"
                + "import annotation.parameter;\n"
                + "import annotation.set_class;\n"
                + "import annotation.value;\n\n"
                + "public class " + obj_.name + " extends Class_base {\n\n"
                + "     public  " + obj_.name + " (obj_ obj_) throws Exception {\n"
                + "        super(obj_);\n"
                + "     }\n" + value_str + "\n" + psrams_str + "\n" + clazz_ch_str + "\n"
                + "}\n";

        return clazz_str;
    }

}
