inputGroupSelect01.addEventListener('change', () => {
    var estado = inputGroupSelect01.value;
    if(estado!="Seleccionar...")
     fetch('/incidentes/' + estado)
        .then(response => {
            if (response.ok) {
               window.location.href='/incidentes/' + estado;
            } else {
                window.alert("Ocurrio un error");
            }
        })
});

inputGroupSelect02.addEventListener('change', () => {
    var estado = inputGroupSelect01.value;
    var comunidadId = inputGroupSelect02.value;
    if(comunidadId!="Seleccionar..." && estado!="Seleccionar...")
     fetch('/incidentes/' + estado + '/comunidad/' + comunidadId)
        .then(response => {
            if (response.ok) {
               window.location.href='/incidentes/' + estado + '/comunidad/' + comunidadId;
            } else {
                window.alert("Ocurrio un error");
            }
        })
});

document.querySelector('.btn-cerrar-incidente').addEventListener('click', function() {
    var incidenteId = this.getAttribute('data-incidente-id');
    var comunidadId = this.getAttribute('data-comunidad-id');
    fetch('/reportarIncidente/CERRADO/' + incidenteId + '/' + comunidadId, {
        method: 'POST'
    })
    .then(response => {
        if (response.ok) {
            window.location.href='/incidentes/ABIERTO' + '/comunidad/' + comunidadId;
        } else {
            window.alert('Error al cerrar incidente');
        }
    });
});