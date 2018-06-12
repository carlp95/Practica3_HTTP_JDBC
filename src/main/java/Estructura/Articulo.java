package Estructura;

import BD.Dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Articulo {

    private long id;
    private String titulo;
    private String cuerpo;
    private Usuario autor;
    private Date fecha;
    private List<Etiqueta> listaEtiquetas;
    private List<Comentario> listaComentarios;

    public Articulo() {}

    public Articulo(String titulo, String cuerpo, Usuario autor, Date fecha) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(String username) {
        this.autor = Dao.getInstance().getUsuariosPorUsername(username);
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Etiqueta> getListaEtiquetas() {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(List<Etiqueta> etiquetas) {
        this.listaEtiquetas = etiquetas;
    }

    public void setListaEtiquetas(String[] etiquetas) {
        this.listaEtiquetas = new ArrayList<>();
        for (String etiqueta : etiquetas) {
            this.listaEtiquetas.add(new Etiqueta(etiqueta, this.getId()));
        }
//            this.listaEtiquetas = Dao.getInstance().getEtiquetas(this.getId());
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(List<Comentario> listaComentarios) {
        //this.listaComentarios = Dao.getInstance().getComentarios(this);
    }
}
