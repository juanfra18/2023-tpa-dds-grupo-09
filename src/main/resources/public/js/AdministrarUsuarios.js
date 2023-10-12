document.querySelectorAll('.btn-ver-perfil').forEach(button => {
    button.addEventListener('click', function() {
        // Obtener el ID del usuario desde la tarjeta
        var userId = this.getAttribute('data-user-id');

        // Redirigir al perfil del usuario
        window.location.href = '/perfil/' + userId;
    });
});

