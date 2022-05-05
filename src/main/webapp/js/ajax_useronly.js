/** 
function ajax_member_list() {
  var httpRequest;

  var url = "./content/member";

  httpRequest = new XMLHttpRequest();

  if (!httpRequest) {
    alert('Giving up :( Cannot create an XMLHTTP instance');
    return false;
  }

  httpRequest.onreadystatechange = alertContents;
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
          tr.setAttribute("class","clickable-row");
          tr.setAttribute("data-href",'./adminedituser?username='+member['username']);
          
          var td_name = document.createElement('td');
          td_name.appendChild(document.createTextNode(member['name']));
          tr.appendChild(td_name);

          var td_surname = document.createElement('td');
          td_surname.appendChild(document.createTextNode(member['surname']));
          tr.appendChild(td_surname);
          
          var td_email = document.createElement('td');
          td_email.appendChild(document.createTextNode(member['email']));
          tr.appendChild(td_email);

          var td_psw = document.createElement('td');
          td_psw.appendChild(document.createTextNode(member['pswHash']));
          tr.appendChild(td_psw);

          var td_phone = document.createElement('td');
          td_phone.appendChild(document.createTextNode(member['phoneNumber']));
          tr.appendChild(td_phone);

          var Depts = document.createElement('td');
          Depts.appendChild(document.createTextNode("Depts"));
          tr.appendChild(Depts);

          var td_photo = document.createElement('td');
          td_photo.appendChild(document.createTextNode("photo"));
          tr.appendChild(td_photo);

          tbody.appendChild(tr);
        }


      } else {
        alert('There was a problem with the request.');
      }
    }
  }
}


$("#newMemberForm").submit(function(e) {

  e.preventDefault();
  
  var newMemberForm = document.getElementById('newMemberForm')

  var fData = new FormData(newMemberForm);

  x={};
  for(let [name, value] of fData) {
      x[name] = `${value}`; 
    }

  var jsonData = JSON.stringify(x);
  jsonData = '{ "member": ' + jsonData + "}";
    console.log(jsonData);
  //TRADITIONAL AJAX CALL

  var url = "./content/member";

  var xhreq = new XMLHttpRequest();
  xhreq.onreadystatechange = alertState;

  xhreq.open('POST', url);
  xhreq.setRequestHeader('Content-type', 'application/json');
  xhreq.setRequestHeader('Data-type', 'application/json');
  xhreq.send(jsonData);

  function alertState() {
      if (xhreq.readyState === XMLHttpRequest.DONE) {
          if (xhreq.status == 201) {
              var jsonData = JSON.parse(xhreq.responseText);
              var displayData = "The transaction <strong> " + jsonData.member.username + " </strong>has been added to the system";
              //var newUrl =  "activityview?activityid=" + jsonData.activity.activityID;
              console.log(jsonData);
              addSuccessMessage(displayData);
          } else {
              addErrorMessage();
              var jsonData = JSON.parse(xhreq.responseText);
              console.log(jsonData);
          }
      }
  };

});
*/



function load_member_data() {

var username = (new URLSearchParams(window.location.search)).get('username');
frm = document.getElementById('adminEditMemberForm');

var httpRequest;
var url = "./content/member/"+username;
httpRequest = new XMLHttpRequest();
if (!httpRequest) {
  alert('Giving up :( Cannot create an XMLHTTP instance');
  return false;
}

httpRequest.onreadystatechange = alertContents;
httpRequest.open('GET', url);
httpRequest.send();

function alertContents() {
  if (httpRequest.readyState === XMLHttpRequest.DONE) {

    if (httpRequest.status == 200) {

      var jsonData = JSON.parse(httpRequest.responseText);
      var member = jsonData['member'];
      populateForm(frm,member);
    } else {
      alert('There was a problem with the request.');
      console.log(httpRequest.responseText);
      console.log(httpRequest.status);
    }
  }
}

function populateForm(frm, data) {

  $.each(data, function(key, value) {
      var ctrl = $("[name=" + key + "]", frm);
      switch (ctrl.prop("type")) {
          case "radio":
              if (
                  ctrl.parent().hasClass("icheck-primary") ||
                  ctrl.parent().hasClass("icheck-danger") ||
                  ctrl.parent().hasClass("icheck-success")
              ) {
                  
                  $.each(ctrl, function(ctrlKey, radioElem) {
                      radioElem = $(radioElem);


                      if (radioElem.attr("value") == value) {
                          radioElem.iCheck("check");
                      } else {
                          radioElem.iCheck("uncheck");
                      }
                  });
              } else {
                  $.each(ctrl, function(ctrlKey, radioElem) {
                      radioElem = $(radioElem);


                      if (radioElem.attr("value") == value) {
                          radioElem.attr("checked", value);
                      } else {
                          radioElem.attr("checked", value);
                      }
                  });
              }
              break;

          case "checkbox":
              if (
                  ctrl.parent().hasClass("icheck-primary") ||
                  ctrl.parent().hasClass("icheck-danger") ||
                  ctrl.parent().hasClass("icheck-success") ||
                  ctrl.parent().hasClass("icheckbox_flat-green")
              ) {
                  if (value) {
                      ctrl.iCheck("check");
                  } else {
                      ctrl.iCheck("uncheck");
                  }
              } else {
                  ctrl.removeAttr("checked");
                      
                      if (value) {
                          $(this).attr("checked", "checked")
                      }
                    }             
              break;
          default:
              ctrl.val(value);
      }
  });
}

}
