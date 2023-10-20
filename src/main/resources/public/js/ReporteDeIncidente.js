document.querySelector('.btn-cargarReporte').addEventListener('submit', function() {
    var estado = this.getAttribute('data-estado');
    fetch('/reportarIncidente/' + estado, {
        method: 'POST'
    })
    .then(response => {
        if (response.ok) {

        } else {
            window.alert('Error al crear reporte');
        }
    });
});

const inputGroupSelect01 = document.getElementById('inputGroupSelect01');
const inputGroupSelect02 = document.getElementById('inputGroupSelect02');
const inputGroupSelect03 = document.getElementById('inputGroupSelect03');

inputGroupSelect01.addEventListener('change', () => {
    const entidadSeleccionadaId = inputGroupSelect01.value;

    if(entidadSeleccionadaId!="Seleccionar..."){

    fetch('/entidades/' + entidadSeleccionadaId + '/establecimientos')
        .then(response => response.json())
        .then(establecimientos => {
            // Limpiar el segundo select
            inputGroupSelect02.innerHTML = '';
            const option1 = document.createElement('option');
                            option1.value=-1;
                            option1.textContent = "Seleccionar...";
                            inputGroupSelect02.appendChild(option1);
            // Agregar opciones al segundo select
            establecimientos.forEach(establecimiento => {
                const option = document.createElement('option');
                option.value = establecimiento.id;
                option.textContent = establecimiento.nombre;
                inputGroupSelect02.appendChild(option);
            });
        });}
        else{
            inputGroupSelect02.innerHTML = '';
            inputGroupSelect03.innerHTML = '';
        }
});

inputGroupSelect02.addEventListener('change', () => {
    const entidadSeleccionadaId = inputGroupSelect01.value;
    const establecimientoSeleccionadoId = inputGroupSelect02.value;
    if(establecimientoSeleccionadoId>-1){
    fetch('/entidades/' + entidadSeleccionadaId + '/establecimientos/' + establecimientoSeleccionadoId + '/servicios')
        .then(response => response.json())
        .then(servicios => {
            // Limpiar el tercer select
            inputGroupSelect03.innerHTML = '';
            const option1 = document.createElement('option');
                            option1.value=-1;
                            option1.textContent = "Seleccionar...";
                            inputGroupSelect03.appendChild(option1);
            // Agregar opciones al tercer select
            servicios.forEach(servicio => {
                const option = document.createElement('option');
                option.value = servicio.id;
                option.textContent = servicio.nombre + ' ' + servicio.tipoNombre;
                inputGroupSelect03.appendChild(option);
            });
        });
}
else{
    inputGroupSelect03.innerHTML = '';
}
});

document.addEventListener("DOMContentLoaded", function () {
    const inputFields = document.querySelectorAll(".input-field");
    const enviarButton = document.querySelector(".btn-cargarReporte");

    // Verifica si todos los campos tienen valores
    function checkFields() {
        let allFieldsFilled = true;
        inputFields.forEach(function (field) {
        console.log('value: ' + field.value);
            if (!field.value || field.value=="Seleccionar..." || field.value=="-1"){
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
