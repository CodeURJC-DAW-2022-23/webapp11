function tooglePW(){
    var password = document.querySelector('[name=password]');
    if (password.getAttribute('type')==='password'){
        password.setAttribute('type', 'text');
        document.getElementById("togglePassword").addClass("fa-solid fa-eye").removeClass("fa-solid fa-eye-slash");
    }else{
        password.setAttribute('type', 'password');
        document.getElementById("togglePassword").addClass("fa-solid fa-eye-slash").removeClass("fa-solid fa-eye");
    }
}