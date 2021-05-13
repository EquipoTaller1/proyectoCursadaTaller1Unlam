document.addEventListener('DOMContentLoaded', () => {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			//coordenas de cliente
			var latitude = position.coords.latitude;
			var longitude = position.coords.longitude;

			infoMedicos = document.getElementById("jsonMedicosHidden");
			var jsonMedicos = infoMedicos.innerHTML;
			console.log(jsonMedicos);

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



			//agregar ruta de medico
			L.Routing.control({
				waypoints: [
								
					L.latLng(latitude, longitude),
					L.latLng(-34.6699, -58.5622) //        -->>1
					
				],
				language: 'es'
				
				
			}).addTo(mymap);
			
			L.Routing.control({
				waypoints: [
								
					L.latLng(latitude, longitude),
					L.latLng(-34.7148928,-58.5826893) // -->>2
					
				],
				language: 'es'
				
				
			}).addTo(mymap);
			
			L.Routing.control({
				waypoints: [		
				
					L.latLng(latitude, longitude),
					L.latLng(-34.6824033,-58.5121902) // -->>3
					
				],
				language: 'es'
				
				
			}).addTo(mymap);
			
			L.Routing.control({
				waypoints: [
				
				
					L.latLng(latitude, longitude),
					L.latLng(-34.5992347,-58.46747) // -->>4
					


				],
				language: 'es'
				
				
			}).addTo(mymap);
		});
	}
	else {
		console.log("no funca geolocalizacion")
	}
})