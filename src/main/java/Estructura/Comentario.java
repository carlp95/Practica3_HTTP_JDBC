package Estructura;

import BD.Dao;

public class Comentario {

    private long id;
    private String comentario;
    private Usuario autor;
    private Articulo articulo;

    public Comentario() {
    }

    public Comentario(long id, String comentario, Usuario autor, Articulo articulo) {
        this.id = id;
        this.comentario = comentario;
        this.autor = autor;
        this.articulo = articulo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = Dao.getInstance().getUsuariosPorUsername(autor);
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Long articulo) {
        this.articulo = Dao.getInstance().getArticulosPorId(articulo);
    }
}
