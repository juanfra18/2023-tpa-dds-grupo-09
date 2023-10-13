document.querySelectorAll('.btn-eliminar').forEach(button => {
    button.addEventListener('click', function() {
        var organismoId = this.getAttribute('data-organismo-id');

        // Realizar una solicitud al servidor para eliminar el usuario
        fetch('/organismosDeControl/eliminar/' + organismoId, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                // Usuario eliminado con éxito, puedes actualizar la interfaz o redirigir si es necesario
                console.log('Organismo de Control eliminado con éxito');
            } else {
                console.error('Error al eliminar Organismo de Control');
            }
        });
    });
});
