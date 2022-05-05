function ajax_userrole_list() {

    var httpRequest;

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }

    var username = (new URLSearchParams(window.location.search)).get('username');
    document.getElementById("username").value = username;
    var url = "./content/member/" + username + "/userrole";

    httpRequest.onreadystatechange = alertContents;
    httpRequest.open('GET', url);
    httpRequest.send();

    function alertContents() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {

                var tbody = document.getElementById('userrole_list');
                tbody.textContent = '';
                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData['resource-list'];

                for (var i = 0; i < resource.length; i++) {
                    var userrole = resource[i].userrole;
                    var tr = document.createElement('tr');

                    var td_name = document.createElement('td');
                    tr.setAttribute("seasonID",userrole['seasonID'])
                    td_name.appendChild(document.createTextNode(userrole['seasonID']));
                    tr.appendChild(td_name);

                    var td_name = document.createElement('td');
                    tr.setAttribute("playID",userrole['playID']);
                    td_name.appendChild(document.createTextNode(window.play_map[userrole['playID']]));
                    tr.appendChild(td_name);

                    var td_surname = document.createElement('td');
                    tr.setAttribute("name",userrole['name']);
                    td_surname.appendChild(document.createTextNode(userrole['name']));
                    tr.appendChild(td_surname);

                    var td_birthDate = document.createElement('td');
                    tr.setAttribute("role",userrole['role']);
                    td_birthDate.appendChild(document.createTextNode(userrole['role']));
                    tr.appendChild(td_birthDate);

                    tr.addEventListener("click",function(){populateForm(this)});
                    tbody.appendChild(tr);
                }
                //tbody.refresh();
            } else {
                alert('There was a problem with the request.');
                console.log(httpRequest.responseText);
                console.log(httpRequest.status);
            }
        }
    }
}

function fill_dep_selector() {
    var httpRequest;

    var url = "./content/department";

    httpRequest = new XMLHttpRequest();

    httpRequest.onreadystatechange = listDepartments;
    httpRequest.open('GET', url);
    httpRequest.send();

    function listDepartments() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {

                var relAct = document.getElementById('task_selector');

                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData['resource-list'];
                
                var map = {};

                for (var i = 0; i < resource.length; i++) {
                    var department = resource[i].department;

                    var option = document.createElement('option');
                    option.value = department['name'];
                    option.appendChild(document.createTextNode(department['name']));
                    relAct.appendChild(option);

                }

            } else {
                alert('There was a problem retrieving the department list.');
            }
        }
    }
}



function fill_play_selector() {

    let httpRequest;
    let url = "./content/play";
    window.play_map = {};

    httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = listPlays;
    httpRequest.open('GET', url);
    httpRequest.send();

    function listPlays() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {

                let relAct = document.getElementById('play_list');

                let jsonData = JSON.parse(httpRequest.responseText);
                let resource = jsonData['resource-list'];

                for (i = 0; i < resource.length; i++) {
                    let play = resource[i].play;
                    window.play_map[play['playID']] = play['title'];
                    let option = document.createElement('option');
                    option.value = play['playID'];
                    option.appendChild(document.createTextNode(play['title']));
                    relAct.appendChild(option);

                }
                ajax_userrole_list();
            } else {
                alert('There was a problem retrieving the play list.');
            }
        }
    }
}



$("#addroleform").submit(function (e) {

    e.preventDefault();

    var form = document.getElementById('addroleform')

    var fData = new FormData(form);

    x = {};
    for (let [name, value] of fData) {
        x[name] = `${value}`;
    }

    var jsonData = JSON.stringify(x);
    jsonData = '{ "userrole": ' + jsonData + "}";
    console.log(jsonData);
    //TRADITIONAL AJAX CALL

    var url = "./content/member/" + form.username.value + "/userrole";

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
                var displayData = "The user role has been added to the system";
                addSuccessMessage(displayData);
                ajax_userrole_list();
               // document.getElementById("userrole_list").refresh();
            } else {
                addErrorMessage(xhreq.responseText);

            }
        }
    };

});


function delete_userrole() {

    var form = document.getElementById('addroleform')

    var fData = new FormData(form);

    x = {};
    for (let [name, value] of fData) {
        //x[name] = `${value}`; 
        x[name] = value;
    }

    var jsonData = JSON.stringify(x);
    jsonData = '{ "userrole": ' + jsonData + "}";
    console.log(jsonData);

    var url = "./content/member/" + document.getElementById('username').value + "/userrole";
    var xhreq = new XMLHttpRequest();
    xhreq.onreadystatechange = alertState;
    xhreq.open('DELETE', url);
    xhreq.setRequestHeader('Content-type', 'application/json');
    xhreq.setRequestHeader('Data-type', 'application/json');
    xhreq.send(jsonData);
    function alertState() {
        if (xhreq.readyState === XMLHttpRequest.DONE) {
            if (xhreq.status == 200) {
                var jsonData = JSON.parse(xhreq.responseText);
                var displayData = "The userrole has been removed from the system";

                console.log(jsonData);
                addSuccessMessage(displayData);
                //location.reload(true);
                ajax_userrole_list();
               // document.getElementById("userrole_list").refresh();
            } else {
                addErrorMessage(xhreq.responseText);
                var jsonData = JSON.parse(xhreq.responseText);
                console.log(jsonData);
            }
        }
    };

}





function populateForm(row) {
    form = document.getElementById("addroleform");
    form.name.value=row.getAttribute("name");
    form.seasonID.value=row.getAttribute("seasonID");
    form.playID.value=row.getAttribute("playID");
    form.role.value=row.getAttribute("role");
}

