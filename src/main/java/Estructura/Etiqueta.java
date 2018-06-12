package Estructura;

public class Etiqueta {

    private long id;
    private String etiqueta;
    private long articulo;

    public Etiqueta() {
    }

    public Etiqueta(String etiqueta, long articulo) {
        this.etiqueta = etiqueta;
        this.articulo = articulo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public long getArticuloId() {
        return articulo;
    }

    public void setArticuloId(long articulo) {
        this.articulo = articulo;
    }
}
