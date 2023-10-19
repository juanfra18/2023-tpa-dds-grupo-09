inputGroupSelect01.addEventListener('change', () => {
    var comunidadId = inputGroupSelect01.value;

     fetch('/comunidades/incidentes/' + comunidadId)
        .then(response => {
            if (response.ok) {
               window.location.href='/comunidades/incidentes/' + comunidadId;
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

        } else {
            window.alert('Error al cerrar incidente');
        }
    });
});