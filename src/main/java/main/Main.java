package main;

import BD.Dao;
import Estructura.Articulo;
import Estructura.Comentario;
import Estructura.Usuario;
import freemarker.template.Configuration;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
            model.put("articulos", conexionBD.getArticulos());
            model.put("usuarioValue", request.session().attribute("usuarioValue"));
            return new ModelAndView(model, "index.ftl");
        }, freemarkerEngine);

        get("/login",(request, response) ->{
            Map<String, Object> atributos = new HashMap<>();
            atributos.put("titulo", "Login");
            atributos.put("usuarioValue",request.queryParams("username"));
            return new ModelAndView(atributos,"login.ftl");
        },freemarkerEngine);

        post("/confirmLogin", (request,response) ->{
            String usuario = request.queryParams("username");
            String contrasena = request.queryParams("contrasena");
            BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

            if(request.queryParams("recordar") == null) {

                //Session session = request.session(true);
                List<Usuario> usuarios = conexionBD.getUsuarios();

                for(Usuario user : usuarios){
                    boolean verificacion = encryptor.checkPassword(contrasena, user.getContrasena());
                    if(request.queryParams("username").equalsIgnoreCase(user.getUsername()) && verificacion){
                        //session.attribute("usuario", user);
                        request.session().attribute("usuarioValue",user);
                    }else{
                        halt(401,"Credenciales invalidas...");
                    }
                }
                response.redirect("/");
            }else if(request.queryParams("recordar").equals("on")){
                    List<Usuario> usuarios = conexionBD.getUsuarios();

                    if(request.cookie("Sesion") == null){
                        for(Usuario user : usuarios){
                            boolean verificacion = encryptor.checkPassword(contrasena, user.getContrasena());
                            if(request.queryParams("username").equalsIgnoreCase(user.getUsername()) && verificacion){
                                BasicTextEncryptor encriptadorTexto = new BasicTextEncryptor();
                                response.cookie("/","Sesion", encriptadorTexto.encrypt("Username: "+
                                            request.queryParams("username") + " Contrasena: " + contrasena  ), 604800, true);
                                request.session().attribute("usuarioValue", user);
                            }else{
                                halt(401,"Credenciales invalidas...");
                            }
                        }
                        response.redirect("/");
                    }else {
                        request.session().attribute("usuario",conexionBD.getUsuariosPorUsername(usuario));
                    }

                }
            return null;
        },freemarkerEngine);

        get("/show/:id",(request, response) ->{
            Map<String, Object> atributos = new HashMap<>();
            List<Articulo> articulos = conexionBD.getArticulos();
            atributos.put("usuarioValue", request.session().attribute("usuarioValue"));
            for(Articulo art : articulos){
                if(Long.parseLong(request.params("id")) == art.getId()){
                    atributos.put("titulo",art.getTitulo());
                    atributos.put("articulo",art);
                    for(Comentario com : conexionBD.getComentarios(art.getId())){
                        atributos.put("comentarios",com);
                    }
                }
            }

            return new ModelAndView(atributos,"showArticle.ftl");
        },freemarkerEngine);

        post("/createComment",(request,response)->{
            Comentario comentario = new Comentario();

            if(request.session().attribute("usuarioValue") == null){
                response.redirect("/login");
            }else {

                for(Articulo art : conexionBD.getArticulos()){
                    if(Long.parseLong(request.queryParams("id")) == art.getId()){
                        comentario.setComentario(request.queryParams("comentario"));
                        comentario.setAutor(request.session().attribute("usuarioValue"));
                        comentario.setArticulo(art.getId());
                        conexionBD.insertarComentario(comentario);
                        response.redirect("/show/:" + art.getId());
                    }
                }
            }

            return null;
        },freemarkerEngine);

        get("/createUser", (request,response) ->{
            Map<String, Object> atributos = new HashMap<>();
            atributos.put("titulo","Crear Usuario");
            atributos.put("usuarioValue", request.session().attribute("usuarioValue"));
            return new ModelAndView(atributos,"createUser.ftl");
        },freemarkerEngine);

        post("/createUser", (request, response) ->{
            Usuario user = new Usuario();
            BasicPasswordEncryptor encriptor = new BasicPasswordEncryptor();
            String passEncripted = encriptor.encryptPassword(request.queryParams("contrasena"));
            user.setUsername(request.queryParams("username"));
            user.setContrasena(passEncripted);
            if(request.queryParams("administrador").equals("on")){
                user.setAdministrador(true);
                user.setAutor(true);
            }else if(request.queryParams("autor").equals("on")){
                user.setAutor(true);
                user.setAdministrador(false);
            }
            conexionBD.insertarUsuario(user);
            response.redirect("/");
            return null;

        }, freemarkerEngine);
    }
}
