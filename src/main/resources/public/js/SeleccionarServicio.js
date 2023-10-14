document.getElementById('informacion10').addEventListener('change', function() {
    var selectDetalles = document.getElementById('informacion11');
    selectDetalles.innerHTML = ''; // Limpiar el select

    var servicioSeleccionado = this.value;

    if (servicioSeleccionado === 'Banio') {
        var opciones = ['CABALLEROS', 'DAMAS', 'UNISEX'];
    } else if (servicioSeleccionado === 'Elevacion') {
        var opciones = ['ASCENSOR', 'ESCALERAS_MECANICAS'];
    }
            opciones.forEach(function(opcion) {
                var option = document.createElement('option');
                option.value = opcion;
                option.text = opcion;
                selectDetalles.appendChild(option);
            });
});