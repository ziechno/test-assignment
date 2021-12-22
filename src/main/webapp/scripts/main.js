$(document).ready(function() {
    $('form').on('submit', function(e){
        e.preventDefault();
    });
  });

$("#date-input").ready(function (){
    let currentDate = moment().format("yyyy-MM-DD");
    let minDate = moment("1970-01-01").format("yyyy-MM-DD");
    document.getElementById("date-input").setAttribute("value", currentDate);
    document.getElementById("date-input").setAttribute("min", minDate);
    document.getElementById("date-input").setAttribute("max", currentDate);
})

$("#submit-button").click(function (){
    verifyInput();
})

function verifyInput(){
    let alertDisplay = document.getElementById("inputAlert");
    alertDisplay.setAttribute("style", "display: none")
    let test = false;
    let symbolInput = document.getElementById("symbol-input").value;
    let dateInput = document.getElementById("date-input").value;
    dateInput = moment(dateInput).format("yyyy-MM-DD");
    let unixDateStart = moment("1970-01-01").format("yyyy-MM-DD");
    let currentDate = moment().format("yyyy-MM-DD");
    if(symbolInput == ''){
        alertDisplay.setAttribute("style", "display: block");
    }
    else if((dateInput < unixDateStart) || (dateInput > currentDate)){
        alertDisplay.setAttribute("style", "display: block");
    }
    else{
        test = true;
    }
    if(test == true){
        fetch();
    }
}

function fetch() {
    let symbols = document.getElementById("symbol-input").value;
    let date = document.getElementById("date-input").value;
    document.getElementById("tableContainer").setAttribute("style", "display: none");
    $.ajax({
        type: 'GET',
        url: "TestServlet",
        data: {
            symbols: symbols,
            date: date
        },
        success: function (data){
            fillTable(data);
        }
    });
}

function fillTable(data) {
    let tableBody = document.getElementById("table-body");
    let tableData = '';

    data.forEach(element => {
        tableData += '<tr>'
            + '<th scope="row">'+element.ticker.symbol+'</th>'
            + '<td>'+element.ticker.fullName+'</td>'
            + '<td>'+element.ticker.yearFounded+'</td>'
            + '<td>'+element.ticker.employeeNumber+'</td>'
            + '<td>'+element.ticker.city+'</td>'
            + '<td>'+element.ticker.state+'</td>'
            + '<td>'+element.ticker.marketCap+'</td>'
            + '<td>'+element.closePrice+'</td>'
            + '<td>'+element.openPrice+'</td>'
            + '<td>'+element.timestamp+'</td></tr>';
        tableBody.innerHTML = tableData;
        document.getElementById("tableContainer").setAttribute("style", "display: block");
    });
}

