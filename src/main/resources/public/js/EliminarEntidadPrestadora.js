document.querySelectorAll('.btn-eliminar').forEach(button => {
    button.addEventListener('click', function() {
        var organismoId = this.getAttribute('data-organismo-id');
        var entidadPrestadoraId = this.getAttribute('data-entidadPrestadora-id');
        // Realizar una solicitud al servidor para eliminar el usuario
        fetch('/organismosDeControl/' + organismoId + '/entidadesPrestadoras/eliminar/' + entidadPrestadoraId, {
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
