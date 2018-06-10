package main;

import BD.Dao;
import Estructura.Usuario;
import freemarker.template.Configuration;
import spark.ModelAndView;
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

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Banana Blog");
            return new ModelAndView(model, "index.ftl");
        }, freemarkerEngine);

        get("/createUser", (request,response) ->{
            Map<String, Object> atributos = new HashMap<>();
            atributos.put("titulo","Crear Usuario");
            return new ModelAndView(atributos,"createUser.ftl");
        },freemarkerEngine);

        post("/createUser", (request, response) ->{
            Usuario user = new Usuario();
            user.setUsername(request.queryParams("username"));
            user.setContrasena(request.queryParams("contrasena"));
            if(request.queryParams("administrador").equals("true")){
                user.setAdministrador(true);
                user.setAutor(true);
            }else if(request.queryParams("autor").equals("true")){
                user.setAutor(true);
                user.setAdministrador(false);
            }
            conexionBD.insertarUsuario(user);

            response.redirect("/");
            return null;
        }, freemarkerEngine);
    }
}
