<%@ page contentType="text/html;charset=utf-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>Edit Transaction | Ruzzante TMS</title>
    <jsp:include page="layout/assets_header.jsp" />
</head>

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <jsp:include page="layout/sidebar.jsp" />
            <jsp:include page="layout/top_navigation.jsp" />

            <!-- page content -->
            <div class="right_col" role="main">

                

                <div class="">
                    <div class="page-title">
                        <div class="title_left">
                            <h3>Manage transaction</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    

                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Transaction edit</h2>
                                    <div class="clearfix"></div>
                                </div>
                                
                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-12">

                                            <!-- page content --> 
                                            <form class="form-horizontal form-label-left" action="" method="post" name="newEntry" id="updateTransEntry">
                                        
                                                <div class="field item form-group">
                                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Transaction ID</label>
                                                    <div class="col-md-6 col-sm-6">
                                                        <input class="form-control" name="transID" value="${transaction.transID}" disabled/>
                                                    </div>
                                                </div>
    
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Name</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="name" value="${transaction.name}"/>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Amount</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" name="amount" type="number" step="0.01" value="${transaction.amount}" />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Date</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" class="date" type='date' name="date" value="${transaction.date}" disabled/>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Description</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <textarea class="form-control" name="description" rows="3" >${transaction.description}</textarea>
                                                </div>
                                            </div>



                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Managed by</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" name="username" value="${transaction.username}" disabled/>
                                                </div>
                                            </div>

                                            <div class="divider-dashed"></div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Invoice Link</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <a href="${transaction.invoice}" target="_blank" id="invoice_link">Open here <i class="fa fa-external-link-square"></i></a> or replace the document
                                                    <input type="text" class="form-control" name="invoice" value="${transaction.invoice}">
                                                </div>
                                              </div>

                                            <div class="divider-dashed"></div>
    
                                            <div class="form-group">
                                                <div class="col-md-6 offset-md-4"> 
                                                    <button type='reset' class="btn btn-info">Reset fields</button>
                                                    <button type='button' class="btn btn-primary"id="send">Edit fields</button>
                                                    <button type='button' class="btn btn-danger"id="delete">Delete Transaction</button>
                                                </div>
                                            </div>
                                        </form>                                          

                                        </div>
                                    </div>
                                </div>

                                

                            </div>

                        </div>
                    </div>

                    <div id="servMessage"></div>

                    <div class="row">
                        <div class="col-md-6 col-sm-6">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Related activities management</h2>
                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-12">

                                            

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Related Activities</label>
                                                <div class="col-md-7 col-sm-7">
                                                    <textarea class="form-control" name="relatedAct" value="N/D" id="relActDisplay" disabled></textarea>
                                                </div>
                                            </div>

                                            <form class="form-horizontal form-label-left" action="" method="post" id="addActForm">
                                        
                                                <div class="field item form-group">
                                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Add related activity</label>    
                                                    <div class="col-md-6 col-sm-6">
                                                        <input list="activity_list" name="addActivity" class="form-control col-md-12" id="relAct" rows="4" autocomplete="off" placeholder="start typing to search...">
                                                        <datalist id="activity_list">
                                                        </datalist>
                                                        
                                                    </div>
                                                    <div class="col-md-1 col-sm-1">
                                                        <button type='button' class="btn btn-primary" id="addActButton">Add</button>
                                                    </div>
                                                </div>   

                                            </form>
                                            
                                            <form class="form-horizontal form-label-left" action="" method="post" id="removeActForm">
                                                <div class="field item form-group">
                                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Remove related activity</label>
                                                    <div class="col-md-6 col-sm-6">
                                                        <select class="select2_group form-control" name="remActivity" id="relActSelect">
                                                            <!--<optgroup label="None">
                                                                <option value="NULL">None</option>
                                                            </optgroup> -->
                                                        </select>
                                                        
                                                    </div>
                                                    <div class="col-md-1 col-sm-1">
                                                        <button type="button" class="btn btn-danger" id ="removeActButton">Remove</button>
                                                    </div>
                                                </div>

                                            </form>  
                                                                                
                                        </div>
                                    </div>
                                </div>

                                

                            </div>

                        </div>

                        <div class="col-md-6 col-sm-6"> <!--rel items-->

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Related items management</h2>
                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-12">

                                            

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Related Items</label>
                                                <div class="col-md-7 col-sm-7">
                                                    <textarea class="form-control" name="relatedItm" value="N/D" id="relItemDisplay" disabled></textarea>
                                                </div>
                                            </div>

                                            <form class="form-horizontal form-label-left" action="" method="post" name="addItem" id="addItemForm">
                                        
                                                <div class="field item form-group">
                                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Add related item</label>    
                                                    <div class="col-md-6 col-sm-6">
                                                        <input list="item_list" name="addItem" class="form-control col-md-12" id="relItm" rows="4" autocomplete="off" placeholder="start typing to search...">
                                                        <datalist id="item_list">
                                                        </datalist>
                                                        
                                                    </div>
                                                    <div class="col-md-1 col-sm-1">
                                                        <button type='button' class="btn btn-primary" id="addItemButton">Add</button>
                                                    </div>
                                                </div>

                                            </form>
                                            
                                            <form class="form-horizontal form-label-left" action="" method="post" id="removeItemForm">
                                                <div class="field item form-group">
                                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Remove related item</label>
                                                    <div class="col-md-6 col-sm-6">
                                                        <select class="select2_group form-control" name="remItem" id="relItemSelect">
                                                            <!--<optgroup label="None">
                                                                <option value="NULL">None</option>
                                                            </optgroup> -->
                                                        </select>
                                                        
                                                    </div>
                                                    <div class="col-md-1 col-sm-1">
                                                        <button type="button" class="btn btn-danger" id ="removeItemButton">Remove</button>
                                                    </div>
                                                </div>

                                            </form>  
                                                                                
                                        </div>
                                    </div>
                                </div>

                                

                            </div>

                        </div>


                    </div>
                    

                </div>

            </div>


        


            <!-- /page content -->
            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />

    <script>

$(document).ready(showRelActsValue("relActDisplay"));

$(document).ready(showRelActsSelect("relActSelect"));


$(document).ready(showRelItemsValue("relItemDisplay"));

$(document).ready(showRelItemsSelect("relItemSelect"));

$(document).ready(function() {
        
        // initialization of full activity list
        var httpRequest;

        var url = "./content/activity";

        httpRequest = new XMLHttpRequest();

        httpRequest.onreadystatechange = listActivities;
        httpRequest.open('GET', url);
        httpRequest.send();

        
        function listActivities() {
            if (httpRequest.readyState === XMLHttpRequest.DONE) {
            
            if (httpRequest.status == 200) {


                var relAct = document.getElementById('activity_list');

                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData['resource-list'];

                for (var i = 0; i < resource.length; i++) {
                    var activity = resource[i].activity;

                    var option = document.createElement('option');

                    option.value = activity['activityID'];
                    //option.setAttribute("data-value",activity['title']); // advanced
                    option.appendChild(document.createTextNode(activity['title']));

                    relAct.appendChild(option);

                    }


                } else {
                    alert('There was a problem with the request.');
                }
            }
        }


        // initialization of full items list

        var httpRequest2;

        var url2 = "./content/item";

        httpRequest2 = new XMLHttpRequest();

        httpRequest2.onreadystatechange = listItems;
        httpRequest2.open('GET', url2);
        httpRequest2.send();
        

        function listItems() {
            if (httpRequest2.readyState === XMLHttpRequest.DONE) {
            
            if (httpRequest2.status == 200) {


                var relItem = document.getElementById('item_list');

                var jsonData = JSON.parse(httpRequest2.responseText);
                var resource = jsonData['resource-list'];

                for (var i = 0; i < resource.length; i++) {
                    var item = resource[i].item;

                    var option = document.createElement('option');

                    option.value = item['itemID'];
                    //option.setAttribute("data-value",activity['title']); // advanced
                    option.appendChild(document.createTextNode(item['name']));

                    relItem.appendChild(option);

                    }


                } else {
                    alert('There was a problem with the request.');
                }
            }
        }


    $("#send").click(function(e) {

        e.preventDefault();

        var elem = document.getElementById('updateTransEntry');

        var fData = new FormData(elem);
        var x = {};

        x.transID = '<c:out value="${transaction.transID}"/>'
        x.name = fData.get("name");
        x.amount = fData.get("amount");
        x.description = fData.get("description");
        x.invoice= fData.get("invoice");

        document.getElementById("invoice_link").setAttribute("href", x.invoice);
        
        var jsonData = JSON.stringify(x);

        jsonData = '{ "transaction": ' + jsonData + "}";

        console.log(jsonData);

        
        //TRADITIONAL AJAX CALL

        var url = "./content/transaction/${transaction.transID}";

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
                    var displayData = "The transaction <strong> " + jsonData.transaction.name + " </strong>has been updated in the system";
                    
                    addSuccessMessage(displayData);
                } else {
                    addErrorMessage();
                }
            }
        };

    });

    $("#delete").click(function(e) {

        //TRADITIONAL AJAX CALL

        var url = "./content/transaction/${transaction.transID}";

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
                    var displayData = "The transaction <strong> " + jsonData.transaction.name + " </strong>has been deleted from the system, you will be redeirected to the transaction list in a few seconds";
                    addWarningMessage(displayData);
                    setTimeout(function() {
                        window.location.href = "transactionlist";
                    }, 2000);
                    
                } else {
                    addErrorMessage();
                }
            }
        };

    });

    $("#removeActButton").click(function(e) {

        //jquery AJAX CALL
        //e.preventDefault;

        var elem = document.getElementById("removeActForm");
        var fData = new FormData(elem);
        activityid = fData.get("remActivity");

        var URI = "./content/transaction/activity/" + activityid;

        var x = {};

        x.transID = getUrlVars()["transid"]; 
        reqData = JSON.stringify(x);
        reqData = '{ "transaction": ' + reqData + "}";

        $.ajax({
            url:URI,
            method: "DELETE",
            contentType: "application/json",
            //dataType: "application/json",
            data: reqData,
            success: function(data) {
                var text = "Activity successfully removed";
                addWarningMessage(text);
                showRelActsValue("relActDisplay");
                showRelActsSelect("relActSelect");
            },
            error: function() {
                console.log("Error");
                addErrorMessage();
            }, 
        });

        });

    $("#addActButton").click(function(e) {

        //jquery AJAX CALL
        //e.preventDefault;

        var elem = document.getElementById("addActForm");
        var fData = new FormData(elem);
        activityid = fData.get("addActivity");

        var URI = "./content/transaction/activity/" + activityid;

        var x = {};

        x.transID = getUrlVars()["transid"]; 
        reqData = JSON.stringify(x);
        reqData = '{ "transaction": ' + reqData + "}";

        $.ajax({
            url:URI,
            method: "POST",
            contentType: "application/json",
            //dataType: "application/json",
            data: reqData,
            success: function(data) {
                var successText = "Activity successfully added";
                addSuccessMessage(successText);
                showRelActsValue("relActDisplay");
                showRelActsSelect("relActSelect");
            },
            error: function(jqXHR) {
                console.log("Error");
                if (jqXHR.status == 409) {
                    addErrorMessage("The activity is already related to this transaction");
                 }
            }, 
        });

    });


    $("#removeItemButton").click(function(e) {

        //jquery AJAX CALL
        //e.preventDefault;

        var elem = document.getElementById("removeItemForm");
        var fData = new FormData(elem);
        itemid = fData.get("remItem");

        var URI = "./content/transaction/item/" + itemid;

        var x = {};

        x.transID = getUrlVars()["transid"]; 
        reqData = JSON.stringify(x);
        reqData = '{ "transaction": ' + reqData + "}";

        $.ajax({
            url:URI,
            method: "DELETE",
            contentType: "application/json",
            //dataType: "application/json",
            data: reqData,
            success: function(data) {
                var text = "Item successfully removed";
                addWarningMessage(text);
                showRelItemsValue("relItemDisplay");
                showRelItemsSelect("relItemSelect");
            },
            error: function() {
                console.log("Error");
                addErrorMessage();
            }, 
        });

        });

    $("#addItemButton").click(function(e) {

        //jquery AJAX CALL
        //e.preventDefault;

        var elem = document.getElementById("addItemForm");
        var fData = new FormData(elem);
        itemid = fData.get("addItem");

        var URI = "./content/transaction/item/" + itemid;

        var x = {};

        x.transID = getUrlVars()["transid"]; 
        reqData = JSON.stringify(x);
        reqData = '{ "transaction": ' + reqData + "}";

        $.ajax({
            url:URI,
            method: "POST",
            contentType: "application/json",
            //dataType: "application/json",
            data: reqData,
            success: function(data) {
                var successText = "Item successfully added";
                addSuccessMessage(successText);
                showRelItemsValue("relItemDisplay");
                showRelItemsSelect("relItemSelect");
            },
            error: function(jqXHR) {
                console.log("Error");
                if (jqXHR.status == 409) {
                    addErrorMessage("The item is already related to this transaction");
                }
            }, 
        });

    });
    
});
    </script>


</body>

</html>