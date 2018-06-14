package main;

import BD.Dao;
import Estructura.Articulo;
import Estructura.Comentario;
import Estructura.Etiqueta;
import Estructura.Usuario;
import freemarker.template.Configuration;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.sql2o.Sql2oException;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("public");
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freemarkerEngine = new FreeMarkerEngine(configuration);

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Articulo> listaArticulos = Dao.getInstance().getArticulos();

            if (request.session().attribute("usuarioValue") == null){
                response.redirect("/login");
            } else {
                for (Articulo articulo : listaArticulos) {
                    try {
                        articulo.setListaEtiquetas(Dao.getInstance().getEtiquetas(articulo.getId()));
                    } catch (Sql2oException e) {
                        System.out.println("Ocurrio un error " + e.getMessage());
                    }
                }

                model.put("titulo", "Banana Blog");
                model.put("usuarioValue", request.session().attribute("usuarioValue"));
                model.put("articulos", listaArticulos);
            }
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
            Map<String, String> cookies = request.cookies();

            for(String key : cookies.keySet()){
                if(key.equalsIgnoreCase("Sesion")){
                    String encryptedText = request.cookie("Sesion");
                    BasicTextEncryptor encriptador = new BasicTextEncryptor();
                    encriptador.setPassword("secretPasswd");
                    String usern = encriptador.decrypt(encryptedText);
                    if( usuario.equalsIgnoreCase(usern)){
                        request.session().attribute("usuarioValue", Dao.getInstance().getUsuariosPorUsername(usern));
                        response.redirect("/");
                    }else {
                        break;
                    }

                }
            }

            if(request.queryParams("recordar") == null) {

                boolean verificacion = encryptor.checkPassword(contrasena, Dao.getInstance().getUsuariosPorUsername(request.queryParams("username")).getContrasena());
                if(verificacion){
                    request.session(true).attribute("usuarioValue",Dao.getInstance().getUsuariosPorUsername(request.queryParams("username")));
                }else {
                    halt(401,"Credenciales invalidas...");
                }
                response.redirect("/");

            }else if(request.queryParams("recordar").equals("on")){
                    List<Usuario> usuarios = Dao.getInstance().getUsuarios();

                        for(Usuario user : usuarios){
                            boolean verificacion = encryptor.checkPassword(contrasena, user.getContrasena());
                            if(request.queryParams("username").equalsIgnoreCase(user.getUsername()) && verificacion){
                                BasicTextEncryptor encriptadorTexto = new BasicTextEncryptor();
                                encriptadorTexto.setPassword("secretPasswd");
                                response.cookie("/","Sesion", encriptadorTexto.encrypt(user.getUsername()), 604800, false);
                                request.session(true).attribute("usuarioValue", user);
                            }else{
                                halt(401,"Credenciales invalidas...");
                            }
                        }
                        response.redirect("/");

                }
            return null;
        },freemarkerEngine);

        get("/logout",(request,response) ->{
            Session sesionActiva = request.session();
            sesionActiva.invalidate();
            response.redirect("/");

            return null;
        },freemarkerEngine);

        get("/show/:id",(request, response) ->{
            Map<String, Object> atributos = new HashMap<>();
            atributos.put("usuarioValue", request.session().attribute("usuarioValue"));
            List<Articulo> articulos = Dao.getInstance().getArticulos();
            for(Articulo art : articulos){
                if(Long.parseLong(request.params("id")) == art.getId()){
                    atributos.put("titulo",art.getTitulo());
                    atributos.put("articulo",art);

                        atributos.put("comentarios",Dao.getInstance().getComentarios(art.getId()));

                        atributos.put("etiquetas",Dao.getInstance().getEtiquetas(art.getId()));

                }
            }

            return new ModelAndView(atributos,"showArticle.ftl");
        },freemarkerEngine);

        post("show/createComment/:id",(request,response)->{
            Comentario comentario = new Comentario();

            if(request.session().attribute("usuarioValue") == null){
                response.redirect("/login");
            }else {

                comentario.setComentario(request.queryParams("comentario"));
                comentario.setAutor(((Usuario)request.session().attribute("usuarioValue")).getUsername());
                comentario.setArticulo(Long.parseLong(request.params("id")));
                Dao.getInstance().insertarComentario(comentario);
                response.redirect("/show/" + request.params("id"));

                }

            return null;
        }, freemarkerEngine);

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
            if(request.queryParams("rol").equals("administrador")){
                user.setAdministrador(true);
                user.setAutor(true);
            }else if(request.queryParams("rol").equals("autor")){
                user.setAutor(true);
                user.setAdministrador(false);
            }
            Dao.getInstance().insertarUsuario(user);
            response.redirect("/");
            return null;

        }, freemarkerEngine);

        get("/createArticle", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("titulo", "Crear Articulo");
            model.put("usuarioValue", request.session().attribute("usuarioValue"));
            if(request.session().attribute("usuarioValue") == null){
                response.redirect("/login");
            }
            return new ModelAndView(model, "createArticle.ftl");
        }, freemarkerEngine);

        post("/createArticle", (request, response) -> {

            Articulo articulo = new Articulo();

            articulo.setTitulo(
                  request.queryParams("titulo"));

            articulo.setCuerpo(
                  request.queryParams("cuerpo"));

            articulo.setAutor((( Usuario ) request.session().attribute("usuarioValue")).getUsername());

            articulo.setFecha(new Date());

            articulo.setListaEtiquetas(
                    request.queryParams("etiquetas").split(","));

            Dao.getInstance().insertarArticulo(articulo);

            response.redirect("/");
            return null;
        });

        get("/editArticle/:article_id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Articulo articulo =
                    Dao.getInstance()
                            .getArticulosPorId(Long.parseLong(request.params("article_id")));

            model.put("titulo", "Editar Artículo");
            model.put("usuarioValue", request.session().attribute("usuarioValue"));

            model.put("articulo", articulo);
            model.put("cadenaEtiquetas",
                    articulo.getListaEtiquetas().stream()
                            .map(Etiqueta::getEtiqueta)
                            .collect(Collectors.joining(", ")));

            return new ModelAndView(model, "editArticle.ftl");
        }, freemarkerEngine);

        post("/editArticle/:articulo_id", (request, response) -> {
            Articulo articulo =
                    Dao.getInstance()
                            .getArticulosPorId(Long.parseLong(request.params("articulo_id")));

            articulo.setTitulo(request.queryParams("titulo"));
            articulo.setCuerpo(request.queryParams("cuerpo"));
            articulo.setFecha(new Date());
            //TODO - Arreglar el asunto de las etiquetas

            Dao.getInstance().actualizarArticulo(articulo);

            response.redirect("/show/" + articulo.getId());
            return null;
        });
    }
}
