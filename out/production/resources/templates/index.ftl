<#import "general.ftl" as gen>

<@gen.base>
    <h1>Here we have the list</h1>
    <#list articulos>
        <ul>
            <#items as articulo>
                <li>${ articulo.titulo }</li>
            </#items>
        </ul>
    </#list>
</@gen.base>

