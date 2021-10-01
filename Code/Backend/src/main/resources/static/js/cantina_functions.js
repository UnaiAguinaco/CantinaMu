
var data=[];
var categories=[];

var room1=[];
var numInfections=[];
var infections = [{}, {}, {}];
var numReservations=[];

var reservationPerRoom=[];

window.onload = function () {
    getData();
}


var chart1 = c3.generate({
    bindto: '#chart1', //asociamos al id que hemos creado antes
    data: {
    x:'x',
      columns:[ //le pasamos el dataset
        categories,room1,numInfections],
    type:'bar',
    types: {
        Room1: 'bar',
        numInfections: 'area'
    },
    axes: {
            Room1: 'y',
            numInfections: 'y2'
        }
    },
    axis:{
        x:{ type:'category'},
        y2: {
            show: true
        }
    }    
});


function getData(){
    categories=['x'];
    var f = new Date();
    for(let i=5;i>0;i--){
        cat=f.getDate()-i + "/" + (f.getMonth() + 1) + "/" + f.getFullYear();
        categories.push(cat.toString(10));
    }
    $.ajax({
        url:'/admin/charts/getInfections',
        type:'GET',
        datatype:"json",
        async: "true",
        cache:"false",
        success: function(response){
            numInfections =response.map(function(d){return d.numInfections});
            numInfections=numInfections.reverse();
            infectionTitle=[ 'numInfections'];
            numInfections=infectionTitle.concat(numInfections);
        },
        error:function(){
            alert("Unable to fix the error");
        }
    });
    $.ajax({
        url:'/admin/charts/reservationPerRoom',
        type:'GET',
        datatype:"json",
        async: "true",
        cache:"false",
        success: function(response){
            reservationPerRoom =response.map(function(d){return d.reservationPerRoom});
            for(room of reservationPerRoom){
                id_room=room[0].toString();
                room[0]=id_room;
                room1=room;
                chart1.load({
                    columns:[ //le pasamos el dataset
                        categories,room1,numInfections],
                });
            }
        },
        error:function(){
            alert("Unable to fix the error");
        }
    });
    
    $.ajax({
        url:'/admin/charts/reservation',
        type:'GET',
        datatype:"json",
        async: "true",
        cache:"false",
        success: function(response){
            var numReservations =response.map(function(d){return d.numReservations});
            numReservations=numReservations.reverse();
            reservationTitle=["Reservations"];
            numReservations=reservationTitle.concat(numReservations);
            chart2.load({
                columns: [numReservations],
                unload: numReservations
            });
        },
        error:function(){
            alert("Unable to fix the error");
        }
    });
    $.ajax({
        url:'/admin/charts/infectionPerWeek',
        type:'GET',
        datatype:"json",
        async: "true",
        cache:"false",
        success: function(response){
            infections =response.map(function(d){return d.infectionsPerWeek});
            infections[2]=infections[2]-infections[1];
            infections[1]=infections[1]-infections[0];
        },
        error:function(){
            alert("Unable to fix the error");
        }
    });
}

var chart2 = c3.generate({
    bindto: '#chart2', //asociamos al id que hemos creado antes
    data: {
        columns: [numReservations]
    },
    zoom: {
        enabled: true
    },
    subchart:
    {show:true},
    axis: {
        y: {
            min: 0,
        }
    }

});



var chart3 = c3.generate({
    bindto: '#chart3', //asociamos al id que hemos creado antes
    data: {
        columns: [
            ['Week1', infections[0]]
        ],
        type: 'gauge',
        onclick: function (d, i) { console.log("onclick", d, i); },
        onmouseover: function (d, i) { console.log("onmouseover", d, i); },
        onmouseout: function (d, i) { console.log("onmouseout", d, i); }
    },
    gauge: {
        label: {
            format: function (value) { return value; }
        },
//        
    min: 0, // 0 is default, //can handle negative min e.g. vacuum / voltage / current flow / rate of change
    max: 15, // 100 is default
    },
    color: {
        pattern: ['#014854', '#0289A1', '#F6C600', '#FF0000'], // the three color levels for the percentage values.
        threshold: {
            unit: 'value', // percentage is default
            max: 15, // 100 is default
            values: [5, 8, 11, 13]
        }
    },
    size: {
        height: 180
    }
});
var i=0;
var b, anterior;


function refrescar(){
    anterior=b;
    var week = [{}, {}, {}];
    week[0]="One week ago";
    week[1]="Two weeks ago";
    week[2]="Three weeks ago";
    a=infections[i];
    b=week[i];
    chart3.load({
        columns: [[b, a]],
        unload: ['Week1', anterior],
    });
    chart2.load({
        columns: data,
        unload: data
    });
    i++;
    if(i==3)i=0;
    return 0;
};


setInterval('refrescar()',4000);