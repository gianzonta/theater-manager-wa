function submit_dep() {
    var username = (new URLSearchParams(window.location.search)).get('username');
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
                //addSuccessMessage(displayData);
            } else {
                addErrorMessage(JSON.parse(xhreq.responseText).message.message);

            }
        }
    };
}




function set_dep_list() {
    var checkbox = document.getElementById("dep_list").querySelectorAll("input");
    for (i = 0; i < checkbox.length; i++) {
        checkbox[i].removeAttribute("checked");
    }
    var httpRequest;
    var username = (new URLSearchParams(window.location.search)).get('username');
    var url = "./content/member/" + username + "/ispartof";
    httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = listActivities;
    httpRequest.open('GET', url);
    httpRequest.send();

    function listActivities() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {

                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData['resource-list'];

                for (var i = 0; i < resource.length; i++) {
                    document.getElementById(resource[i].department).setAttribute("checked", "checked");
                }
                $('.dep_checkbox').iCheck({checkboxClass: 'icheckbox_flat-green'});
            } else {
                alert('There was a problem retrieving the department list.');
            }
        }
    }
}


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

                set_dep_list();
            } else {
                alert('There was a problem retrieving the department list.');
            }
        }
    }
}


function delete_member(username) {

    var url = "./content/member/" + username;
    var xhreq = new XMLHttpRequest();
    xhreq.onreadystatechange = alertState;
    xhreq.open('DELETE', url);
    xhreq.setRequestHeader('Content-type', 'application/json');
    xhreq.setRequestHeader('Data-type', 'application/json');
    xhreq.send();
    function alertState() {
        if (xhreq.readyState === XMLHttpRequest.DONE) {
            if (xhreq.status == 200) {
                var jsonData = JSON.parse(xhreq.responseText);
                var displayData = "The member <strong> " + jsonData.member.username + " </strong>has been removed from the system";
                addSuccessMessage(displayData);
            } else
            {
                addErrorMessage(JSON.parse(xhreq.responseText).message.message);
            }
            
        }
    };
}

$("#adminEditMemberForm").submit(function (e) {

    submit_dep();
    e.preventDefault();

    var editform = document.getElementById('adminEditMemberForm');

    var fData = new FormData(editform);

    x = {};
    for (let [name, value] of fData) {
        x[name] = `${value}`;
    }

    var jsonData = JSON.stringify(x);
    jsonData = '{ "member": ' + jsonData + "}";

    //TRADITIONAL AJAX CALL

    var url = "./content/member/" + editform.username.value;
    var xhreq = new XMLHttpRequest();
    xhreq.onreadystatechange = alertState;

    xhreq.open('PUT', url);
    xhreq.setRequestHeader('Content-type', 'application/json');
    xhreq.setRequestHeader('Data-type', 'application/json');
    xhreq.send(jsonData);

    function alertState() {
        if (xhreq.readyState === XMLHttpRequest.DONE) {
            if (xhreq.status == 201 || xhreq.status == 200) {
                var jsonData = JSON.parse(xhreq.responseText);
                var displayData = "The member <strong> " + jsonData.member.username + " </strong>has been updated in the system";
                addSuccessMessage(displayData);
            } else {
                addErrorMessage(JSON.parse(xhreq.responseText).message.message);

            }
        }
    };

});


function load_member_data() {

    var username = (new URLSearchParams(window.location.search)).get('username');
    frm = document.getElementById('adminEditMemberForm');

    var httpRequest;
    var url = "./content/member/" + username;
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
                populateForm(frm, member);
            } else {
                addErrorMessage(JSON.parse(xhreq.responseText).message.message);
            }
        }
    }

    function populateForm(frm, data) {

        $.each(data, function (key, value) {
            var ctrl = $("[name=" + key + "]", frm);
            switch (ctrl.prop("type")) {
                case "radio":
                    if (
                        ctrl.parent().hasClass("icheck-primary") ||
                        ctrl.parent().hasClass("icheck-danger") ||
                        ctrl.parent().hasClass("icheck-success")
                    ) {

                        $.each(ctrl, function (ctrlKey, radioElem) {
                            radioElem = $(radioElem);


                            if (radioElem.attr("value") == value) {
                                radioElem.iCheck("check");
                            } else {
                                radioElem.iCheck("uncheck");
                            }
                        });
                    } else {
                        $.each(ctrl, function (ctrlKey, radioElem) {
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
