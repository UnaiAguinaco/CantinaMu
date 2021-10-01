var stompClient = null;
//var mesas = document.querySelectorAll(".mesa");
var roomId;

window.onload= function(){
    var socket = new SockJS('/cantineWSserver');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    
}

function onConnected() {
    // Subscribe to the Public Topic
    var room = document.querySelectorAll(".reserveIdRoom")[0].value;
    var bulding = document.querySelectorAll(".reserveIdBuilding")[0].value;
    var date = document.querySelectorAll(".reserveTime")[0].value;
    roomId = room+" "+bulding+" "+date;
   stompClient.subscribe(`/room/${roomId}`,onMessageRecived);
   enterToRoom();
}


function onError(error) {
    console.log("WebSocket Connection Error");

}

function disconnect() {
  stompClient.send(`/app/disconnect`, {}, JSON.stringify({
        type: " ",
        roomId : roomId,
        deskStatus : [0,0],
        row: document.querySelector("#rows").innerText,
        col: document.querySelector("#cols").innerText,
        users: 0

    }));
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}


function enterToRoom() {
    stompClient.send(`/app/enter/${roomId}`, {}, JSON.stringify({
        type: "JOIN",
        roomId : roomId,
        deskStatus : [0,0],
        row: document.querySelector("#rows").innerText,
        col: document.querySelector("#cols").innerText,
        users: 0

    }));
}
function changeDesks() {
 var desks= [];
    mesas.forEach(m => {
          desks.push(m.classList[1]);
        });
    stompClient.send(`/app/change/${roomId}`, {}, JSON.stringify({
        type: "CHANGE",
        roomId : roomId,
        deskStatus : desks,
        row: document.querySelector("#rows").innerText,
        col: document.querySelector("#cols").innerText,
        users: 0

    }));
}

function onMessageRecived(payload) {
 
    var message = JSON.parse(payload.body);
    if (message.type=="JOIN") {
      var index = 0;
        mesas.forEach(m => {
          if (m.classList[1]!= message.deskStatus[index]) {
            m.classList.remove(m.classList[1]);
            m.classList.add(message.deskStatus[index]);
          }
          index++;
          
        });
    }
    if (message.type=="CHANGE") {
      var index = 0;
        mesas.forEach(m => {
            m.classList.remove(m.classList[1]);
            m.classList.add(message.deskStatus[index]);         
          index++;
          if(m.classList[1]=="0"){
            m.style.backgroundColor ="green";
          }
          if(m.classList[1]=="1"){
            m.style.backgroundColor ="orange";
          }
          if(m.classList[1]=="2"){
            m.style.backgroundColor ="red";
            
          }
        });
    }
    
}



$(function () {
    $( "#confirmBook" ).click(function() { connect(); });
    $( "#newReservationCnl" ).click(function() { disconnect(); });
    $( "#logo>a" ).click(function() { disconnect(); });
});