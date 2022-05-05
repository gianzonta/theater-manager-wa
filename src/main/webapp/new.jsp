<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>New Play - Ruzzante TMS</title>
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
              <h3>Add Play</h3>
            </div>
          </div>
          <div class="clearfix"></div>

          <div class="row">
            <div class="col-md-12 col-sm-12">
              <div class="x_panel">
                <div class="x_title">
                  <h2>New Event</h2>
                  <div class="clearfix"></div>
                  </div>
                            <div class="x_content">
                                   <form class="form-horizontal form-label-left" action="" method="post" id="newPlayEntry"
                                             <span class="section">Information</span>

                                             <table class="table table-striped table-bordered dt-responsive nowrap" >

                                                       <div class="field item form-group">
                                                             <label class="col-form-label col-md-3 col-sm-3  label-align">Title<span
                                                              class="required">*</span></label>
                                                             <div class="col-md-6 col-sm-6">
                                                                <input class="form-control" data-validate-length-range="6"
                                                                data-validate-words="2" name="title"
                                                                placeholder="ex. The New Opera Shakespeare 2019/2020"
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
                                                                  <label class="col-form-label col-md-3 col-sm-3  label-align">Script Link</label>
                                                                  <div class="col-md-6 col-sm-6">
                                                                  <input type="text" class="form-control" name="script">
                                                                   </div>
                                                        </div>

                                                         <div class="field item form-group">
                                                                   <label class="col-form-label col-md-3 col-sm-3  label-align">Duration</label>
                                                                     <div class="col-md-6 col-sm-6">
                                                                     <input class="form-control" name="duration" required="required"
                                                                     type="number" step="1" />
                                                                      </div>
                                                         </div>




                                                          <div class="field item form-group">
                                                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Poster Image Link</label>
                                                                    <div class="col-md-6 col-sm-6">
                                                                    <input type="text" class="form-control" name="posterimage">
                                                                    </div>
                                                          </div>

                                                         <!-- <div class="field item form-group">
                                                                   <label class="col-form-label col-md-3 col-sm-3  label-align">Related
                                                                   activity</label>
                                                                   <div class="col-md-6 col-sm-6">
                                                                       <select class="select2_group form-control" name="activity">
                                                                           <optgroup label="None">
                                                                               <option value="NULL">None</option>
                                                                           </optgroup>
                                                                           <optgroup label="All is Moon Fault">
                                                                               <option value="AIMF-1304">All is Moon Fault @ Padua - 13/4/2020
                                                                               </option>
                                                                               <option value="AIMF-2503">All is Moon Fault @ Verona - 25/3/2020
                                                                               </option>
                                                                           </optgroup>
                                                                           <optgroup label="Clue">
                                                                               <option value="CLUE-2404">Clue @ Venice - 29/4/2020</option>
                                                                               <option value="CLUE-1503">Clue @ Rovigo - 15/3/2020</option>
                                                                           </optgroup>
                                                                       </select>
                                                                   </div>
                                                               </div>-->

                                                            <!--    <div class="field item form-group">
                                                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Item</label>
                                                                    <div class="col-md-6 col-sm-6">
                                                                        <select class="select2_group form-control" name="item" >
                                                                                <option value="light">light</option>
                                                                                <option value="camera">camera</option>
                                                                                <option value="costume">costume</option>
                                                                                <option value="stage">stage</option>
                                                                        </select>

                                                                    </div>
                                                                </div>-->



                                                            <div class="field item form-group">
                                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Rehearsal schedule
                                                                </label>
                                                                <div class="col-md-6 col-sm-6">
                                                                    <textarea class="form-control" class='optional' name="rehearsal"
                                                                         type="text" row="3"></textarea>
                                                                </div>
                                                            </div>

                                             </table>



                                                          <div class="divider-dashed"></div>
                                                     <div class="form-group">
                                                        <div class="col-md-6 offset-md-5">
                                                           <button type='reset' class="btn btn-info">Reset fields</button>
                                                           <button type='button'class="btn btn-primary" id="newPlayEntryButton">Create Play</button>

                                                        </div>
                                                     </div>

                                    </form>
                            </div>
              </div>
            </div>
          </div>
        </div>
      </div>


  <jsp:include page="layout/footer.jsp" />
</div>
</div>

<jsp:include page="layout/assets_footer.jsp" />


    <script>



        $(function() {

            $("#newPlayEntryButton").click(function(e) {

                e.preventDefault();

                var elem = document.getElementById('newPlayEntry')

                var fData = new FormData(elem);
                var x = {};

                /*fData.forEach(function(value, key){
                    x[key] = value;
                }); */

                x.title = fData.get("title");
                x.description = fData.get("description");
                x.script = fData.get("script");

                x.duration = fData.get("duration");
                if (x.duration == "") {
                    x.duration = 0;
                }

                x.posterimage = fData.get("posterimage");
                x.rehearsal = fData.get("rehearsal");
                //x.activity = fData.get("activity");
                //x.item = fData.get("item");
                

                if (x.title == "") {
                    addErrorMessage("Title is a required field");
                    return;
                }


                var jsonData = JSON.stringify(x);

                jsonData = '{ "play": ' + jsonData + "}";


                //TRADITIONAL AJAX CALL

                var url = "./content/play";

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
                            var displayData = "The play <strong> " + jsonData.play.title + " </strong>has been added to the system";
                            var newUrl =  "playview?playid=" + jsonData.play.playID;
                            addSuccessMessageUrl(displayData,newUrl);
                        } else {
                            addErrorMessage();
                        }
                    }
                };

            });


        });
    </script>


</body>

</html>