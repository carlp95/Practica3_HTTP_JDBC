<#import "general.ftl" as gen>

<@gen.base>

    <#list articulos>
        <ul>
            <#items as articulo>
                <div class="card mx-auto" style="width: 750px">
                    <div class="card-body">
                        <h2>${ articulo.titulo }</h2>
                        ${ articulo.cuerpo }
                    </div>
                    <div class="card-footer">
                        span
                    </div>
                </div>
            </#items>
        </ul>
    </#list>

</@gen.base>

