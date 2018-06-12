<#import "general.ftl" as gen>

<@gen.base>

    <div class="card mx-auto" style="width: 750px">
        <div class="card-header">
            <h2>Nuevo Artículo</h2>
        </div>
        <div class="card-body">
            <form action="/createArticle" method="post">
                <div class="form-group">
                    <label for="titulo">Título:</label>
                    <input id="titulo" class="form-control" type="text" name="titulo" placeholder="Título">
                </div>

                <div class="form-group">
                    <label for="cuerpo">Cuerpo:</label>
                    <textarea id="cuerpo" class="form-control" rows="10" name="cuerpo"
                              placeholder="Escribe el cuerpo de tu articulo aqui"></textarea>
                </div>

                <div class="form-group">
                    <label class="sr-only" for="etiqueta">Etiquetas</label>
                    <input id="etiqueta" class="form-control" type="text" name="etiquetas" placeholder="Etiquetas">
                </div>

                <a href="/" class="btn btn-danger">Cancel</a>
                <button style="float:right" type="submit" class="btn btn-primary">Aceptar</button>
            </form>
        </div>
    </div>

</@gen.base>