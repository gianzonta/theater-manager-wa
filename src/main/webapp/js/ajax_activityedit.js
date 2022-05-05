$(document).ready(function() {


    fill_play_selector();

    function fill_play_selector() {
        var httpRequest;

        var url = "./content/play";

        httpRequest = new XMLHttpRequest();

        httpRequest.onreadystatechange = listPlays;
        httpRequest.open('GET', url);
        httpRequest.send();

        function listPlays() {
            if (httpRequest.readyState === XMLHttpRequest.DONE) {

                if (httpRequest.status == 200) {

                    var relAct = document.getElementById('play_list');

                    var jsonData = JSON.parse(httpRequest.responseText);
                    var resource = jsonData['resource-list'];

                    for (var i = 0; i < resource.length; i++) {
                        var department = resource[i].play;

                        var option = document.createElement('option');
                        option.value = department['playID'];
                        option.appendChild(document.createTextNode(department['title']));
                        relAct.appendChild(option);

                    }

                } else {
                    alert('There was a problem retrieving the department list.');
                }
            }
        }
    }

    $("#updateActEntry").submit(function(e) {

        e.preventDefault();




        var elem = document.getElementById('updateActEntry');

        var fData = new FormData(elem);
        var x = {};

        x.activityID = (new URLSearchParams(window.location.search)).get('activityid');
        x.title = fData.get("title");
        x.type = fData.get("type");
        x.play = fData.get("play");

        var date = fData.get("date");
        var time = fData.get("time");
        x.date = date + " " + time +":00" ; // automatick reference to italian timezone, when added to DB

        x.location = fData.get("location");
        x.description = fData.get("description");
        x.privacyTag = fData.get("privacyTag");
        x.audienceSize = fData.get("audienceSize");
        x.maxAudience = fData.get("maxAudience");
        //x.seasonID =  '<c:out value="${activity.seasonID}"/>' // season not relevant for update (supposed the same)
        
        var jsonData = JSON.stringify(x);

        jsonData = '{ "activity": ' + jsonData + "}";

        
        //TRADITIONAL AJAX CALL

        var actid;

        var url = "./content/activity/"+ (new URLSearchParams(window.location.search)).get('activityid');

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
                    actid = jsonData.activity.activityID;
                    var displayData = "The activity <strong> " + jsonData.activity.title + " </strong>has been updated in the system";
                    var newUrl =  "activityview?activityid=" + jsonData.activity.activityID;
                    addSuccessMessage(displayData,newUrl);
                    updatePlayRel();
                } else {
                    addErrorMessage();
                }
            }
        };

        function updatePlayRel(){
        
            if (x.play == "null") {
                x.play = -1;
            }

                var URI = "./content/activity/play/" + x.play;
                x.activityID = actid;

                var fullAct = JSON.stringify(x);
                fullAct = '{ "activity": ' + fullAct + "}";

                $.ajax({
                    url:URI,
                    method: "POST",
                    contentType: "application/json",
                    //dataType: "application/json",
                    data: fullAct,
                    success: function(data) {
                        var successText = "Play relation successfully added";
                        addSuccessMessage(successText);

                    },
                    error: function(jqXHR) {
                        if (jqXHR.status == 409) {
                            addErrorMessage("The play is already related to this activity");
                        }
                    }, 
                });
        
}

    });

$("#delete").click(function(e) {

    //TRADITIONAL AJAX CALL

    var url = "./content/activity/" +(new URLSearchParams(window.location.search)).get('activityid');

    var xhreq = new XMLHttpRequest();
    xhreq.onreadystatechange = alertState;

    xhreq.open('DELETE', url);
    xhreq.setRequestHeader('Content-type', 'application/json');
    xhreq.setRequestHeader('Data-type', 'application/json');
    xhreq.send();

    function alertState() {
        if (xhreq.readyState === XMLHttpRequest.DONE) {
            if (xhreq.status == 201 || xhreq.status == 200) {
                var jsonData = JSON.parse(xhreq.responseText);
                var displayData = "The activity <strong> " + jsonData.activity.title + " </strong>has been deleted from the system, you will be redeirected to the activity list in a few seconds";
                addWarningMessage(displayData);
                setTimeout(function() {
                    window.location.href = "activitylist";
                }, 2000);
                
            } else {
                addErrorMessage();
            }
        }
    };

    });


});