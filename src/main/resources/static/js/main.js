/* global L */

var mymap = L.map('mapid').setView([45.5017, -73.5673], 10);

L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
    maxZoom: 18,
    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
            '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
            'Imagery © <a href="http://mapbox.com">Mapbox</a>',
    id: 'mapbox.streets'
}).addTo(mymap);

var listeMarker = [];
var listeBixi = [];
var listeVelo = [];

$(document).ready(function () {

    //mettre la date par défaut //////////////
    var now = new Date();
    var month = (now.getMonth() + 1);
    var day = now.getDate();
    if (month < 10)
        month = "0" + month;
    if (day < 10)
        day = "0" + day;
    var today = now.getFullYear() + '-' + month + '-' + day;
    $('#inpDateDebut').val(today);
    $('#inpDateFin').val(today);
    //Fin mettre la date par défaut ///////////

    $("#btnRechercheHeure").click(function () {
        emptyMap();
        var dateDebut = document.getElementById("inpDateDebut").value;
        var dateFin = document.getElementById("inpDateFin").value;

        $.get("/horaires-camions", {du: dateDebut, au: dateFin}).done(
                function (data) {
                    emptyMap();
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].locations.length > 0) {
                            addFoodTruckMarkerToMap(data[i]);
                        }
                    }
                });
    });
});


function addBixiToMap(data) {
    var circle = L.circle([
        data.x,
        data.y
    ], 20, {
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5
    }).bindPopup(
            "<div style='text-align: center;''>" +
            "<b>" +
            data.name +
            "</b>" +
            "</div>" +
            "</br>" +
            "Place utilise:" + data.nbBikes +
            "</br>" +
            "Place libre:" + data.nbEmptyDocks +
            "</br>" +
            "Place total:" + (data.nbBikes + data.nbEmptyDocks)
            ).addTo(mymap);
    listeBixi.push(circle);
}

function addBixiToMap(data) {
    var circle = L.circle([
        data.x,
        data.y
    ], 20, {
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5
    }).bindPopup(
            "<div style='text-align: center;''>" +
            "<b>" +
            data.name +
            "</b>" +
            "</div>" +
            "</br>" +
            "Place utilise:" + data.nbBikes +
            "</br>" +
            "Place libre:" + data.nbEmptyDocks +
            "</br>" +
            "Place total:" + (data.nbBikes + data.nbEmptyDocks)
            ).addTo(mymap);
    listeBixi.push(circle);
}

function addVeloToMap(data) {
    var circle = L.circle([
        data.x,
        data.y
    ], 20, {
        color: 'blue',
        fillOpacity: 0.5
    }).addTo(mymap);
    listeVelo.push(circle);
}

function addFoodTruckMarkerToMap(foodtruck) {

    for (var j = 0; j < foodtruck.locations.length; j++) {
        var marker = L.marker([
            foodtruck.locations[j].coord.y,
            foodtruck.locations[j].coord.x
        ]).bindPopup(
                "<div style='text-align: center;''>"
                + "<b>"
                + foodtruck.name
                + "</b>"
                + "</div>"
                + "</br>"
                + "Heure d'ouverture: " + foodtruck.locations[j].timeStart
                + "<br>"
                + "Heure de fermeture: " + foodtruck.locations[j].timeEnd
                + "<br>"
                + "date: " + foodtruck.locations[j].date
                ).openPopup();
        marker.addTo(mymap).on("click", function () {
            removeBixi();
            removeVelo();
            $.get("/bixi", {y: this._latlng.lng, x: this._latlng.lat}).done(
                    function (data) {
                        for (var i = 0; i < data.length; i++) {
                            addBixiToMap(data[i]);
                        }
                        console.log(data);
                    });
                    
            $.get("/velo", {y: this._latlng.lng, x: this._latlng.lat}).done(
                    function (data) {
                        for (var i = 0; i < data.length; i++) {
                            addVeloToMap(data[i]);
                        }
                        console.log(data);
                    });
        });
        listeMarker.push(marker);
    }
}

function emptyMap() {
    console.log("emptyMap");
    var i;
    for (i = 0; i < listeMarker.length; i++) {
        mymap.removeLayer(listeMarker[i]);
    }
    for (i = 0; i < listeBixi.length; i++) {
        mymap.removeLayer(listeBixi[i]);
    }
    for (i = 0; i < listeVelo.length; i++) {
        mymap.removeLayer(listeVelo[i]);
    }
}
function removeBixi() {
    for (var i = 0; i < listeBixi.length; i++) {
        mymap.removeLayer(listeBixi[i]);
    }
}
function removeVelo() {
    for (var i = 0; i < listeVelo.length; i++) {
        mymap.removeLayer(listeVelo[i]);
    }
}



