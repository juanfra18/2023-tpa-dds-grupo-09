document.querySelectorAll('.btn-eliminar').forEach(button => {
    button.addEventListener('click', function() {
        var organismoId = this.getAttribute('data-organismo-id');
        var entidadPrestadoraId = this.getAttribute('data-entidadPrestadora-id');
        var entidadId = this.getAttribute('data-entidad-id');
        var establecimientoId = this.getAttribute('data-establecimiento-id');
        // Realizar una solicitud al servidor para eliminar el usuario
        fetch('/organismosDeControl/' + organismoId + '/entidadesPrestadoras/' + entidadPrestadoraId + '/entidades/' + entidadId + '/establecimientos/eliminar/' + establecimientoId, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                // Usuario eliminado con éxito, puedes actualizar la interfaz o redirigir si es necesario
                console.log('Establecimiento eliminado con éxito');
            } else {
                console.error('Error al eliminar Establecimiento');
            }
        });
    });
});
