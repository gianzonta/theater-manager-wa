$("#editItemForm").submit(function (e) {

    e.preventDefault();

    var editform = document.getElementById('editItemForm');

    var fData = new FormData(editform);

    x = {};
    for (let [name, value] of fData) {
        x[name] = `${value}`;
    }

    var jsonData = JSON.stringify(x);
    jsonData = '{ "item": ' + jsonData + "}";
    console.log(jsonData);
    //TRADITIONAL AJAX CALL

    var url = "./content/item/" + editform.itemID.value;
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
                var displayData = "The item <strong> " + jsonData.item.name + " </strong>has been updated in the system";
                addSuccessMessage(displayData);
            } else {
                addErrorMessage();
                console.log(xhreq.responseText);
                console.log(xhreq.status);
            }
        }
    };

});


function load_item_data() {

    var itemid = (new URLSearchParams(window.location.search)).get('itemid');
    frm = document.getElementById('editItemForm');

    var httpRequest;
    var url = "./content/item/" + itemid;
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
                var item = jsonData['item'];
                populateForm(frm, item);
            } else {
                alert('There was a problem with the request.');
                console.log(httpRequest.responseText);
                console.log(httpRequest.status);
            }
        }
    }

    function populateForm(frm, data) {

        $.each(data, function (key, value) {
            var ctrl = $("[name=" + key + "]", frm);
            switch (ctrl.prop("type")) {
               
                 default:
                    ctrl.val(value);
            }
        });
    }

}
