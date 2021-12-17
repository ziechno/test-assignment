window.onload = function(){
    document.getElementById("date-input").value = new Date('MMM DD YYY');
}

var jsonData;

$(document).ready(function() {
    $('form').on('submit', function(e){
        e.preventDefault();
    });
  });
  

function fetch() {
    var symbols = document.getElementById("symbol-input").value;
    var date = document.getElementById("date-input").value;
    $.ajax({
        type: 'GET',
        url: "TestServlet",
        data: {
            symbols: symbols,
            date: date
        },
        success: function(data) {
            var tableBody = document.getElementById("table-body");
            var tableData = '';
        
            data.forEach(element => {
                tableData += '<th scope="row">'+element.ticker.symbol+'</th>'
                + '<td>'+element.ticker.fullName+'</td>'
                + '<td>'+element.ticker.yearFounded+'</td>'
                + '<td>'+element.ticker.state+'</td>'
                + '<td>'+element.ticker.city+'</td>';
                element.dataEntries.forEach(element => {
                    tableData += '<td>'+element.openPrice+'</td>'
                    + '<td>'+element.closePrice+'</td>'
                    + '<td>'+element.timestamp+'</td>';
                });
                tableBody.innerHTML = tableData;
                tableData = '';
            });
            
        }
    });
}

function loadData(data) {
    var tableBody = window.getElementById("table-body");
    var tableData = '';

    data.forEach(element => {
        tableData += '<th scope="row">element.ticker.symbol</th>'
        + '<td>element.ticker.fullName</td>'
        + '<td>element.ticker.state</td>'
        + '<td>element.ticker.city</td>'
        + '<td>element.ticker.yearFounded</td>';
        element.dataEntries.forEach(element => {
            tableData +=  + '<td>Apple Inc.</td>'
            + '<td>element.openPrice</td>'
            + '<td>element.closePrice</td>'
            + '<td>element.timestamp</td>';
        });
             
    });
    tableBody.innerHTML = tableData;
}
