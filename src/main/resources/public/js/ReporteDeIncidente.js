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

document.addEventListener("DOMContentLoaded", function () {
    const inputFields = document.querySelectorAll(".input-field");
    const enviarButton = document.querySelector(".btn-cargarReporte");

    // Verifica si todos los campos tienen valores
    function checkFields() {
        let allFieldsFilled = true;
        inputFields.forEach(function (field) {
            if (!field.value) {
                allFieldsFilled = false;
            }
        });

        if (allFieldsFilled) {
            enviarButton.removeAttribute("disabled");
        } else {
            enviarButton.setAttribute("disabled", "disabled");
        }
    }

    // Escucha eventos de cambio en los campos
    inputFields.forEach(function (field) {
        field.addEventListener("input", checkFields);
    });
});
