

  var today = new Date();
let day;
document.onreadystatechange = function () {
  if (document.readyState != "complete" && document.getElementById("wait") != null) {
    document.getElementById("wait").style.display = "inline";
  }

window.onload = function(){
  let days = document.querySelectorAll(".days>li");
  var currentMonth = false;
  for (let day of days) {
    if (day.getElementsByTagName("div")[0].classList[1]=="date1") {
      currentMonth=!currentMonth;
    }
    if (currentMonth) {
      if (today.getDate()<=day.getElementsByTagName("div")[0].classList[1].split("date", 2)[1]) {     
      day.addEventListener('click', updateMain);
    }
    }
    
  }
  let smallDays = document.querySelectorAll("#smallCalendarlist>li");
  currentMonth = false;
  for (let day of smallDays) {
    if (day.getElementsByTagName("div")[0].classList[0]=="smallDate1") {
      currentMonth=!currentMonth;
    }
    if (!currentMonth) {
      day.querySelector(".smallBookBtn").remove();
    }
    if (currentMonth) {
      if (today.getDate()>day.getElementsByTagName("div")[0].classList[0].split("smallDate", 2)[1]) {   
       if (day.querySelector(".smallBookBtn")!=null) {
         
         day.querySelector(".smallBookBtn").remove();
       }
    }
  }
    
  }
   getBuildingRooms();
document.querySelector('.date'+dd).parentElement.click();
}
/*Change the view for the calendar*/
let toChange;
let myDiv;

function updateMain(event){
  let text=event.currentTarget.querySelector('.date').textContent.replace(/[\n\r]+|[\s]{2,}/g, ' ').trim(); 
  let left=document.getElementById("left-day");
  left.innerHTML=text + " of " + document.querySelector('.month').textContent.replace(/[\n\r]+|[\s]{2,}/g, ' ').trim();
  day=left.innerHTML;
  var events=event.currentTarget.querySelectorAll('.event')
  myDiv=document.getElementById("aside_event");
  myDiv.innerHTML='';
  if(day==null){
    var dd = today.getDate();
    toChange=document.querySelector('.date'+dd).parentElement;
  }else{
    toChange=document.querySelector('.date'+day.substring(0,2)).parentElement;
  }
  
  if(toChange.children.length>1){
    document.querySelector('#bookBtn').style.display="none";
  }else{
    document.querySelector('#bookBtn').style.display="inherit";
  }
  for(let ev of events){
      myDiv.innerHTML+='<div value='+ev.attributes[0].value.split(" ",4)[0]+' class="pl-4 pt-4"><p class="text-white h4 mb-3">'+ev.textContent.replace(/[\n\r]+|[\s]{2,}/g, ' ').trim()+'</p><br><p class="text-white">Número de eficio: '+ev.attributes[0].value.split(" ",4)[1]+'</p><br><p class="text-white">Número de sala: '+ev.attributes[0].value.split(" ",4)[2]+'</p><div class="d-flex justify-content-around"><button id="left-view" class="button-edit">Ver</button><button id="left-edit" class="button-edit">Editar</button><button id="left-cancel" class="button-cancel">Borrar</button></div></div>';
      
    }
    if(toChange.children.length>1){
    document.getElementById("left-cancel").addEventListener("click", leftCancel);
    document.getElementById("left-edit").addEventListener("click", leftEdit);
    document.getElementById("left-view").addEventListener("click", leftView);
    }
  
}
function leftCancel(){
  document.querySelector("#cancelReservation"+document.querySelector("#aside_event>div").attributes.value.value).submit();
}
function leftEdit(){
  document.querySelector("#editReservation"+document.querySelector("#aside_event>div").attributes.value.value).submit();
}
function leftView(){
  document.querySelector("#showReservation"+document.querySelector("#aside_event>div").attributes.value.value).submit();
}
/*Display the book details*/
document.getElementById("bookBtn").addEventListener("click", change);
function change() {
  document.getElementById("roomSelect").classList.remove("hide");
  document.getElementById("roomSelect").classList.add("show");
  document.getElementById("myCalendar").classList.remove("show");
  document.getElementById("myCalendar").classList.add("hide");
  
  document.getElementById("smallDeviceCalendar").style.display="none";

}
/*Display the book details*/
var smallBookBtn = document.querySelectorAll(".smallBookBtn");
smallBookBtn.forEach(element => {
  element.addEventListener("click", changeS);

});
function changeS(e) {
  document.getElementById("roomSelect").classList.remove("hide");
  document.getElementById("roomSelect").classList.add("show");

  document.getElementById("myCalendar").classList.remove("show");
  document.getElementById("myCalendar").classList.add("hide");

  document.getElementById("smallDeviceCalendar").classList.remove("show");
  document.getElementById("smallDeviceCalendar").classList.add("hide");
  var changeDate= document.getElementById("left-day").innerText.split(" ",5);
document.getElementById("left-day").innerText=e.currentTarget.classList[0]+" "+changeDate[1]+" "+changeDate[2]+" "+changeDate[3];
}

/*Cancel book details */
document.getElementById("cancelBook").addEventListener("click", cancelBook);
function cancelBook() {
  
  document.getElementById("roomSelect").classList.remove("show");
  document.getElementById("roomSelect").classList.add("hide");
  document.getElementById("myCalendar").classList.remove("hide");
  document.getElementById("myCalendar").classList.add("show");
  
  document.getElementById("smallDeviceCalendar").classList.remove("hide");
  document.getElementById("smallDeviceCalendar").classList.add("show");
}


/*AJAX*/
document.querySelector("#sBuilding").addEventListener("change", getBuildingRooms);
document.querySelector("#sRoom").addEventListener("change", getRemaining);
document.querySelector("#sHour").addEventListener("change", getRemaining);
document.querySelector("#confirmBook").addEventListener("click", goReservationPage);


function getRemaining(){
  var bulding = document.querySelector("#sBuilding").options[document.querySelector("#sBuilding").selectedIndex].value;
  var room = document.querySelector("#sRoom").options[document.querySelector("#sRoom").selectedIndex].value;
  var hour = document.querySelector("#sHour").options[document.querySelector("#sHour").selectedIndex].value;
  
  var day = splitDate();
  //document.querySelector("#freeDesksInput").value=bulding+" "+room+" "+ day[3]+"-"+day[2]+"-"+day[0]+" "+ hour;
  var url = "/user/remainingDesks/"+bulding+"-"+room+"-"+ day[3]+"-"+day[2]+"-"+day[0]+"-"+ hour;
  $('#freeDesks').load(url);
}
function getBuildingRooms(){ 
  var bulding = document.querySelector("#sBuilding").options[document.querySelector("#sBuilding").selectedIndex].text;
  var url = "/user/chargeRooms/"+bulding.split(" ", 3)[2];
  $('#sRoom').load(url);
  getRemaining();
}
function goReservationPage(){
    var bulding = document.querySelector("#sBuilding").options[document.querySelector("#sBuilding").selectedIndex].value;
  var room = document.querySelector("#sRoom").options[document.querySelector("#sRoom").selectedIndex].value;
  var hour = document.querySelector("#sHour").options[document.querySelector("#sHour").selectedIndex].value;
 
  var day = splitDate();


 document.getElementById("inputBuilding").value=bulding;  
  document.getElementById("inputRoom").value=room;  

  document.getElementById("inputHour").value= day[3]+"-"+day[2]+"-"+day[0]+" "+ hour; 
  document.querySelector("#reserveFrom").submit();
}
}

function splitDate() {
    var date = document.getElementById("left-day").innerText.split(" ", 4);

 switch (date[2]) {
   case "January":
    date[2]="01";
    break;
  case "February":
    date[2]="02";
    break;
  case "March":
    date[2]="03";
    break;
  case "April":
    date[2]="04";
    break;
  case "May":
    date[2]="05";
    break;
  case "June":
    date[2]="06";
    break;
  case "July":
    date[2]="07";
    break;
  case "August":
    date[2]="08";
    break;
  case "September":
    date[2]="09";
    break;
  case "October":
    date[2]="10";
    break;
  case "November":
    date[2]="11";
    break;
  case "December":
    date[2]="12";
    break;

 
   default:
     break;
 }

 if (date[0]<10) {
   date[0]="0"+date[0];
 }
 return date;
}