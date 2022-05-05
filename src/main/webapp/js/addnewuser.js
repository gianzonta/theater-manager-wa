
$("#newMemberForm").submit(function (e) {

    e.preventDefault();

    var newMemberForm = document.getElementById('newMemberForm')

    var fData = new FormData(newMemberForm);

    x = {};
    for (let [name, value] of fData) {
        x[name] = value;
    }

    var jsonData = JSON.stringify(x);
    jsonData = '{ "member": ' + jsonData + "}";

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
                submit_dep();
                var jsonData = JSON.parse(xhreq.responseText);
                var displayData = "The new member <strong> " + jsonData.member.username + " </strong>has been added to the system";
                //var newUrl =  "activityview?activityid=" + jsonData.activity.activityID;
                addSuccessMessage(displayData);
                
            } else {
                addErrorMessage(JSON.parse(xhreq.responseText).message.message);

            }
        }
    };

});


function get_dep_list() {
    var httpRequest;
    var url = "./content/department";
    httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = listActivities;
    httpRequest.open('GET', url);
    httpRequest.send();

    function listActivities() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {

                var to_fill = document.getElementById('dep_list');

                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData['resource-list'];

                for (var i = 0; i < resource.length; i++) {
                    var department = resource[i].department;

                    var row = document.createElement('tr');
                    row.innerHTML = '<td><label><input type="checkbox" id="' + department['name'] + '" name="' + department['name'] + '" value="" class="flat dep_checkbox">' + department['name'] + '</label><td>';
                    to_fill.appendChild(row);
                }
                $('.dep_checkbox').iCheck({checkboxClass: 'icheckbox_flat-green'});
            } else {
                alert('There was a problem retrieving the department list.');
            }
        }
    }
}


function submit_dep() {
    var newMemberForm = document.getElementById('newMemberForm')
    var username = newMemberForm.username.value;
    var form = document.getElementById('user2dep_form')
    var fData = new FormData(form);
    var deplist = "";
    for (let name of fData) {
        deplist = deplist + name;
    }
    deplist = deplist.substring(0, deplist.length - 1);
    //TRADITIONAL AJAX CALL

    var url = "./content/member/" + username + "/ispartof";
    var xhreq = new XMLHttpRequest();
    xhreq.onreadystatechange = alertState;

    xhreq.open('PUT', url);
    xhreq.setRequestHeader('Content-type', 'application/json');
    xhreq.setRequestHeader('Data-type', 'application/json');
    xhreq.send(deplist);

    function alertState() {
        if (xhreq.readyState === XMLHttpRequest.DONE) {
            if (xhreq.status == 200) {
                var jsonData = xhreq.responseText;
                var displayData = "The user has been assigned to departments: "+deplist;

                addSuccessMessage(displayData);
            } else {
                addErrorMessage(JSON.parse(xhreq.responseText).message.message);
            }
        }
    };
}