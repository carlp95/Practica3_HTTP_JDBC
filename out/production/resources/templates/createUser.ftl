<#import "general.ftl" as gen>

<@gen.base>
<div class="container" align="center">
    <div class="col-lg-5">
        <div class="card border-primary">
            <div class="card-header"><h2>Formulario para Crear Usuario</h2></div>
            <div class="card-body">
                <form action="createUser" method="post">
                    <div class="form-group row">
                        <div class="col-md-6" style="margin: 0 auto;">
                            <label for="username">Usuario</label>
                            <input class="form-control" name="username" placeholder="usuario123" type="text">
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
                                <input class="form-check-input" name="administrador" type="checkbox">
                                Administrador
                            </label>
                            <label class="form-check-label">
                                <input class="form-check-input" name="autor" type="checkbox">
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
</@gen.base>