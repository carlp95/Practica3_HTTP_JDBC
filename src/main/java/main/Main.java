package main;

import BD.Dao;
import Estructura.Usuario;
import freemarker.template.Configuration;
import org.jasypt.util.password.BasicPasswordEncryptor;
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
            BasicPasswordEncryptor encriptor = new BasicPasswordEncryptor();
            String passEncripted = encriptor.encryptPassword(request.queryParams("contrasena"));
            user.setUsername(request.queryParams("username"));
            user.setContrasena(passEncripted);
            if(request.queryParams("administrador").equals("administrador") && request.queryParams("autor") == null){
                user.setAdministrador(true);
                user.setAutor(true);
                conexionBD.insertarUsuario(user);
                response.redirect("/");
                return null;
            }else if(request.queryParams("autor").equals("autor") && request.queryParams("administrador") == null){
                user.setAutor(true);
                user.setAdministrador(false);
                conexionBD.insertarUsuario(user);
                response.redirect("/");
                return null;
            }

            //response.redirect("/");
            return null;

        }, freemarkerEngine);
    }
}
