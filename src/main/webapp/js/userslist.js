
    var httpRequest;
  
    var url = "./content/member";
  
    httpRequest = new XMLHttpRequest();
  
    if (!httpRequest) {
      //alert('Giving up :( Cannot create an XMLHTTP instance');
      throw new Error("Something went badly wrong!");
    }
  
    httpRequest.onreadystatechange = function(){if (document.readyState === 'interactive' || document.readyState === 'complete') {alertContents();} else {document.addEventListener( "DOMContentLoaded",alertContents)};};
    httpRequest.open('GET', url);
    httpRequest.send();
  
    function alertContents() {
      if (httpRequest.readyState === XMLHttpRequest.DONE) {
  
        if (httpRequest.status == 200) {
  
  
          var tbody = document.getElementById('userlist_all_table');
  
          var jsonData = JSON.parse(httpRequest.responseText);
          var resource = jsonData['resource-list'];
  
          for (var i = 0; i < resource.length; i++) {
            var member = resource[i].member;
            var tr = document.createElement('tr');
  
            tr.href = './adminedituser?username='+member['username'];
  
            var td_name = document.createElement('td');
            td_name.appendChild(document.createTextNode(member['username']));
            tr.appendChild(td_name);
  
            var td_name = document.createElement('td');
            td_name.appendChild(document.createTextNode(member['name']));
            tr.appendChild(td_name);
  
            var td_surname = document.createElement('td');
            td_surname.appendChild(document.createTextNode(member['surname']));
            tr.appendChild(td_surname);
  
            var td_birthDate = document.createElement('td');
            td_birthDate.appendChild(document.createTextNode(member['birthDate']));
            tr.appendChild(td_birthDate);
  
            var td_email = document.createElement('td');
            td_email.appendChild(document.createTextNode(member['email']));
            tr.appendChild(td_email);
  
            var td_phone = document.createElement('td');
            td_phone.appendChild(document.createTextNode(member['phoneNumber']));
            tr.appendChild(td_phone);
  
            var td_isUnipd = document.createElement('td');
            td_isUnipd.appendChild(document.createTextNode(member['isUnipdStudent'] ? "\u2714" : "\u274C"));
            tr.appendChild(td_isUnipd);
  
            var td_isPro = document.createElement('td');
            td_isPro.appendChild(document.createTextNode(member['isMemberPro'] ? "\u2714" : "\u274C"));
            tr.appendChild(td_isPro);
  
            var Plays = document.createElement('td');
            
            var button = document.createElement("button");
            button.setAttribute("class","btn btn-primary small-tab-btn");
            button.setAttribute("type","button");
            button.appendChild(document.createTextNode("Plays"));
            var link = document.createElement("a");
            link.setAttribute("href","user2play?username="+member['username']);
            link.appendChild(button);
            Plays.appendChild(link);
  
            tr.appendChild(Plays);
  
            tr.addEventListener("click",function(){window.location.href=this.href});
           
            tbody.appendChild(tr);
          }
  
          $('#members_table').DataTable();
        } else if(httpRequest.status == 403) 
          {
            let message = JSON.parse(httpRequest.responseText).message.message;
            alert(message);
          }
        
        
        else {
          alert('There was a problem with the request.');
        }
      }
      
    }
  