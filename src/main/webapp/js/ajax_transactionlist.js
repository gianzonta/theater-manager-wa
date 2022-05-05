
  var httpRequest;

    var url = "./content/transaction";

    httpRequest = new XMLHttpRequest();
/*
    if (!httpRequest) {
      alert('Giving up :( Cannot create an XMLHTTP instance');
      return false;
    }
*/
    httpRequest.onreadystatechange = listTransactions;
    httpRequest.open('GET', url);
    httpRequest.send();

    var page = document.getElementById("whole_page"); //alvays executed, may be a problem
    page.addEventListener("click",clearInfoTab);



  function listTransactions() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
      
      if (httpRequest.status == 200) {


        var tbody = document.getElementById('transactions_table');

        var jsonData = JSON.parse(httpRequest.responseText);
        var resource = jsonData['resource-list'];

        for (var i = 0; i < resource.length; i++) {
          var transaction = resource[i].transaction;

          var tr = document.createElement('tr');

          var td_name = document.createElement('td');
          td_name.appendChild(document.createTextNode(transaction['name']));
          tr.appendChild(td_name);

          var td_amount = document.createElement('td');
          td_amount.appendChild(document.createTextNode(transaction['amount']));
          tr.appendChild(td_amount);

          var td_date = document.createElement('td');
          td_date.appendChild(document.createTextNode(transaction['date']));
          tr.appendChild(td_date);

          var td_username = document.createElement('td');
          td_username.appendChild(document.createTextNode(transaction['username']));
          tr.appendChild(td_username);

          var td_edit = document.createElement('td');

          var a = document.createElement('a');
          a.href = "transactionedit?transid=" + transaction['transID']


          var edit_button = document.createElement('button');
          edit_button.className = "btn btn-primary small-tab-btn";
          edit_button.type="button";
          edit_button.innerHTML= 'Edit';

          a.appendChild(edit_button);
          td_edit.appendChild(a);
          tr.appendChild(td_edit);

          tr.className = "transEntry";
          tr.id= transaction['transID'];

          tr.addEventListener("click",openDetails);
          tr.addEventListener("click",openActivityDetails);
          tr.addEventListener("click",openItemDetails);

          tbody.appendChild(tr);

        }


      } else {
        alert('There was a problem with the request.');
      }
    }
  }

  function openDetails() {
    var httpRequest = new XMLHttpRequest();


    if(!httpRequest) {
      alert('Giving up: cannot create an XML HTTP instance');
      return false;
    }

    httpRequest.onload = addInfo;

    //console.log(this.id); // debug
        

    var requestData = {
        "tID" : this.id, /* will then be adapted to the needs*/
    }
    
    httpRequest.open('GET', './content/transaction/'+ this.id);
    httpRequest.setRequestHeader("Content-Type", "application/json");
    //httpRequest.body = JSON.stringify(requestData);

    httpRequest.send();




    function addInfo() {
  if (httpRequest.readyState == XMLHttpRequest.DONE) {
    if (httpRequest.status==200) {
        showDetails(JSON.parse(httpRequest.responseText));
    }
    else {
      alert("There was a problem with your request");
    }
  }

}

}

function showDetails (data) {

    data = data.transaction;
    document.getElementById("ID").innerHTML = data.transID;
    document.getElementById("name").innerHTML = data.name;
    document.getElementById("date").innerHTML = data.date;
    document.getElementById("amount").innerHTML = data.amount;
    document.getElementById("desc").innerHTML = data.description;
    document.getElementById("relItem").innerHTML = data.relItem;
    if (data.invoice != "") {
      document.getElementById("invoice").innerHTML = "<a href='" + data.invoice + "' target='_blank'>" + "Click here to open" + "</a>";
    } else {
      document.getElementById("invoice").innerHTML = "Invoice not available";
    }
    document.getElementById("mngby").innerHTML = data.username;


}


function clearInfoTab () {
    document.getElementById("ID").innerHTML = "";
    document.getElementById("name").innerHTML = "";
    document.getElementById("date").innerHTML = "";
    document.getElementById("amount").innerHTML = "";
    document.getElementById("desc").innerHTML = "";
    document.getElementById("relAct").innerHTML = "";
    document.getElementById("relItem").innerHTML = "";
    document.getElementById("invoice").innerHTML = "";
    document.getElementById("mngby").innerHTML = "";
}

function openActivityDetails() {


  $.ajax({
      url:"./content/activity/transaction/"+ this.id,
      type: "GET",
      contentType: "application/json",
      //dataType: "application/json",
      success: function(data) {
          loadActivity(data);
      },
      error: function() {
          console.log("Error")
      }, 
  });

  function loadActivity(data) {
      //var jsonData = JSON.parse(data); //data comes already parsed!
      var jsonData = data;
      var resource = jsonData['resource-list'];
      var actText = "";

      if (resource.length == 0) {
          document.getElementById("relAct").innerHTML = "Not defined";
      }else {
          for (var i = 0; i < resource.length; i++) {
              var act = resource[i].activity;
              var actText = actText + "<a href='./activityview?activityid=" + act['activityID'] + "' >" + act['title'] + ", </a>"   
          }
          document.getElementById("relAct").innerHTML = actText;
    }

  }


}

function openItemDetails() {


  $.ajax({
      url:"./content/item/transaction/"+ this.id,
      type: "GET",
      contentType: "application/json",
      //dataType: "application/json",
      success: function(data) {
          loadItem(data);
      },
      error: function() {
          console.log("Error")
      }, 
  });

  function loadItem(data) {
      //var jsonData = JSON.parse(data); //data comes already parsed!
      var jsonData = data;
      var resource = jsonData['resource-list'];
      var actText = "";

      if (resource.length == 0) {
          document.getElementById("relItem").innerHTML = "Not defined";
      }else {
          for (var i = 0; i < resource.length; i++) {
              var item = resource[i].item;
              var actText = actText + "<a href='./itemdetail?itemid=" + item['itemID'] + "' >" + item['name'] + ", </a>"   
          }
          document.getElementById("relItem").innerHTML = actText;
    }

  }


}



