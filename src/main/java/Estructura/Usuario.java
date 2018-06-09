package Estructura;

public class Usuario {

    private String username;
    private String contrasena;
    private boolean administrador;
    private boolean autor;


    public Usuario() {
    }

    public Usuario(String username, String contrasena, boolean administrador, boolean autor) {
        this.username = username;
        this.contrasena = contrasena;
        this.administrador = administrador;
        this.autor = autor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isAutor() {
        return autor;
    }

    public void setAutor(boolean autor) {
        this.autor = autor;
    }
}
