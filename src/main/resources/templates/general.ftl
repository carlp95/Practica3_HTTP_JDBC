<#macro base>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${titulo}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link  href="https://bootswatch.com/4/darkly/bootstrap.css" rel="stylesheet"/>
</head>
<body>
    <#--contenido-->
    <#nested >

    <#--footer-->
    <#include "footer.ftl">
</body>
</html>

</#macro>