document.getElementById('submit-button').addEventListener('click', test);

function test(){
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'TestServlet', true);

    xhr.send();
}