//<!-- START MENU TEMAS*******************************************************************************************************************-->	
function modalNuevoTema(){
    $('#modalNuevoTema').modal('show')
}

function actualizarTemasSelects(){
    //LIMPIAR SELECT DE USUARIOS
    $('#selectTemaOrigen').empty();
    $('#selectTemaDestino').empty();
    $.ajax({
            type : 'GET',
            url : _baseURL+'/theme',
            async: false,
            success : function(temas) {
                //POR CADA USUARIO AGREGARLO AL SELECT
                temas.forEach(tema => {
                    $('#selectTemaOrigen').append(`<option value=${tema.id}>${tema.nombre}</option>`);
                    $('#selectTemaDestino').append(`<option value=${tema.id}>${tema.nombre}</option>`);
                });
                $('#selectTemaOrigen').selectpicker('refresh'); 
                $('#selectTemaOrigen').val(temas[0].id).trigger('change');

                $('#selectTemaDestino').selectpicker('refresh'); 
                $('#selectTemaDestino').val(temas[0].id).trigger('change');

                //ACTUALIZAR TABLAS USUARIOS X TEMA
                actualizarTablaUsuariosXTema('tablaUsuariosTemaOrigen', temas[0].id);
                actualizarTablaUsuariosXTema('tablaUsuariosTemaDestino', temas[0].id);

                llenarTablaTemas(temas);
            }
    });
}


function crearTema(){
    var objTema = 	{
                              "nombre": document.getElementById("inputNombreNuevoTema").value,
                              "descripcion": document.getElementById("inputDescripcionNuevoTema").value
                        }
    $.ajax({
        type : 'POST',
        url : _baseURL+'/theme',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(objTema),									
        success : function(tema) {
            activarPositionToastr('centrado-abajo');
            toastr["success"]("Tema creado con éxito !!", "Nuevo Tema");
            actualizarTemasSelects();

            //LIMPIAR
            document.getElementById("inputNombreNuevoTema").value = '';
            document.getElementById("inputDescripcionNuevoTema").value = '';
        },
        error : function(response) {
            console.log(response)
            if(response.readyState == 0){
                errorSeguridadKeyCloak();
            }else{	
                activarPositionToastr('centrado-abajo');					
                toastr["error"]("No se pudo crear el tema", "Error Nuevo Tema");
            }
        }
    });
}

function llenarTablaTemas(temas){
    //RECUPERAR USUARIOS
    let usuariosTodos = null;
    $.ajax({
            type : 'GET',
            url : _baseURL+'/user',
            async: false,
            success : function(users) {
                usuariosTodos = users;
            }
    });

    $('#tablaTemas').DataTable({ 
                    "destroy": true,
                    "bDeferRender": true,
                    "aaSorting": [[0, 'asc']],
                    "data": temas, 
                    "columns":[					            
                        {"data":"nombre"},
                        {"data":"descripcion"},
                        {
                            "render": function ( data, type, full, meta ) {
                                var options = ``;
                                var usuariosSeleccionados = full.usuarios;
                                if(usuariosSeleccionados != null){
                                    usuariosSeleccionados.forEach(usuario => {
                                        options += `<option value="${usuario.id}" selected="selected">${usuario.nombre +" "+ usuario.paterno +" "+ usuario.materno}</option>`;
                                    });
                                }

                                var banderaExisteUsuario = false;
                                if(usuariosTodos != null){
                                    usuariosTodos.forEach(usuario => {
                                        banderaExisteUsuario = false;
                                        if(usuariosSeleccionados != null){
                                            usuariosSeleccionados.forEach(userSeleccionado => {
                                                if(userSeleccionado.id == usuario.id){
                                                    banderaExisteUsuario = true;
                                                }
                                            });
                                            if(banderaExisteUsuario == false){
                                                options += `<option value="${usuario.id}">${usuario.nombre +" "+ usuario.paterno +" "+ usuario.materno}</option>`;
                                            }
                                                
                                        }else{
                                            options += `<option value="${usuario.id}">${usuario.nombre +" "+ usuario.paterno +" "+ usuario.materno}</option>`;
                                        }											
                                    });
                                }

                                return `<div class="row d-flex justify-content-center mt-100">
                                            <div class="col-md-12"> 
                                                <select 
                                                id="multiSelectUsuariosTema${full.id}" 
                                                class="multiSelectUsuarios" 
                                                placeholder="Agregar Usuarios" 
                                                multiple
                                                onchange="cambiarUsuarioATema(this.id, ${full.id})">
                                                    ${options}
                                                </select> 
                                            </div>
                                        </div>`;
                            }
                        }
                    ],
                    language: {
                        search:         "Buscar&nbsp;:",
                        lengthMenu: "_MENU_ renglones",
                        zeroRecords: "No se encontraron resultados",
                        info:           "Mostrando _START_ a _END_ de _TOTAL_ renglones totales",
                        infoEmpty:      "Mostrando 0 a 0 de 0 renglones totales",
                        infoFiltered:   "(Filtrado _START_ de _MAX_ renglones)"
                    }
            });
}

function cambiarUsuarioATema(idComponente, idTema){
    //console.log([...document.getElementById(idComponente).options])
    var objUserTema = [];
    var options = [...document.getElementById(idComponente).options];
    
    options.forEach(optionSelected => {
        objUserTema.push(	{
                               "theme_id": idTema,
                               "users_id": parseInt(optionSelected.value)
                            });
    });

    //MANDAR OBJETO CON USUARIO EN 0 PARA ELIMINAR DE LA BD
    if(objUserTema.length == 0){
        objUserTema.push(	{
                               "theme_id": idTema,
                               "users_id": 0
                            });
    }

    $.ajax({
        type : 'PUT',
        url : _baseURL+'/belong',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(objUserTema),									
        success : function(usuarios) {
            activarPositionToastr('centrado-abajo');
            toastr["success"]("Usuarios actualizados con éxito !!", "Cambio en Usuarios");
        },
        error : function(response) {
            console.log(response)
            if(response.readyState == 0){
                errorSeguridadKeyCloak();
            }else{	
                activarPositionToastr('centrado-abajo');					
                toastr["error"]("No se pudo agregar el usuario", "Error Agregar Usuario al tema");
            }
        }
    });
}

//<!-- END MENU TEMAS*******************************************************************************************************************-->
