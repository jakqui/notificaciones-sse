//<!-- START MENU USUARIOS*******************************************************************************************************************-->	
function llenarTablaUsuarios(usuarios){
    $('#tablaUsuarios').DataTable({ 
                    "destroy": true,
                    "bDeferRender": true,
                    "aaSorting": [[0, 'asc']],
                    "data": usuarios, 
                    "columns":[					            
                        {"data":"numero"},
                        {"data":"nombre"},
                        {"data":"paterno"},
                        {"data":"materno"},
                        {"data":"email"},
                        {"data":"id"}
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

function modalNuevoUsuario(){
    $('#modalNuevoUsuario').modal('show')
}	

function crearUsuario(){
    var objUsuario = 	{
                              "materno": document.getElementById("inputApellidoMaternoNuevoUsuario").value,
                              "nombre": document.getElementById("inputNombreNuevoUsuario").value,
                              "numero": document.getElementById("inputNumeroNuevoUsuario").value,
                              "paterno": document.getElementById("inputApellidoPaternoNuevoUsuario").value,
                              "email": document.getElementById("inputEmailNuevoUsuario").value
                        }//jakqueline.herrera@turbomaquinas.com
    $.ajax({
        type : 'POST',
        url : _baseURL+'/user',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(objUsuario),									
        success : function(usuario) {
            //console.log("nuevo usuario creado: "+usuario)
            activarPositionToastr('centrado-abajo');
            toastr["success"]("Usuario creado con éxito !!", "Nuevo Usuario");
            actualizarUsuariosSelects();

            //LIMPIAR
            document.getElementById("inputApellidoMaternoNuevoUsuario").value = '';
            document.getElementById("inputNombreNuevoUsuario").value = '';
            document.getElementById("inputNumeroNuevoUsuario").value = '';
            document.getElementById("inputApellidoPaternoNuevoUsuario").value = '';
            document.getElementById("inputEmailNuevoUsuario").value = '';
        },
        error : function(response) {
            console.log(response)
            if(response.readyState == 0){
                errorSeguridadKeyCloak();
            }else{	
                activarPositionToastr('centrado-abajo');					
                toastr["error"]("No se pudo crear el usuario", "Error Nuevo Usuario");
            }
        }
    });
}	
//<!-- END MENU USUARIOS*******************************************************************************************************************-->
