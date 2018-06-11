package BD;

import Estructura.*;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Dao {

    private Sql2o sql2o;

    private static Dao instance = null;

    public Dao(){
        this.sql2o = new Sql2o("jdbc:h2:tcp://localhost/~/practica3","sa","");
        crearTablas();
        cargarUsuarioAdm();
    }

    public static Dao getInstance() {
        if ( instance == null) {
            instance = new Dao();
        }

        return instance;
    }

    private void crearTablas(){
        String queryUsuario = "CREATE TABLE IF NOT EXISTS Usuario(" +
                "username VARCHAR(100) PRIMARY KEY NOT NULL,"+
                "contrasena VARCHAR(255), " +
                "administrador BOOLEAN, " +
                "autor BOOLEAN)";

        String queryArticulo = "CREATE TABLE IF NOT EXISTS Articulo(" +
                "id BIGINT PRIMARY KEY auto_increment, " +
                "titulo VARCHAR(200),"+
                "cuerpo VARCHAR, " +
                "autor VARCHAR(100), " +
                "fecha DATE, " +
                "foreign key (autor) references Usuario(username));";


        String queryComentario = "CREATE TABLE IF NOT EXISTS Comentario(" +
                "id BIGINT PRIMARY KEY auto_increment, " +
                "comentario VARCHAR, " +
                "autor VARCHAR(100), " +
                "articulo BIGINT," +
                "FOREIGN KEY (autor) REFERENCES Usuario(username), " +
                "FOREIGN KEY (articulo) REFERENCES Articulo(id));";

        String queryEtiqueta = "CREATE TABLE IF NOT EXISTS Etiqueta(" +
                "id BIGINT PRIMARY KEY auto_increment, " +
                "etiqueta VARCHAR(80)," +
                "articulo BIGINT," +
                "FOREIGN KEY (articulo) REFERENCES Articulo(id));";


        //Ejecucion de los Querys:

        //Usuario
        try(Connection conexion = sql2o.open()){
            conexion.createQuery(queryUsuario).executeUpdate();
        }
        //Articulo
        try(Connection conexion = sql2o.open()){
            conexion.createQuery(queryArticulo).executeUpdate();
        }
        //Comentario
        try(Connection conexion = sql2o.open()){
            conexion.createQuery(queryComentario).executeUpdate();
        }
        //Etiqueta
        try(Connection conexion = sql2o.open()){
            conexion.createQuery(queryEtiqueta).executeUpdate();
        }


    }

    private  void cargarUsuarioAdm(){
        if(getUsuarios().isEmpty()){
            String sql = "insert into Usuario(username,contrasena,administrador,autor) values(:username,:contrasena,:administrador,:autor)";
            BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
            String passEncrypted = encryptor.encryptPassword("admin123");
            try(Connection conexion = sql2o.open()) {
                conexion.createQuery(sql)
                        .addParameter("username","admin")
                        .addParameter("contrasena",passEncrypted)
                        .addParameter("administrador","true")
                        .addParameter("autor","true")
                        .executeUpdate();
            }

        }
    }

    public List<Usuario> getUsuarios(){
        String sql = "select * from Usuario";
        try(Connection conexion = sql2o.open()){
            return conexion.createQuery(sql).executeAndFetch(Usuario.class);
        }
    }

    public Usuario getUsuariosPorUsername(String username){
        String sql = "select * from Usuario where username = :user";
        try(Connection conexion = sql2o.open()){
            return conexion.createQuery(sql)
                    .addParameter("user", username)
                    .executeAndFetch(Usuario.class).get(0);
        }
    }

    public List<Comentario> getComentarios(Articulo articulo){
        String sql = "select * from Comentario WHERE id = :articulo_id";
        try(Connection conexion = sql2o.open()){
            return conexion.createQuery(sql)
                    .addParameter("articulo_id", articulo.getId())
                    .executeAndFetch(Comentario.class);
        }
    }

    public List<Articulo> getArticulos(){
        String sql = "select * from Articulo";
        try(Connection conexion = sql2o.open()){
            return conexion.createQuery(sql).executeAndFetch(Articulo.class);
        }
    }

    public List<Etiqueta> getEtiquetas(long articulo_id) throws Sql2oException{
        String sql = "select * from Etiqueta where articulo = :articulo_id";
        try(Connection conexion = sql2o.open()) {
            return conexion.createQuery(sql)
                    .addParameter("articulo_id", articulo_id)
                    .executeAndFetch(Etiqueta.class);
        }
    }


    public void actualizarArticulo(Articulo articulo){
        String sql = "update Articulo set titulo = :titulo, set cuerpo = :cuerpo, set fecha = :fecha, set etiquetas = :etiquetas, set comentarios = :comentarios where articulo_id = :articulo_id";

        try(Connection conexion = sql2o.open()){
            conexion.createQuery(sql)
                    .addParameter("titulo",articulo.getTitulo())
                    .addParameter("cuerpo",articulo.getCuerpo())
                    .addParameter("fecha",articulo.getFecha())
                    .addParameter("etiquetas",articulo.getListaEtiquetas())
                    .addParameter("comentarios",articulo.getListaComentarios())
                    .addParameter("articulo_id",articulo.getId())
                    .executeUpdate();
        }
    }

    public void actualizarUsuario(Usuario usuario){

        String sql = "update Usuario set contrasena = :contrasena, set administrador = :administrador, set autor = :autor where username = :username";

        try(Connection conexion = sql2o.open()){
            conexion.createQuery(sql)
                    .addParameter("contrasena",usuario.getContrasena())
                    .addParameter("administrador",usuario.isAdministrador())
                    .addParameter("autor",usuario.isAutor())
                    .addParameter("username",usuario.getUsername())
                    .executeUpdate();
        }
    }

    public void insertarArticulo(Articulo articulo){

        String sql = "insert into Articulo(titulo,cuerpo,fecha,autor,etiquetas,comentarios) values(:titulo,:cuerpo,:fecha,:autor,:etiquetas,:comentarios)";
        try(Connection conexion = sql2o.open()){
            conexion.createQuery(sql)
                    .addParameter("titulo",articulo.getTitulo())
                    .addParameter("cuerpo",articulo.getCuerpo())
                    .addParameter("fecha",articulo.getFecha())
                    .addParameter("autor",articulo.getAutor().getUsername())
                    .addParameter("etiquetas",articulo.getListaEtiquetas())
                    .addParameter("comentarios",articulo.getListaComentarios())
                    .executeUpdate();
        }

    }
    public void insertarUsuario(Usuario usuario){

        String sql = "insert into Usuario(username,contrasena,administrador,autor) values(:username,:contrasena,:administrador,:autor)";
        try(Connection conexion = sql2o.open()){
            conexion.createQuery(sql)
                    .addParameter("username",usuario.getUsername())
                    .addParameter("contrasena",usuario.getContrasena())
                    .addParameter("administrador",usuario.isAdministrador())
                    .addParameter("autor",usuario.isAutor())
                    .executeUpdate();
        }
    }
    public void insertarComentario(Comentario comentario){

        String sql = "insert into Comentario(comentario,autor,articulo) values(:comentario,:autor,:articulo)";
        try(Connection conexion = sql2o.open()){
            conexion.createQuery(sql)
                    .addParameter("comentario",comentario.getComentario())
                    .addParameter("autor",comentario.getAutor().getUsername())
                    .addParameter("articulo",comentario.getArticulo().getId())
                    .executeUpdate();
        }
    }

    public void insertarEtiqueta(Etiqueta etiqueta){

        String sql = "insert into Etiqueta(etiqueta) values(:etiqueta)";
        try(Connection conexion = sql2o.open()){
            conexion.createQuery(sql)
                    .addParameter("etiqueta",etiqueta.getEtiqueta())
                    .executeUpdate();
        }
    }



}
