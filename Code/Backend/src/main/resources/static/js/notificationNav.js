
const rightNav = document.querySelector(".notificationBar");
const bell = document.getElementById("bell");
if (bell!=null) {
  bell.addEventListener("click", function showSideNav(params) {
  rightNav.classList.toggle("hide");
})
}
