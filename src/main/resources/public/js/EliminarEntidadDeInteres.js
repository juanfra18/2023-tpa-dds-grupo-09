document.querySelectorAll('.btn-eliminar-entidadInteres').forEach(button => {
    button.addEventListener('click', function() {
        var entidadId = this.getAttribute('data-entidad-id');

                    fetch('/intereses/entidad/eliminar/' + entidadId, {
                        method: 'POST'
                    })
                    .then(response => {
                        if (response.ok) {
                            window.location.href = '/intereses';
                            console.log('Interes eliminado con éxito');
                        } else {
                         window.location.href = '/menu';
                            console.error('Error al eliminar interés');
                        }
                    });
    });
});
