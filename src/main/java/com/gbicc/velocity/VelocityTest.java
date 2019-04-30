package com.gbicc.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.util.Properties;

public class VelocityTest {

    public static void main(String[] args) {
        VelocityEngine ve = new VelocityEngine();
        VelocityContext context = new VelocityContext();
        Properties properties = new Properties();
        String basePath = "D:/tmp";
        properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, basePath);
        try {
            ve.init(properties);


            Template template = ve.getTemplate("vm.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
