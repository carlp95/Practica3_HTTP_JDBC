package BD;

import Estructura.Articulo;
import Estructura.Comentario;
import Estructura.Etiqueta;
import Estructura.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

public class Dao {

    private Sql2o sql2o;

    public Dao(){
        this.sql2o = new Sql2o("jdbc:h2:~/practica3","sa","");
        crearTablas();
        cargarUsuarioAdm();
    }

    private void crearTablas(){
        String queryArticulo = "CREATE TABLE IF NOT EXISTS Articulo(articulo_id BIGINT PRIMARY KEY auto_increment, titulo VARCHAR(200),"+
                                " cuerpo VARCHAR, fecha DATE, autor VARCHAR(100), etiquetas_id ARRAY, comentarios_id ARRAY,"+
                                "foreign key (autor) references Usuario(username));";

        String queryUsuario = "CREATE TABLE IF NOT EXISTS Usuario(username VARCHAR(100) PRIMARY KEY NOT NULL,"+
                " contrasena VARCHAR(255), administrador BOOLEAN, autor BOOLEAN)";

        String queryComentario = "CREATE TABLE IF NOT EXISTS Comentario(comentario_id BIGINT PRIMARY KEY auto_increment, comentario VARCHAR, autor VARCHAR(100), " +
                                "articulo_id BIGINT," +
                                "FOREIGN KEY (autor) REFERENCES Usuario(username), FOREIGN KEY (articulo_id) REFERENCES Articulo(articulo_id));";

        String queryEtiqueta = "CREATE TABLE IF NOT EXISTS Etiqueta(etiqueta_id BIGINT PRIMARY KEY auto_increment, etiqueta VARCHAR(80))";

        try(Connection conexion = sql2o.open()){
            conexion.createQuery(queryUsuario).executeUpdate();
        }

        try(Connection conexion = sql2o.open()){
            conexion.createQuery(queryEtiqueta).executeUpdate();
        }


        try(Connection conexion = sql2o.open()){
            conexion.createQuery(queryArticulo).executeUpdate();
        }

        try(Connection conexion = sql2o.open()){
            conexion.createQuery(queryComentario).executeUpdate();
        }

    }

    private  void cargarUsuarioAdm(){
        if(getUsuarios().isEmpty()){
            String sql = "insert into Usuario(username,contrasena,administrador,autor) values(:username,:contrasena,:administrador,:autor)";
            try(Connection conexion = sql2o.open()){
                conexion.createQuery(sql)
                        .addParameter("username","admin")
                        .addParameter("contrasena","admin123")
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

    public List<Comentario> getComentarios(){
        String sql = "select * from Comentario";
        try(Connection conexion = sql2o.open()){
            return conexion.createQuery(sql).executeAndFetch(Comentario.class);
        }
    }
    public List<Articulo> getArticulos(){
        String sql = "select * from Articulo";
        try(Connection conexion = sql2o.open()){
            return conexion.createQuery(sql).executeAndFetch(Articulo.class);
        }
    }
    public List<Etiqueta> getEtiqueta(){
        String sql = "select * from Etiqueta";
        try(Connection conexion = sql2o.open()){
            return conexion.createQuery(sql).executeAndFetch(Etiqueta.class);
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
