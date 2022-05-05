// this is what is called an 'iife' function; it basically calls itself after it's defined
(function () {
    // find all checkboxes that have data-js='toggle_additional'
    var toggle_additional = document.querySelectorAll("[data-js='toggle_additional']")

    // for each of them
    toggle_additional.forEach(el => {
        // add an event listener
        el.addEventListener("change", function (event) {
            // get the target from data-target='target'
            var target = document.getElementById(el.dataset.target)
            // if checked => display the target
            if (this.checked) target.style.display = "block";
            // otherwise => hide it
            else target.style.display = "none";
        })
    })


})();

(function setToday() {

    var field = document.getElementById("datePicker");
    if (field) {
        var date = new Date();

        field.value = date.getFullYear().toString() + '-' + (date.getMonth() + 1).toString().padStart(2, 0) +
            '-' + date.getDate().toString().padStart(2, 0);
    }
})();

(() => {
    var forgotpsw_form = document.getElementById("forgotpsw");
    if (forgotpsw_form) {
        forgotpsw_form.addEventListener("submit", function(event) {
            event.preventDefault();
            // ajax call to the server will be here
            // on success, show the modal dynamically
            $(forgotpsw_form.dataset.target).modal('show')
        })
    }
})();

/* //switched to eventListener below
function addSelectionToTextarea(idSel,idText) {
    
    if (idSel == "") {document.getElementById(idText).value = "";}
    else {
        var e = document.getElementById(idSel);
        var str = e.options[e.selectedIndex].text + "\n";
        document.getElementById(idText).value +=  str;
    }
};
*/
// this functions append a text from data-source to data-target
(function addToTextarea() {
    // find all inputs that have attribute data-js='addToTextarea'
    var addTo = document.querySelectorAll("[data-js='addToTextarea']")

    // for each of them
    addTo.forEach(el => {
        // add an event listener
        el.addEventListener("click", function (event) {
            // get the target from data-target='target'
            var target = document.getElementById(el.dataset.target);
            var str = "";
            if (el.dataset.source == "") {
                target.value = "";
                return;
            }
            var source = document.getElementById(el.dataset.source);
            if (source.nodeName == "SELECT") {
                str = source.options[source.selectedIndex].text + "\n";
            } 
            else {
                str = source.value + "\n";
            }
            target.value += str;
        })
    })
})();


function addSuccessMessageUrl(data,newUrl) {
            


    var htmlcontent ='<div class="alert alert-success alert-dismissible fade show"><a href="' + newUrl + '">';
    htmlcontent = htmlcontent + data;
    htmlcontent = htmlcontent + '</a><button type="button" class="close" data-dismiss="alert">&times;</button></div>'; 
        
    $("#servMessage").append(htmlcontent); //EXTENDED CODE WITH URL
}

function addSuccessMessage(data) {
            
    var htmlcontent ='<div class="alert alert-success alert-dismissible fade show">';
    htmlcontent = htmlcontent + data;
    htmlcontent = htmlcontent + '<button type="button" class="close" data-dismiss="alert">&times;</button></div>'; 
        
    $("#servMessage").append(htmlcontent); 
}

function addErrorMessage() {
    var htmlcontent ='<div class="alert alert-danger alert-dismissible fade show">';
        htmlcontent = htmlcontent + "Something went wrong";
        htmlcontent = htmlcontent + '<button type="button" class="close" data-dismiss="alert">&times;</button></div>'; 
        
        $("#servMessage").append(htmlcontent);
}

function addErrorMessage(text) {
    if (text == null){
        text = "Something went wrong"
    }
    var htmlcontent ='<div class="alert alert-danger alert-dismissible fade show">';
        htmlcontent = htmlcontent + text;
        htmlcontent = htmlcontent + '<button type="button" class="close" data-dismiss="alert">&times;</button></div>'; 
        
        $("#servMessage").append(htmlcontent);
}

function addWarningMessage(data) {
    var htmlcontent ='<div class="alert alert-warning alert-dismissible fade show">';
    htmlcontent = htmlcontent + data;
    htmlcontent = htmlcontent + '<button type="button" class="close" data-dismiss="alert">&times;</button></div>'; 
        
        $("#servMessage").append(htmlcontent);
}

function setDefaultActivity(){
    var actid = -1;
    if(window.location.href.indexOf("activityid") > -1){
        actid = getUrlVars()["activityid"];
        document.getElementById("relAct").setAttribute("value", actid);
        }
    return actid;
}

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

function showRelActsValue(elID) { // used in transactionedit
            
    $.ajax({
        url:'http://localhost:8080/ruzzantetms/content/activity/transaction/'+ getUrlVars()["transid"],
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
            document.getElementById(elID).value = "Not defined";
        }else {
            for (var i = 0; i < resource.length; i++) {
                var act = resource[i].activity;
                var actText = actText + act['title'] + "\n"
                
            }
            document.getElementById(elID).innerHTML = actText;
        }

    }

}

function showRelActsSelect(elID) { // used in transactionedit
            
    $.ajax({
        url:'http://localhost:8080/ruzzantetms/content/activity/transaction/'+ getUrlVars()["transid"],
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
        document.getElementById(elID).innerHTML = "";
        var jsonData = data;
        var resource = jsonData['resource-list'];
        var actText = "";

        if (resource.length == 0) {
            var opt = document.createElement("option");
            opt.value = "NULL";
            opt.innerHTML = "None"
            document.getElementById(elID).appendChild(opt);
        }else {
            for (var i = 0; i < resource.length; i++) {
                var act = resource[i].activity;
                var opt = document.createElement("option");
                opt.value = act["activityID"];
                opt.innerHTML = act['title']
                document.getElementById(elID).appendChild(opt);

                
            }
        }

    }

}



function showRelItemsValue(elID) { // used in transactionedit
            
    $.ajax({
        url:'http://localhost:8080/ruzzantetms/content/item/transaction/'+ getUrlVars()["transid"],
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
            document.getElementById(elID).value = "Not defined";
        }else {
            for (var i = 0; i < resource.length; i++) {
                var itm = resource[i].item;
                var actText = actText + itm['name'] + "\n"
                
            }
            document.getElementById(elID).innerHTML = actText;
        }

    }

}

function showRelItemsSelect(elID) { // used in transactionedit
            
    $.ajax({
        url:'http://localhost:8080/ruzzantetms/content/item/transaction/'+ getUrlVars()["transid"],
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
        document.getElementById(elID).innerHTML = "";
        var jsonData = data;
        var resource = jsonData['resource-list'];
        var actText = "";

        if (resource.length == 0) {
            var opt = document.createElement("option");
            opt.value = "NULL";
            opt.innerHTML = "None"
            document.getElementById(elID).appendChild(opt);
        }else {
            for (var i = 0; i < resource.length; i++) {
                var itm = resource[i].item;
                console.log(itm);
                var opt = document.createElement("option");
                opt.value = itm['itemID'];
                opt.innerHTML = itm['name'];
                document.getElementById(elID).appendChild(opt);

                
            }
        }

    }

}



function automateBadgeColor() { //general purpose, document needs to be completely loaded for the function to work
    
    var elements = document.getElementsByName("activityBadge");
    for (var i = 0; i < elements.length; i++  ) {
      var actType = elements[i].innerHTML;
      var badgeType = "badge ";

      if (actType == "Play" || actType == "play") {
        badgeType = badgeType + "badge-primary";
      } 
      else if (actType == "Rehearsal" || actType == "rehearsal") {
          badgeType = badgeType + "badge-success";
      } else {
          badgeType = badgeType + "badge-secondary";
        }

      elements[i].className = badgeType + " event-type";
    } 
}