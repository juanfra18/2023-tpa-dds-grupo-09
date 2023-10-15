document.querySelector('.btn-cargarReporte').
button.addEventListener('click', function() {
        var estado = this.getAttribute('data-estado')
        fetch('/reportarIncidente/' + estado, {
            method: 'POST'
            console.log(estado);
        })
        .then(response => {
            if (response.ok) {
                window.alert('Reporte creado con éxito');
            } else {
                window.alert('Error al crear reporte');
            }
        });
    });

