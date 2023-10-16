inputGroupSelect01.addEventListener('change', () => {
    var estado = inputGroupSelect01.value;

     fetch('/incidentes/' + estado)
        .then(response => {
            if (response.ok) {
               window.location.href='/incidentes/' + estado;
            } else {
                window.alert("Ocurrio un error");
            }
        })
});
