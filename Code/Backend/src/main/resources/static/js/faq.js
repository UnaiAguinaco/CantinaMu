  const newQuestion = document.querySelectorAll(".createNewFaq");
for(let btn of newQuestion) {
      btn.addEventListener('click', newQuestionForm);
  }
function newQuestionForm() {
  document.getElementById("faqContainer").classList.add("hide");
  document.getElementById("faqForm").classList.remove("hide");
}
document.getElementById("newFaqCnl").addEventListener('click', returnToQuestionList);
document.getElementById("newFaqAcp").addEventListener('click', returnToQuestionList);
function returnToQuestionList() {
  document.getElementById("faqContainer").classList.remove("hide");
  document.getElementById("faqForm").classList.add("hide");
}