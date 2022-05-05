<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>New Activity | Ruzzante TMS</title>
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
                            <h3>Add a new activity</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    

                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>New Activity details</h2>
                                    <div class="clearfix"></div>
                                </div>

                                Fill the following fields, the ones marked with * are mandatory.

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-12">

                                            <!-- page content --> 
                                            <form class="form-horizontal form-label-left" action="" method="post" id="newActEntry">
    
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Title*</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="title" placeholder="First replica: La Farsa dell'oratore Palpellius" required="required"/>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Type</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="type"/>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Assigned play (if any)</label>    
                                                <div class="col-md-6 col-sm-6">
                                                    <select class="select2_group form-control" name="play" id="play_list">
                                                        <option value ="null">No Play</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Date*</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" class="date" type='date' name="date"  required="required"/>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Start Time*</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" type='time' name="time" value="10:00" required="required"/>
                                                </div>
                                            </div>


                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Location*</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="location" required="required"/>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Description</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <textarea class="form-control" name="description" rows="3" ></textarea>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">PrivacyTag</label>    
                                                <div class="col-md-6 col-sm-6">
                                                    <select class="select2_group form-control" name="privacyTag" >
                                                            <option value="Pro">Pro</option>
                                                            <option value="Uni">Uni</option>
                                                            <option value="Public">Public</option>
                                                            <option value="Private">Private</option>
                                                    </select>
                                                    
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Audience Size</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" name="audienceSize" type="number" step="1" />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Max. Audience</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" name="maxAudience" type="number" step="1" />
                                                </div>
                                            </div>

                                            <!-- SEASON FETCHED AUTOMATICALLY FROM DATE on server side
                                                <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Season</label>    
                                                <div class="col-md-6 col-sm-6">
                                                    <select class="select2_group form-control" name="seasonID" required="required" >
                                                            <option value="20182019">2018/2019</option>
                                                            <option value="20192020">2019/2020</option>
                                                            <option value="20202021">20202021</option>
                                                    </select>
                                                    
                                                </div>
                                            </div> --> 

                                        
                                            <div class="divider-dashed"></div>
    
                                            <div class="form-group">
                                                <div class="col-md-6 offset-md-5"> 
                                                    <button type='reset' class="btn btn-info">Reset fields</button>
                                                    <button type='submit' id='submitButton' class="btn btn-primary">Create Activity</button>

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

                    </div>


                </div>
            </div>
            <!-- /page content -->
            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />

    <script>
        

        
        $(function() {

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

            $("#newActEntry").submit(function(e) {

                e.preventDefault();
                
                var elem = document.getElementById('newActEntry')

                var fData = new FormData(elem);
                var x = {};

                /*fData.forEach(function(value, key){
                    x[key] = value;
                }); */

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
                if (x.audienceSize =="") {
                    x.audienceSize = 0;
                }

                x.maxAudience = fData.get("maxAudience");
                if (x.maxAudience =="") {
                    x.maxAudience = 0;
                }

                //x.seasonID = fData.get("seasonID");


                
                var jsonData = JSON.stringify(x);

                jsonData = '{ "activity": ' + jsonData + "}";

                
                //TRADITIONAL AJAX CALL

                var actid;

                var url = "./content/activity";

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
                            actid = jsonData.activity.activityID;
                            var displayData = "The activity <strong> " + jsonData.activity.title + " </strong>has been added to the system";
                            var newUrl =  "activityview?activityid=" + actid;
                            addSuccessMessage(displayData,newUrl);
                            updatePlayRel();
                        } else {
                            addErrorMessage();
                        }
                    }
                };

                //JQUERY ajax call, add relation to play - URI: content/activity/play/{playid} sending the activity with POST
                
                function updatePlayRel(){

                
                    if (x.play != "null") {

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
                }

            });

                        
        });

        
    </script>

</body>

</html>