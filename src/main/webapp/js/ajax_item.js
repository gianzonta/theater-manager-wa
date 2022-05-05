
  var httpRequest;

    var url = "./content/item";

    httpRequest = new XMLHttpRequest();

    httpRequest.onreadystatechange = listItems;
    httpRequest.open('GET', url);
    httpRequest.send();

    // var page = document.getElementById("item_page"); //alvays executed, may be a problem
    // page.addEventListener("click",clearInfoTab);



  function listItems() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
      
      if (httpRequest.status == 200) {


        var tbody = document.getElementById('item_table');

        var jsonData = JSON.parse(httpRequest.responseText);
        var resource = jsonData['resource-list'];

        for (var i = 0; i < resource.length; i++) {
          var item = resource[i].item;

          var tr = document.createElement('tr');

          tr.href = './itemdetail?itemid='+item['itemID'];

          var td_name = document.createElement('td');
          td_name.appendChild(document.createTextNode(item['name']));
          tr.appendChild(td_name);

          var td_description = document.createElement('td');
          td_description.appendChild(document.createTextNode(item['description']));
          tr.appendChild(td_description);

          var td_quantity = document.createElement('td');
          td_quantity.appendChild(document.createTextNode(item['quantity']));
          tr.appendChild(td_quantity);

          var td_size = document.createElement('td');
          td_size.appendChild(document.createTextNode(item['size']));
          tr.appendChild(td_size);

          var td_historicalgenre = document.createElement('td');
          td_historicalgenre.appendChild(document.createTextNode(item['historicalGenre']));
          tr.appendChild(td_historicalgenre);

          var td_image = document.createElement('td');
          td_image.appendChild(document.createTextNode(item['image']));
          tr.appendChild(td_image);

          var td_username = document.createElement('td');
          td_username.appendChild(document.createTextNode(item['username']));
          tr.appendChild(td_username);

          var td_edit = document.createElement('td');

          var a = document.createElement('a');
          a.href = "edititem?itemid=" + item['itemID']


          var edit_button = document.createElement('button');
          edit_button.className = "btn btn-primary small-tab-btn";
          edit_button.type="button";
          edit_button.innerHTML= 'Edit';

          a.appendChild(edit_button);
          td_edit.appendChild(a);
          tr.appendChild(td_edit);

          tr.className = "itemsEntry";
          tr.id= item['itemID'];

     //     tr.addEventListener("click",openDetails);
       //   tr.addEventListener("click",openActivityDetails);
          tr.addEventListener("click",function(){window.location.href=this.href});
          tbody.appendChild(tr);

        }


      } else {
        alert('There was a problem with the request.');
      }
    }
  }