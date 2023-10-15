document.querySelectorAll('.btn-unirse').forEach(button => {
    button.addEventListener('click', function() {
        var comunidadId = this.getAttribute('data-comunidad-id');
        // Realizar una solicitud al servidor para eliminar el usuario
        fetch('/comunidades/unirseAComunidad/' + comunidadId, {
            method: 'POST'
        })
          .then(response => {
                    if (response.ok) {
                        window.location.href = '/comunidades';
                        console.log('Union a comunidad con éxito');
                    } else {
                        console.error('Error al unirse a comunidad');
                    }
                });
    });
});
