/*document.getElementById('submit-button').addEventListener('click', test);

function test(){
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'TestServlet', true);

    xhr.send();
}

/*$("#submit-button").click(function(){
    
    $.ajax({
       url:'TestServlet',
       data:{name: symbol},
       type:'get',
       cache:false,
       success:function(data){
          alert(data);
       },
       error:function(){
         alert('error');
       }
    })
})*/



function fetch(){
    var symbols = document.getElementById("symbol-input").value;
    var date = document.getElementById("date-input").value;
    $.ajax({
    type:'Get',
    data:{symbols: symbols,
          date: date
    },
    url:"TestServlet",
    success: function(){
        if(data == "false"){
            alert("success")
        }else{
            alert(fail);
        } 
    }
});
return;
}
