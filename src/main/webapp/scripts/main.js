class FormattedDate{
    constructor(Date) {

        this.date = Date.getDate();
        this.month = Date.getMonth() + 1;
        this.year = Date.getFullYear();
    }

    toString(){
        return this.year + "-" + this.month + "-" + this.date;
    }
}

$(document).ready(function() {
    $('form').on('submit', function(e){
        e.preventDefault();
    });
  });

$("#date-input").ready(function (){
    var unixDateStart = new Date("01-01-1970");
    var currentDate = new Date();
    var minDate = new FormattedDate(unixDateStart);
    var maxDate = new FormattedDate(currentDate);
    document.getElementById("date-input").setAttribute("value", maxDate.toString());
    document.getElementById("date-input").setAttribute("min", "1970-01-01");
    document.getElementById("date-input").setAttribute("max", maxDate.toString());
})

function verifyInput(){
    var alertDisplay = document.getElementById("inputAlert");
    alertDisplay.setAttribute("style", "display: none")
    var test = false;
    var symbolInput = document.getElementById("symbol-input").value;
    var dateInput = document.getElementById("date-input").value;
    dateInput = new Date(dateInput);
    var unixDateStart = new Date("1970-01-01");
    var currentDate = new Date();
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
    var symbols = document.getElementById("symbol-input").value;
    var date = document.getElementById("date-input").value;
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
    var tableBody = document.getElementById("table-body");
    var tableData = '';

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

