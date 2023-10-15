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

inputGroupSelect01.addEventListener('change', () => {
        const entidadSeleccionada = inputGroupSelect01.value; // Obtiene la entidad seleccionada
        const entidad = entidades.find(e => e.nombre === entidadSeleccionada); // Busca la entidad en la lista de entidades

        // Obtener la lista de establecimientos de la entidad seleccionada
        const establecimientosEntidad = entidad.establecimientos;

        // Limpiar el segundo select
        inputGroupSelect02.innerHTML = '';

        // Agregar opciones al segundo select
        establecimientosEntidad.forEach(establecimiento => {
            const option = document.createElement('option');
            option.value = establecimiento.nombre;
            option.textContent = establecimiento.nombre;
            inputGroupSelect02.appendChild(option);
        });
});

inputGroupSelect02.addEventListener('change', () => {
        const entidadSeleccionada = inputGroupSelect01.value;
        const entidad = entidades.find(e => e.nombre === entidadSeleccionada);
        const establecimientosEntidad = entidad.establecimientos;

        const establecimientoSeleccionado = inputGroupSelect02.value; // Obtiene el establecimiento seleccionado
        const establecimiento = establecimientosEntidad.find(e => e.nombre === establecimientoSeleccionado);

        // Obtener la lista de servicios del establecimiento seleccionado
        const serviciosEstablecimiento = establecimiento.servicios;

        // Limpiar el tercer select
        inputGroupSelect02.innerHTML = '';

        // Agregar opciones al tercer select
        serviciosEstablecimiento.forEach(servicio => {
            const option = document.createElement('option');
            option.value = servicio.nombre;
            option.textContent = servicio.nombre;
            inputGroupSelect03.appendChild(option);
        });
});

