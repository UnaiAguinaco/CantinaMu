const sideNav = document.querySelector(".sideBar");
const navButton = document.getElementById("asideButton");
if (navButton!=null) {
  navButton.addEventListener("click", function showSideNav(params) {
  sideNav.classList.toggle("hide");
})
}