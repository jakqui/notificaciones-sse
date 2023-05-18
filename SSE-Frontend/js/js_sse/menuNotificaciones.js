//<!-- START MENU NOTIFICACIONES >> CONECTAR TEMA *******************************************************************************************************************-->
$(document).ready(function() {
    $('#example').DataTable();


    actualizarMultiSelects();
} );

/*$('#tablaTemas')
                .on( 'order.dt',  function () { actualizarMultiSelects(); } )
                .on( 'search.dt', function () { actualizarMultiSelects(); } )
                .on( 'page.dt',   function () { actualizarMultiSelects(); } )
                .DataTable();*/


function actualizarMultiSelects(){
    var multipleCancelButton = new Choices('.multiSelectUsuarios', {
        removeItemButton: true,
        //maxItemCount:5,
        //searchResultLimit:5,
        //renderChoiceLimit:5
    });
}

function mostrarContenido(idComponente){
    //GENERAR NOMBRE DEL DIV QUE SE VA A MOSTRAR
    var nombreComponente = 'divContenido'+idComponente.split('contenido')[1];
    var divsContenido = document.getElementsByClassName('divsContenido');			
    for(i = 0; i < divsContenido.length; i++){
        divsContenido[i].style.display = 'none';
    }
    document.getElementById(nombreComponente).style.display = 'block';
}

actualizarUsuariosSelects();
actualizarTemasSelects();		

function conectar(){			
    _numeroUsuario = document.getElementById("selectUsuario").value;			

    //TEMAS A LOS QUE ESTA CONECTADO EL USUARIO
    $.ajax({
        type : 'GET',
        url : _baseURL+'/notifications/user-themes/'+_numeroUsuario,
        async: false,
        success : function(temas) {
            /*LA FORMA DE CONECTARSE AL SERVIDOR DE NOTIFICACIONES ES A TRAVES DEL NUMERO DE EMPLEADO
                    SOLO CONECTAR AL SERVIDOR SI EL USUARIO TIENE TEMAS
            */
            if(es != null){
                if(es.readyState == 1){
                    cerrarConexion();
                }
            }
            es = new EventSource(_baseURL+"/SSE/configuracion/notificaciones/"+_numeroUsuario);
            
            //DETECTAR CONEXION ABIERTA
            es.onopen = function() {
               validarStatusConexion();
            };
            //DETECTAR ERRORES
            es.onerror = function(e) {
                validarStatusConexion();
            };
            
            //POR CADA TEMA, DEBE CREAR UN ESCUCHADOR
            temas.forEach(tema => {
                //console.log("Se conectó al tema: "+tema.theme_nombre)
                //1)AGREGAR ESCUCHADOR POR CADA TEMA
                es.addEventListener(tema.theme_nombre, event => {
                    let dataNotificacion = (event.data);
                    
                    //SI NO EXISTE LA NOTIFICACION RECIBIRLA
                    if(JSON.parse(dataNotificacion).numero_usuario_dirigida == _numeroUsuario){								
                        console.log("Del tema: " + tema.theme_nombre + " -- Se recibió notificación: "+dataNotificacion);
                        //agregarNotificacion(JSON.parse(dataNotificacion), 'arriba');
                        recorrerNotificaciones();
                        //toastr.info(JSON.parse(event.data).descripcion,'Nueva Notificacion', {timeOut: 3000,showEasing:"swing",positionClass:"toast-top-right"});

                        activarPositionToastr('arriba');
                        toastr["success"](JSON.parse(event.data).descripcion, "Nueva Notificación");
                    }
                }, false);
            });

            //2)AGREGAR TEMAS A LA TABLA
            $('#tablaTemasPorUsuario').DataTable({ 
                    "destroy": true,
                    "bDeferRender": true,
                    "aaSorting": [[1, 'desc']],
                    "data": temas, 
                    "columns":[					            
                        {"data":"theme_nombre"},
                        {"data":"theme_nombre"}
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
    });			

    recorrerNotificaciones();
}

function actualizarUsuariosSelects(){
    //LIMPIAR SELECT DE USUARIOS
    $('#selectUsuario').empty();
    $.ajax({
            type : 'GET',
            url : _baseURL+'/user',
            async: false,
            success : function(usuarios) {
                //POR CADA USUARIO AGREGARLO AL SELECT
                usuarios.forEach(usuario => {
                    $('#selectUsuario').append(`<option value=${usuario.numero}>${usuario.nombre} ${usuario.paterno} ${usuario.materno}</option>`);
                });
                $('#selectUsuario').selectpicker('refresh'); 
                $('#selectUsuario').val(usuarios[0].numero).trigger('change');

                llenarTablaUsuarios(usuarios);
            }
    });
}

function actualizarTablaUsuariosXTema(nombreTabla, idTema){
    //console.log(nombreTabla, idTema)
    $.ajax({
            type : 'GET',
            url : _baseURL+'/theme/'+idTema+'/users',
            async: false,
            success : function(usuarios) {
                $('#'+nombreTabla).DataTable({ 
                    "destroy": true,
                    "bDeferRender": true,
                    "aaSorting": [[1, 'desc']],
                    "data": usuarios, 
                    "columns":[					            
                        {"data":"numero"},
                        {
                            "render": function ( data, type, full, meta ) {
                                return full.nombre + ' ' + full.paterno + ' ' + full.materno;
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
    });
    
}

function agregarNotificacion(notificacion, ubicacion){
    //SI NO EXISTE LA NOTIFICACION SE AGREGA
    if(!document.getElementById("divNotificacionNo"+notificacion.id)){
        //Estilos notificaciones leidas
        var stylecolorBack = 'background-color:#fff';
        var stylecolor = 'fff';
        var icono = 'bi-envelope-check';
        //Estilos notificaciones NO leidas
        if(notificacion.lectura_por_usuario == 0){
            //cantidadNotiSinLeer ++;
            stylecolorBack = 'background-color:#e3e7fb';
            stylecolor = '#e3e7fb';
            icono = 'bi-envelope-exclamation-fill';
        }
        
        var liNotificacion = `	<div class="dropdown-item" id="divNotificacionNo${notificacion.id}" style="${stylecolorBack}">
                                    <div class="row">
                                        <div class="col-md-2 profile-img">
                                            <i class="${icono}" style="font-size: 20px;"></i>
                                        </div>
                                        <div class="col-md-10">
                                            <a href="javascript:leerNotificacion(${notificacion.id})">
                                                <b>${notificacion.titulo}</b> ${notificacion.descripcion}
                                                <br>
                                                <small><strong>De ${notificacion.theme_nombre_origen}</strong></small>
                                                <br>
                                                <small>Hace ${notificacion.tiempo_transcurrido}</small> -
                                                <small>${moment(notificacion.creado).format("DD/MM/YYYY h:mm:ss")}</small>
                                            </a>
                                        </div>
                                    </div>
                                </div>`;
        if(ubicacion == 'arriba'){
            $("#divNotificaciones").prepend(liNotificacion);
        }
        if(ubicacion == 'abajo'){
            $('#divNotificaciones').append(liNotificacion);
        }
        //AGREGAR NOTIFICACION AL ARREGLO
        _notificaciones.push(notificacion);
    }else{
        //console.log("Ya existe esa notificacion")
    }			
}

function recorrerNotificaciones(){
    $("#divNotificaciones").empty();
    //START NOTIFICACIONES ANTERIORES DEL USUARIO****************************************************************************************
    $.ajax({
        type : 'GET',
        url : _baseURL+'/notifications/user/'+_numeroUsuario,
        data: {statusLecturaUsuario: '%'},
        async: false,
        success : function(notificaciones){     
            _notificaciones = notificaciones;
        }
    });

    var cantidadNotiSinLeer = 0;
    _notificaciones.forEach(notificacion => {
        agregarNotificacion(notificacion, 'abajo'); 
        //CONTAR NOTIFICACIONES SIN LEER
        if(notificacion.lectura_por_usuario == 0){
            cantidadNotiSinLeer ++;
        }   
    });
    //NOTIFICACIONES SIN LEER
    $("#spanCantidadNotificacionesSinLeer").html(cantidadNotiSinLeer);
    //END NOTIFICACIONES ANTERIORES DEL USUARIO*******************************************************************************************
}

function cerrarConexion(){
    $('#tablaTemasPorUsuario').dataTable().fnClearTable();
    es.close();
    document.getElementById("spanCantidadNotificacionesSinLeer").innerHTML = 0;
    $("#divNotificaciones").empty();
    validarStatusConexion();			
}

function validarStatusConexion(){
    document.getElementById('labelDesconectado').style.display = 'none';
    document.getElementById('labelConectado').style.display = 'none';
    document.getElementById('btnConectar').style.visibility = 'visible';
    document.getElementById('btnDesconectar').style.visibility = 'visible';

    //console.log(es.readyState)
    //CONEXION ABIERTA
    if(es.readyState == 1){
        document.getElementById('labelConectado').style.display = 'block';
        document.getElementById('btnConectar').style.visibility = 'hidden';
    }
    //CONEXION CERRADA
    if(es.readyState == 2){
        document.getElementById('labelDesconectado').style.display = 'block';
        document.getElementById('btnDesconectar').style.visibility = 'hidden';
    }
    //SIN CONEXION
    if(es.readyState == 0){
        document.getElementById('labelDesconectado').style.display = 'block';
    }
}

function leerNotificacion(idNotificacion){
    $.ajax({
            type : 'PUT',
            url : _baseURL+'/notifications/'+idNotificacion+'/user/'+_numeroUsuario,
            async : false,
            success : function(notificacionLeida) {
                recorrerNotificaciones();
                //document.getElementById("divVerNotificaciones").classList.add("show");
            }
    });
}

function crearNotificacion(){
    var objNotificacion = 	{
                              //"adicionales": document.getElementById("inputApellidoMaternoNuevoUsuario").value,
                              "theme_id_origen": document.getElementById("selectTemaOrigen").value,
                              "theme_id_destino": document.getElementById("selectTemaDestino").value,
                              "titulo": document.getElementById("inputTituloNuevaNotificacion").value,
                              "descripcion": document.getElementById("inputDescripcionNuevaNotificacion").value
                        }
    //console.log(objNotificacion)
    $.ajax({
        type : 'POST',
        url : _baseURL+'/notifications',
        contentType : 'application/json; charset=utf-8',
        data : JSON.stringify(objNotificacion),									
        success : function(notificacion) {
            activarPositionToastr('centrado-abajo');
            toastr["success"]("Notificación creada con éxito !!", "Nueva Notificación");

            //LIMPIAR
            document.getElementById("inputTituloNuevaNotificacion").value = '';
            document.getElementById("inputDescripcionNuevaNotificacion").value = '';
        },
        error : function(response) {
            console.log(response)
            if(response.readyState == 0){
                errorSeguridadKeyCloak();
            }else{	
                activarPositionToastr('centrado-abajo');					
                toastr["error"]("No se pudo crear la notificacion", "Error Nueva Notificación");
            }
        }
    });
}	
//<!-- END MENU NOTIFICACIONES >> CONECTAR TEMA *******************************************************************************************************************-->
