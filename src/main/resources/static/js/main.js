$(document).ready(function(){
    $.get("/bixi", {x : "10", y : "10"}).done(
        function(data){ 
            console.log(data);
        });
    $("#btnRechercheHeure").click(function(){
        emptyMap();
    	var dateDebut = document.getElementById("inpDateDebut").value;
    	var dateFin = document.getElementById("inpDateFin").value; 

        $.get("/horaires-camions", {du : dateDebut, au : dateFin}).done(
            function(data){ 
                for(var i = 0 ; i < data.length ; i++){
                    if(data[i].locations.length > 0){
                            addFoodTruckMarkerToMap(data[i]);
                    }
                }
            });
    });
});

var mymap = L.map('mapid').setView([45.5017, -73.5673], 10);
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
	maxZoom: 18,
	attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
		'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
		'Imagery © <a href="http://mapbox.com">Mapbox</a>',
	id: 'mapbox.streets'
}).addTo(mymap);
var listeMarker = [];
/*
 * ici c'est la partie qu'il reste à faire pour avoir fini la partie foodTrucks
 * je n'ai aucune idée de comment retirer un marker
 */
function emptyMap(){
    
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
        listeMarker.push(circle);
}
function addFoodTruckMarkerToMap(foodtruck){
    emptyMap();
    for(var j = 0 ; j < foodtruck.locations.length ; j++){
        var marker = L.marker([
            foodtruck.locations[j].coord.y,
            foodtruck.locations[j].coord.x
        ]).bindPopup(
                "<div style='text-align: center;''>"
                        +"<b>"
                                + foodtruck.name
                        +"</b>"
                + "</div>"
                + "</br>"
                + "Heure d'ouverture: "+ foodtruck.locations[j].timeStart
                + "<br>"
                + "Heure de fermeture: "+ foodtruck.locations[j].timeEnd
                + "<br>"
                + "date: "+ foodtruck.locations[j].date
        ).openPopup();
        marker.addTo(mymap).on("click", showBixyAroundFoodTruck);
        listeMarker.push(marker);
    }
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

function emptyMap(){
    console.log("emptyMap");
    for(var i = 0 ; i < listeMarker.length; i++){
        mymap.removeLayer(listeMarker[i]);
    }
}

