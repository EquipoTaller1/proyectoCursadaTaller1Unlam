document.addEventListener('DOMContentLoaded', () => {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			//coordenas de cliente
			var latitude = position.coords.latitude;
			var longitude = position.coords.longitude;

			/*infoMedicos = document.getElementById("jsonMedicosHidden");
			var jsonMedicos = infoMedicos.innerHTML;
			console.log(jsonMedicos);*/

			//instanciar map
			var mymap = L.map('mapaPaciente', {
				center: [latitude, longitude],
				zoom: 12
			});
			
			L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
				maxZoom: 25,
				attribution: 'Datos del mapa de &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a>, ' + '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' + 'Imágenes © <a href="https://www.mapbox.com/">Mapbox</a>',
				id: 'mapbox/streets-v11'
			}).addTo(mymap);

			axios.get('/proyecto_limpio_spring_war_exploded/api/medicos')
				.then(response => {

					response.data.forEach( medico => {
						var ruta = L.Routing.control({
							waypoints: [
								L.latLng(latitude, longitude),
								L.latLng(medico.lat_actual, medico.long_actual)
							],
							language: 'es'
						});
						ruta.addTo(mymap);

						//Obtengo tiempo y distancia y lo muestro en un popUp con datos del Medico
						ruta.on('routesfound', function(e) {
							var rutasEncontradas = e.routes;
							var summary = rutasEncontradas[0].summary;

							var distanciaTotal = Math.round(summary.totalDistance / 1000);
							var tiempoEstimado = Math.round(summary.totalTime % 3600 / 60);

							var marker = L.marker([medico.lat_actual, medico.long_actual]).addTo(mymap);
							var txtPopUp = "<br>Especialidad: " + medico.especialidad + "</br><br>Edad: "
								+ medico.edad + "</br><br>Demora: " + tiempoEstimado + " minutos</br><br>Distancia: "
								+ distanciaTotal + " Km</br>";
							marker.bindPopup(txtPopUp).openPopup();
						});

					})
				})
				.catch(error => {
					console.log("error api")
				})
		});
	}
	else {
		console.log("no funca geolocalizacion")
	}

})
