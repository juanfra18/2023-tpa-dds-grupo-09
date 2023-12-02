const boton = document.getElementById("btn-crear-comunidad");
boton.addEventListener('click', crearComunidad);


function crearComunidad(){
    var nombre = prompt("Ingrese el nombre de la comunidad: ");

    if(nombre !== null && nombre !== ""){
        fetch("/comunidades/nueva/" + nombre,{
            method: 'POST',
            body: JSON.stringify({nombre}),
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response =>{
            if(response.ok) {
                window.location.href='/comunidades';
            }else if(response.status === 409){
                window.alert("Ese nombre de comunidad ya existe, elija otro.");
            }else{
                window.alert("No se pudo crear la comunidad, ocurrio un error.");
            }
        });
    }
}