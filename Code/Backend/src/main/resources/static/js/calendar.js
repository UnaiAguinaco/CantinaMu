

var today = new Date();
var dd = today.getDate();
var mm = today.getMonth(); 
var yyyy = today.getFullYear();
var FirstDay = new Date(yyyy, mm, 1);
var LastDay = new Date(yyyy, mm+1, 0);
var toChange=document.querySelector("#calendarlist");
var lastDay=LastDay.getDate();
 let numb=lastDay-(FirstDay.getDay()-2);
 var addEvents=false;
 for(let i=0;i<35;i++){
  if(numb>lastDay){
    numb=1
    addEvents=true;
  }
  var div=document.createElement('div');
  div.className="date date"+numb;
  div.innerText=numb;
  
var calendarTitle=document.querySelector("#calendarTitle");

var month;
switch (mm) {
  case 0:
    month="January";
    break;
  case 1:
    month="February";
    break;
  case 2:
    month="March";
    break;
  case 3:
    month="April";
    break;
  case 4:
    month="May"; 
    break;
  case 5:
    month="June";
    break;
  case 6:
    month="July";
    break;
  case 7:
    month="August";
    break;
  case 8:
    month="September";
    break;
  case 9:
    month="October";
    break;
  case 10:
    month="November";
    break;
  case 11:
    month="December";
    break;

  default:
    break;
}
document.querySelector("#left-day").innerText=dd+" of "+month+" "+yyyy;
document.querySelector("#left-day-small").innerText=dd+" of "+month+" "+yyyy;
document.querySelector('.date'+dd).parentElement.click();
calendarTitle.innerText=month+" "+yyyy;


 }
