<#import "general.ftl" as gen>

<@gen.base usuario=usuarioValue>
<div class="container" >
    <div class="card mb-3" align="center">
        <h3 class="card-header">${articulo.titulo}</h3>
        <div class="card-body">
        <div class="card-body">
            <p class="card-text" style="text-align: justify">${articulo.cuerpo}</p>
        </div>

        <div class="card-body"> Etiquetas:
            <#--<#list articulo.listaEtiquetas as etiquetas>
                <a href="#" class="card-link">${etiquetas.etiqueta}</a>
            </#list>-->
        </div>
        <div class="card-footer text-muted">
            Autor: ${articulo.autor.username} - ${articulo.fecha}
        </div>
        </div>
    </div>
        <div class="card">
            <div class="card-body">
                <h4 class="card-title">Comentarios</h4>
                <h6 class="card-subtitle mb-2 text-muted">Comenta este Post</h6>
                <form action="/createComment" method="post">
                    <div class="form-group">
                        <textarea class="form-control" name="comentario" rows="6" placeholder="Escribe tu comentario aquí"></textarea>
                    </div>
                    <button type="submit" class="btn btn-success"><i class="fa fa-commenting"> <strong>Comentar</strong></i></button>
                </form>
            </div>
        </div>
    <div class="card">
        <div class="-body">
            <h4 class="card-title">Comentarios sobre este Post</h4>
            <div class="row">
                <div class="col-xl-5">
                    <p><strong>${comentarios.autor.username}:</strong> ${comentarios.comentario}</p>
                </div>
                <div class="col-xl-5">
                    <form action="/deleteComment" method="post">
                        <button type="submit" class="btn btn-danger"><i class="fa fa-trash"> <strong>Eliminar</strong></i></button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</@gen.base>