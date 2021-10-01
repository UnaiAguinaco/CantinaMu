window.onload = function(){

document.getElementById("changePassBtn").addEventListener('click', settingForm); 
function settingForm() {
  document.getElementById("userData").classList.add("hide");
  document.getElementById("changePassForm").classList.remove("hide");
}
document.getElementById("confPass").addEventListener('click', submitForm);
document.getElementById("cnclPass").addEventListener('click', returnToSettings);
function submitForm() {
  document.querySelector("#formChangePass").submit();
  returnToSettings(); 
}

function returnToSettings() {
  document.getElementById("userData").classList.remove("hide");
  document.getElementById("changePassForm").classList.add("hide");
}
 


}

