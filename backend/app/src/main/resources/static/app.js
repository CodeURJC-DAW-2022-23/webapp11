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
function load(a, b) {
    var arraysize = [];
    var values = [];
    console.log(a);
    const chars = a.substring(1, a.length - 1).split(', ');
    for (i in chars) {
        let num = parseFloat(chars[i]);
        values.push(num);
    }
    console.log("ARRAY:", arraysize);

    Highcharts.chart('container', {
        chart: {
            type: 'line',
            width: 500
        },
        title: {
            text: "Price History for " + b
        },
        xAxix: {
            categories: arraysize
        },
        tooltip: {
            formatter: function () {
                console.log(this);
            }
        },
        series: [{
            data: values.reverse()
        }]
    });
}
