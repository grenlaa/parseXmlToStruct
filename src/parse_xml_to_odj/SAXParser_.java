/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parse_xml_to_odj;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import parse_xml_to_odj.obj_;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author fursov.ga
 */
public class SAXParser_ extends DefaultHandler {

    private String value = "";
    private obj_ obj_last = null;
    private obj_ obj_main = null;
    private obj_ obj_base = new obj_("base", null);
    private HashMap<String, String> replace = new HashMap<String, String>();
    private HashMap<String, String> replace_prefix = new HashMap<String, String>();
    private boolean contain_prefix = false;
    private SAXParserFactory parserFactor;
    private SAXParser parser;

    public SAXParser_() throws Exception {
        this.parserFactor = SAXParserFactory.newInstance();
        this.parser = parserFactor.newSAXParser();
    }

    public void parse(String str) throws Exception {
        str = "\n" + str.replaceAll("><", ">\n<");
        parser.parse(new InputSource(new StringReader(str)), this);
    }

    public void parse(InputStream in_stream) throws Exception {
        parser.parse(in_stream, this);
    }

    public void set_replace(HashMap<String, String> replace) throws Exception {
        this.replace = replace;
    }

    public void set_replace_prefix(HashMap<String, String> eplace_prefix) throws Exception {
        this.replace_prefix = eplace_prefix;
    }

    public void contain_prefix(boolean contain_prefix) throws Exception {
        this.contain_prefix = contain_prefix;
    }

    public obj_ getObj_io() {
        obj_base.obj_child = new ArrayList<obj_>();
        obj_base.obj_child.add(obj_main);
        return obj_base;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (!contain_prefix) {
            if (qName.indexOf(":") > 0) {
                qName = qName.substring(qName.indexOf(":") + 1, qName.length());
            }
        }

        if (value.length() > 0) {
            for (String key : replace.keySet()) {
                value = value.replaceAll(key, replace.get(key));
            }
            obj_last.value = value;
            value = "";
        }

        if (obj_last != null) {
            obj_ obj_io_now = new obj_(obj_last, qName, attributes);
            obj_last.setObj_child(obj_io_now);
            obj_last = obj_io_now;
        } else {
            obj_last = new obj_(qName, attributes);
            obj_main = obj_last;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.indexOf(":") > 0) {
            obj_last.prefix = qName.substring(0, qName.indexOf(":"));
        }

        if (value.length() > 0) {
            for (String key : replace.keySet()) {
                value = value.replaceAll(key, replace.get(key));
            }
            obj_last.value = value;
            value = "";
        }

        if (obj_last.obj_child.isEmpty()) {
            obj_last.obj_child = null;
        }

        obj_ obj_now = obj_last.obj_parent;
        obj_last.obj_parent = null;
        obj_last = obj_now;

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = String.copyValueOf(ch, start, length).trim();

        if (text != null) {
            value += text;
        }

    }
}
