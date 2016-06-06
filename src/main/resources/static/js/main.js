$(document).ready(function(){
    $("#btnRechercheHeure").click(function(){
    	var dateDebut = document.getElementById("inpDateDebut").value;
    	var dateFin = document.getElementById("inpDateFin").value; 

    	var requete = "GET /horaire-camion?du="+dateDebut+"&au="+dateFin;
    	console.log(requete);
    });
});

var mymap = L.map('mapid').setView([45.5017, -73.5673], 10);
/*var iconeBixi = L.icon({iconUrl:'EchangeInternetTest\\Images\\bixi.jpg', iconSize:[40,30]});*/
/*var iconeVelo = L.icon({iconUrl:'EchangeInternetTest\\Images\\Velo.jpg', iconSize:[40,30]});*/
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
	maxZoom: 18,
	attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
		'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
		'Imagery © <a href="http://mapbox.com">Mapbox</a>',
	id: 'mapbox.streets'
}).addTo(mymap);

//initialise la carte avec tous les food trucks
for (var i = 0; i < dataFoodTruck.features.length; i++) {
	addFoodTruckMarkerToMap(i);
}

function addBixiToMap(j){
	var circle = L.circle([
		dataBixi[j].coordinateX, 
		dataBixi[j].coordinateY 
	], 5, {
		color: 'red',
	    fillColor: '#f03',
	    fillOpacity: 0.5
	}).bindPopup(
		"<div style='text-align: center;''>"+
			"<b>"+
		dataBixi[j].nom + 
			"</b>"+
		"</div>"+
		"</br>" + 
		"Place utilise:" + dataBixi[j].nombreUtilise + 
		"</br>" + 
		"Place total:" + dataBixi[j].nombreTotal + 
		"</br>" + 
		"Place libre:" + (dataBixi[j].nombreTotal - dataBixi[j].nombreUtilise)
	).addTo(mymap);
}
function addFoodTruckMarkerToMap(i){
	var marker = L.marker([
		dataFoodTruck.features[i].geometry.coordinates[1], 
		dataFoodTruck.features[i].geometry.coordinates[0]
	]).bindPopup(
		"<div style='text-align: center;''>"
			+"<b>"+
				dataFoodTruck.features[i].properties.Lieu
			+"</b>"
		+"</div>"+
		+"</br>"+
		dataFoodTruck.features[i].properties.Heure_debut
		+"<br>"+
		dataFoodTruck.features[i].properties.Heure_fin
		+"<br>"+
		dataFoodTruck.features[i].properties.Date
	).openPopup();
	marker.addTo(mymap).on("click", showBixyAroundFoodTruck);
}
function showBixyAroundFoodTruck(e) {
	var coord = this.getLatLng();
	var request = "GET /bixy?"+coord.lat+"&/"+coord.lng;
	//c'est ici qu'il faut construire le json pour faire la map
	for(var j = 0 ; j < dataBixi.length; j++){
		addBixiToMap(j);
	}
	console.log(request);
}