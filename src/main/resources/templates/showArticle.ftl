<#import "general.ftl" as gen>

<@gen.base usuario = usuarioValue>
<div class="container" >
    <div class="card mb-3">
        <div class="card-header">
            <h3>${articulo.titulo}</h3>
            <#if usuarioValue.username == articulo.autor.username>
                <a href="/editArticle/${ articulo.id }">
                    <button class="btn btn-primary"><i class="fa fa-edit"></i> Editar</button>
                </a>
            </#if>
        </div>
        <div class="card-body">
            <div class="">
                <p class="card-text" style="text-align: justify">${articulo.cuerpo}</p>
            </div>

            <div class="card-body"> Etiquetas:
                <#if etiquetas?size == 0>
                    <p class="text-danger">No hay etiquetas relacionada a este Artículo</p>
                <#else >
                    <#list etiquetas as e>
                        <span class="badge badge-primary">${ e.etiqueta }</span>
                    </#list>
                </#if>
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
                <form action="createComment/${articulo.id}" method="post">
                    <div class="form-group">
                        <textarea class="form-control" name="comentario" rows="6" placeholder="Escribe tu comentario aquí"></textarea>
                    </div>
                    <button type="submit" class="btn btn-success"><i class="fa fa-commenting"> <strong>Comentar</strong></i></button>
                </form>
            </div>
        </div>
    <#if comentarios?size == 0>
        <div class="card">
            <div class="-body">
                <h4 class="card-title">Comentarios sobre este Post</h4>
                <div class="row">
                    <div class="col-xl-5">
                        <p class="text-danger">No hay comentarios para este Artículo :(</p>
                    </div>
                </div>
            </div>
        </div>
    <#else >
        <div class="card">
            <div class="-body">
                    <div class="col-xl-5">
                        <h4 class="card-title">Comentarios sobre este Post</h4>
                        <#list comentarios?reverse as com>
                            <p><strong>${com.autor.username}:</strong> ${com.comentario}</p>
                        </#list>

                    </div>
            </div>
        </div>
    </#if>

</div>
</@gen.base>