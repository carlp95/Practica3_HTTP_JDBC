<#import "general.ftl" as gen>

<@gen.base usuario=usuarioValue>
<#if usuarioValue.administrador>
    <div class="container" align="center">
        <div class="col-lg-5">
            <div class="card border-primary">
                <div class="card-header"><h2>Formulario para Crear Usuario</h2></div>
                <div class="card-body">
                    <form action="createUser" method="post">
                        <div class="form-group row">
                            <div class="col-md-6" style="margin: 0 auto;">
                                <label for="username">Usuario</label>
                                <input class="form-control" name="username" type="text">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-md-6" style="margin: 0 auto;">
                                <label for="contrasena">Contraseña</label>
                                <input class="form-control"  name="contrasena" type="password" required="">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="form-check col-md-4" style="margin: 0 auto;">
                                <label class="form-check-label">
                                    <input class="form-check-input" name="rol" value="administrador" type="radio">
                                    Administrador
                                </label>
                                <label class="form-check-label">
                                    <input class="form-check-input" name="rol" value="autor" type="radio">
                                    Autor
                                </label>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="fa fa-save"> <strong>Guardar</strong></i></button>
                    </form>
                </div>
            </div>
        </div>
    </div>
<#else >
    <p class="text-danger">No tiene permiso para crear usuarios, contacte al administrador para crear un usuario.</p>
</#if>

</@gen.base>