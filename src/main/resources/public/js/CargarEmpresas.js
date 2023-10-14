document.getElementById('cargaDeDatos').addEventListener('click', function() {

    var datos = [];
    var ruta ='/cargarEmpresas';

    for (var i = 1; i <= 11; i++) {
        var informacion = document.getElementById('informacion' + i).value;
        datos.push(informacion);
        ruta += '/' + informacion;
    }

        fetch(ruta,{
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                console.log('Carga exitosa');
            } else {
                console.error('Error en la carga');
            }
        });

});
