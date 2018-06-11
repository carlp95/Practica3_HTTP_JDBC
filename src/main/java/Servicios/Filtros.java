package Servicios;

import Estructura.Usuario;

import static spark.Spark.before;
import static spark.Spark.halt;

public class Filtros {

    public Filtros() {
    }

    public void aplicarFiltros(){
        before("/confirmLogin/*",(request, response) -> {
            Usuario usuario = request.session(true).attribute("usuario");
            if(usuario==null){
                //parada del request, enviando un codigo.
                halt(401, "No se ha podido verificar sus credenciales");
            }
        });
    }
}
