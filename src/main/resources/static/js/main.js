let eye = document.getElementById("eye");
eye.style.display = "none";

eye.addEventListener('click', function () {
     if (this.previousElementSibling.getAttribute('type') == 'password') {
          this.previousElementSibling.setAttribute('type', 'text');
          this.classList.toggle('fa-eye-slash');
          this.classList.toggle('fa-eye');
     } else {
          this.previousElementSibling.setAttribute('type', 'password');
          this.classList.toggle('fa-eye-slash');
          this.classList.toggle('fa-eye');
     }
})

function focusFunction() {
    eye.style.display = "block";
}