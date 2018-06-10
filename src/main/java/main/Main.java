package main;

import BD.Dao;
import freemarker.template.Configuration;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freemarkerEngine = new FreeMarkerEngine(configuration);

        Dao conexionBD = new Dao();

        /*get("/createUser", (request,response) ->{
            Map<String, Object> atributos = new HashMap<>();

            return null;
        },freemarkerEngine);*/
    }
}
