document.querySelectorAll('.btn-eliminar').forEach(button => {
    button.addEventListener('click', function() {
        var organismoId = this.getAttribute('data-organismo-id');
        var entidadPrestadoraId = this.getAttribute('data-entidadPrestadora-id');
        var entidadId = this.getAttribute('data-entidad-id');
        // Realizar una solicitud al servidor para eliminar el usuario
        fetch('/organismosDeControl/' + organismoId + '/entidadesPrestadoras/' + entidadPrestadoraId + '/entidades/eliminar/' + entidadId, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                // Usuario eliminado con éxito, puedes actualizar la interfaz o redirigir si es necesario
                console.log('Entidad Prestadora eliminada con éxito');
            } else {
                console.error('Error al eliminar Entidad Prestadora');
            }
        });
    });
});
