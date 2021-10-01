  var cols=document.getElementById("cols").innerHTML;
  var rows=document.getElementById("rows").innerHTML;
  const tabla = document.querySelector("#reserva");

  var dimX = (tabla.clientWidth/cols)-32;
  var dimY = (tabla.clientHeight/rows)-16;
  tabla.style.setProperty('--grid-rows', rows);
  tabla.style.setProperty('--grid-cols', cols);
  const mesas = document.querySelectorAll("#reserva>div");
  mesas.forEach(mesa => {
    mesa.addEventListener("click", select);
    if(mesa.classList.contains("0")){
      mesa.style.backgroundColor ="green";
    }
    if(mesa.classList.contains("-1")){
      mesa.style.backgroundColor ="white";
    }

  });
  function select(event){
    if(event.currentTarget.classList.contains("0")){
      event.currentTarget.classList.remove("0");
      event.currentTarget.classList.add("-1");
      event.currentTarget.style.backgroundColor ="white";
      var icon = document.createElement('i');
      icon.innerHTML='<i class="fas fa-ban fa-4x p-0"></i>';     
      event.currentTarget.appendChild(icon)
      event.currentTarget.querySelector(".typeInput").value="-1";
    }
    else if(event.currentTarget.classList.contains("-1")){
      event.currentTarget.classList.remove("-1");
      event.currentTarget.classList.add("0");
      event.currentTarget.style.backgroundColor ="green";
      event.currentTarget.removeChild(event.currentTarget.querySelector("i")); 
      event.currentTarget.querySelector(".typeInput").value="0";
    }
  }