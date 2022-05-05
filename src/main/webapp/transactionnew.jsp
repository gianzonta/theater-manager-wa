<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>New Transaction | Ruzzante TMS</title>
    <jsp:include page="layout/assets_header.jsp" />
</head>

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <jsp:include page="layout/sidebar.jsp" />
            <jsp:include page="layout/top_navigation.jsp" />

            <!-- page content -->
            <div class="right_col" role="main">

                <div id="servMessage"></div>

                <div class="">
                    <div class="page-title">
                        <div class="title_left">
                            <h3>Add a new transaction</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>

                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Transaction info</h2>
                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    Fill the following fields, the ones marked with * are mandatory.
                                    <div class="divider-dashed"></div>
                                    <form class="form-horizontal form-label-left" action="" method="post"
                                        name="newEntry" id="newTransactEntry">

                                        <div class="form-group row">
                                            <label class="col-form-label col-md-3 col-sm-3  label-align">
                                                Type *
                                            </label>

                                            <div class="col-md-6 col-sm-6">
                                                <input type="radio" class="flat" name="type" id="typeI"  value="I"
                                                    checked="" required /> Income
                                                <input type="radio" class="flat" name="type" id="typeE" value="E"
                                                     /> Expense
                                            </div>

                                        </div>


                                        <div class="field item form-group">
                                            <label class="col-form-label col-md-3 col-sm-3  label-align">Name<span
                                                    class="required">*</span></label>
                                            <div class="col-md-6 col-sm-6">
                                                <input class="form-control" data-validate-length-range="6"
                                                    data-validate-words="2" name="name"
                                                    placeholder="ex. Rehearsal room rent, AY 2019/2020"
                                                    required="required" />
                                            </div>
                                        </div>

                                        <div class="field item form-group">
                                            <label class="col-form-label col-md-3 col-sm-3  label-align">Description
                                            </label>
                                            <div class="col-md-6 col-sm-6">
                                                <textarea class="form-control" class='optional' name="description"
                                                     type="text" row="3"></textarea>
                                            </div>
                                        </div>
                                        <div class="field item form-group">
                                            <label class="col-form-label col-md-3 col-sm-3  label-align">Amount <span
                                                    class="required">*</span></label>
                                            <div class="col-md-6 col-sm-6">
                                                <input class="form-control" name="amount" required="required"
                                                    type="number" step="0.01" />
                                            </div>
                                        </div>

                                        <div class="field item form-group">
                                            <label class="col-form-label col-md-3 col-sm-3  label-align">Date <span
                                                class="required">*</span></label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input type='date' class="form-control" id="datePicker" name="date" onload="setToday()" />

                                                </div>
                                        </div>

                                        <div class="field item form-group">
                                            <label class="col-form-label col-md-3 col-sm-3  label-align">Invoice Link</label>
                                            <div class="col-md-6 col-sm-6">
                                                <input type="text" class="form-control" name="invoice">
                                            </div>
                                        </div>

                                        <div class="field item form-group">
                                            <label class="col-form-label col-md-3 col-sm-3  label-align ">Related Activity ID</label>
                                            <div class="col-md-6 col-sm-6 ">
                                            <input list="activity_list" name="relatedActID" class="form-control col-md-12" id="relAct" placeholder="start typing to search..." autocomplete="off">
                                            <datalist id="activity_list">
                                            </datalist>
                                          </div>
                                          </div>

                                     <!--   <div class="field item form-group">
                                            <label class="col-form-label col-md-3 col-sm-3  label-align">New item
                                                purchase?</label>


                                            <div class="col-md-6 col-sm-6">
                                                <label for="itemToggle" class="toggleLabel"> Toggle </label>
                                                    <input name="enableNewItem" type="checkbox" id="itemToggle" class="hiddenToggle" data-js="toggle_additional" data-target="new_item_additional">
                                            </div>
                                            

                                    </div>

                                        <div id="new_item_additional" style="display: none;">
                                            <div class="x_title">
                                                <h2>Additional item information</h2>
                                                <div class="clearfix"></div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Name <span
                                                        class="required">*</span></label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" name="itemName" id="iName"
                                                        placeholder="ex. Complete knight armor (cardboard) (ruined)"
                                                        required="required" />
                                                </div>
                                            </div>
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Description
                                                </label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" class='optional' name="itemDesc"
                                                        type="text" /></div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Quantity
                                                    <span class="required">*</span></label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" name="quantity" required="required"
                                                        type="number" step="0.01" />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Size
                                                </label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" class='optional' name="size"
                                                        type="text" />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Historical
                                                    Genre (if applicable) </label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" class='optional' name="HistoricalGenre"
                                                        type="text" /></div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Image (Max 2MB) </label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input type="file" class="form-control" name="image"
                                                        class='optional'>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="divider-dashed"></div>
                                        <div class="form-group"> -->
                                            <div class="col-md-6 offset-md-5">
                                                <!-- edited here , was 3--> 
                                                <button type='submit' class="btn btn-primary">Submit</button>
                                            </div>
                                        </div>

                                    </form>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
                <!-- /page content -->
            </div>
            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />

    <script>
        

        
        

            //setActivityDefault;

        var httpRequest;

        var url = "./content/activity";

        httpRequest = new XMLHttpRequest();

        httpRequest.onreadystatechange = listActivities;
        httpRequest.open('GET', url);
        httpRequest.send();

        var actid = setDefaultActivity();
        console.log(actid);

        



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


            $("#newTransactEntry").submit(function(e) {

                e.preventDefault();
                
                var elem = document.getElementById('newTransactEntry')

                var fData = new FormData(elem);
                var x = {};
       
                x.name = fData.get("name");
                x.description = fData.get("description");

                x.date = fData.get("date");

                x.amount = fData.get("amount");
                var type= fData.get("type"); //check the value of this field

                if (type == "E") {
                    x.amount = "-"+ x.amount;
                }


                x.invoice = fData.get("invoice");
                
                x.username = "${user}";
                //x.seasonID = "20182019";//determined by dates

                
                
                var jsonData = JSON.stringify(x);

                jsonData = '{ "transaction": ' + jsonData + "}";

                //console.log(jsonData);
                //TRADITIONAL AJAX CALL

               var url = "./content/transaction";

                var xhreq = new XMLHttpRequest();
                xhreq.onreadystatechange = alertState;

                xhreq.open('POST', url);
                xhreq.setRequestHeader('Content-type', 'application/json');
                xhreq.setRequestHeader('Data-type', 'application/json');
                xhreq.send(jsonData);

                var newTransID;

                function alertState() {
                    if (xhreq.readyState === XMLHttpRequest.DONE) {
                        if (xhreq.status == 201) {
                            var jsonData = JSON.parse(xhreq.responseText);
                            var displayData = "The transaction <strong> " + jsonData.transaction.name + " </strong>has been added to the system";
                            //var newUrl =  "activityview?activityid=" + jsonData.activity.activityID;
                            //console.log(newUrl);
                            addSuccessMessage(displayData/*,newUrl*/);
                            newTransID = jsonData.transaction.transID;

                            var relActID = fData.get("relatedActID");
                            console.log(relActID)

                            if (relActID != "") {
                                addTransActRelation(relActID);
                                console.log("entered relation estabilish.")
                            }

                        } else {
                            addErrorMessage();
                        }
                    }
                };

                function addTransActRelation (relActID) {

                    x.transID = newTransID;
                    var jsonData2 = JSON.stringify(x);
                    jsonData2 = '{ "transaction": ' + jsonData2 + "}";
                    
                    var url2 = "http://localhost:8080/ruzzantetms/content/transaction/activity/" + relActID;

                    var xhreq2 = new XMLHttpRequest();
                    xhreq2.onreadystatechange = whenReady;

                    xhreq2.open('POST', url2);
                    xhreq2.setRequestHeader('Content-type', 'application/json');
                    xhreq2.setRequestHeader('Data-type', 'application/json');
                    xhreq2.send(jsonData2);

                    function whenReady() {
                    if (xhreq2.readyState === XMLHttpRequest.DONE) {
                        if (xhreq2.status == 200) {
                            var jsonData = JSON.parse(xhreq2.responseText);
                            var displayData = "The relation with activity <strong> " + relActID + " </strong>has been added to the system";

                            addSuccessMessage(displayData/*,newUrl*/);

                        } else {
                            addErrorMessage();
                        }
                    }
                };







                }

            });


    
    </script>


</body>

</html>