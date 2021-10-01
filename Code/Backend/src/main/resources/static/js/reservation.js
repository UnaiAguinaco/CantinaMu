  var cols=document.getElementById("cols").innerHTML;
  var rows=document.getElementById("rows").innerHTML;
  const tabla = document.querySelector("#reserva");

  var dimX = (tabla.clientWidth/cols)-32;
  var dimY = (tabla.clientHeight/rows)-16;
  tabla.style.setProperty('--grid-rows', rows);
  tabla.style.setProperty('--grid-cols', cols);
  const mesas = document.querySelectorAll("#reserva>div");
  mesas.forEach(mesa => {
   
    if(mesa.classList.contains("0")){
      mesa.style.backgroundColor ="green";
      mesa.addEventListener("click", select);
    }
    if(mesa.classList.contains("-1")){
      mesa.style.backgroundColor ="white";
      var icon = document.createElement('i');
      icon.innerHTML='<i class="fas fa-ban fa-4x p-0"></i>';     
      mesa.appendChild(icon)
    }
    if(mesa.classList.contains("2")){
      mesa.style.backgroundColor ="red";
      
    }

  });
  function select(event){
    if(event.currentTarget.classList.contains("0")){
      event.currentTarget.classList.remove("0");
      event.currentTarget.classList.add("1");
      changeDesks();
      event.currentTarget.style.backgroundColor ="orange";
      if (confirm("Do you want to book this desk?") == true) {
			event.currentTarget.classList.remove("1");
      event.currentTarget.classList.add("2");
      event.currentTarget.style.backgroundColor ="red";
      changeDesks();
      document.getElementById("roomMap").classList.remove("show");
      document.getElementById("roomMap").classList.add("hide");
      document.getElementById("chargeLogo").classList.remove("hide");
      document.getElementById("chargeLogo").classList.add("show");      
      document.getElementById("newReservationCnl").classList.add("hide");
      document.querySelector("#reservedDesk").value=event.currentTarget.querySelector("input").value;
      document.querySelector("#saveReservation").submit();
      
      disconnect();
		} else {
			event.currentTarget.classList.remove("1");
      event.currentTarget.classList.add("0");
      event.currentTarget.style.backgroundColor ="green";
      changeDesks();
		}
    }
    
  }