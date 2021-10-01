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
    }
    if(mesa.classList.contains("3")){
      mesa.style.backgroundColor ="#014955";
    }
    if(mesa.classList.contains("-1")){
      mesa.style.backgroundColor ="white";
      var icon = document.createElement('i');
      icon.innerHTML='<i class="fas fa-ban fa-4x p-0"></i>';     
      mesa.appendChild(icon)
      mesa.querySelector("p").remove();
    }
    if(mesa.classList.contains("2")){
      mesa.style.backgroundColor ="red";
    }
  });