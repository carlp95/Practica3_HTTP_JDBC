<#import "general.ftl" as gen>

<@gen.base>

    <#list articulos>
        <ul>
            <#items as articulo>
                <div class="card mx-auto mb-3" style="width: 750px">
                    <div class="card-body">
                        <h2>${ articulo.titulo }</h2>
                        <p>${ articulo.cuerpo }</p>
                    </div>
                    <div class="card-footer">
                        <#list articulo.listaEtiquetas as e>
                            <span class="badge badge-primary">${ e.etiqueta }</span>
                        <#else>
                            <span class="badge badge-secondary">No one TT</span>
                        </#list>
                    </div>
                </div>
            </#items>
        </ul>
    </#list>

</@gen.base>

