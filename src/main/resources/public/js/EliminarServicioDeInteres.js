document.querySelectorAll('.btn-eliminar-servicioInteres').forEach(button => {
    button.addEventListener('click', function() {
        var servicioId = this.getAttribute('data-servicio-id');

                    fetch('/intereses/servicio/eliminar/' + servicioId, {
                        method: 'POST'
                    })
                    .then(response => {
                        if (response.ok) {
                            window.location.href = '/intereses';
                            console.log('Interes eliminado con éxito');
                        } else {
                            console.error('Error al eliminar interés');
                        }
                    });
    });
});
