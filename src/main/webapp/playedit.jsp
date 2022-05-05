<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>Edit Play/Event | Ruzzante TMS</title>
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
                            <h3>Edit Play/Event</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>





    <div class="row"> <!-- activity row -->
        


        <div class="col-md-4 mb-3">

            <div class="x_panel h-100">
                <div class="x_title">
                    <h2>Play poster</h2>


                    <div class="clearfix"></div>
                </div>

                <div class="x_content">
                    
                    <div class="row">
                        <div class="col-sm-10 offset-sm-1">
                            

                            <!-- play poster --> 
                            <div class="play_img">
                                <div id="crop-avatar">
                                 <!-- Current avatar -->
                                      <img class="img-responsive avatar-view" src="${play.posterimage}" alt="Poster" style="height: 365px;" href="${play.posterimage}">
                                </div>
                             </div>

                        </div>
                        
                    </div>

                </div>


            </div>

        </div>

        <div class="col-md-8 col-sm-8 ">

            <div class="x_panel ">
                <div class="x_title">
                    <h2>Edit Play details</h2>


                    <div class="clearfix"></div>
                </div>

                <div class="x_content ">
                    <div class="row">
                        <div class="col-sm-10 offset-sm-1">


                            <!-- transaction details --> 

                            <form class="form-horizontal form-label-left" action="" method="post" name="newEntry" id="updatePlayEntry">

                                <div class="field item form-group">
                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Play ID</label>
                                    <div class="col-md-6 col-sm-6">
                                        <input class="form-control" name="playid" value="${play.playid}" disabled/>
                                    </div>
                                </div>

                                <div class="field item form-group">
                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Title</label>
                                    <div class="col-md-6 col-sm-6">
                                        <input class="form-control"  name="title" value="${play.title}"/>
                                    </div>
                                </div>


                                <div class="field item form-group">
                                <label class="col-form-label col-md-3 col-sm-3  label-align">Script</label>
                                <div class="col-md-6 col-sm-6">
                                    <input class="form-control"  name="script" value="${play.script}"/>
                                </div>
                                </div>


                                <div class="field item form-group">
                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Poster Image Link</label>
                                    <div class="col-md-6 col-sm-6">
                                        <input class="form-control"  name="posterimage" value="${play.posterimage}"/>
                                    </div>
                                </div>


                                <div class="field item form-group">
                                 <label class="col-form-label col-md-3 col-sm-3  label-align">Duration<span
                                  class="required">*</span></label>
                                       <div class="col-md-6 col-sm-6">
                                          <input class="form-control" name="duration" value="${play.duration}" required="required"
                                            type="number" step="1" value="120"/>
                                       </div>
                                </div>

                                <div class="field item form-group">
                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Description</label>
                                    <div class="col-md-6 col-sm-6">
                                       <textarea class="form-control" name="description"  rows="3" >${play.description}</textarea>
                                         </div>
                                </div>

                                  <div class="field item form-group">
                                      <label class="col-form-label col-md-3 col-sm-3  label-align">Rehearsal</label>
                                      <div class="col-md-6 col-sm-6">
                                         <textarea class="form-control" name="rehearsalschedule" value="${play.rehearsalschedule}" rows="3" >The time expected is during Fridays from 10-01-2020 to 10-02-2020</textarea>
                                           </div>
                                  </div>


                                 <div class="divider-dashed"></div>

                                        <div class="form-group">
                                            <div class="col-md-8 offset-md-3">
                                                <button type='reset' class="btn btn-info">Reset fields</button>
                                                <button type='submit' class="btn btn-primary">Submit Edit</button>
                                                <button type='button' class="btn btn-danger" id="delete">Delete Play</button>
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



        $(document).ready(function() {

            $("#updatePlayEntry").submit(function(e) {

                e.preventDefault();




                var elem = document.getElementById('updatePlayEntry');

                var fData = new FormData(elem);
                var x = {};

                x.playid = (new URLSearchParams(window.location.search)).get('playid');
                x.title = fData.get("title");
                x.description = fData.get("description");
                x.script = fData.get("script");
                x.posterimage = fData.get("posterimage");


                x.duration = fData.get("duration");
                x.rehearsalschedule = fData.get("rehearsalschedule");

                console.log(x);

                var jsonData = JSON.stringify(x);

                jsonData = '{ "play": ' + jsonData + "}";

                console.log(jsonData);


                //TRADITIONAL AJAX CALL

                var url = "./content/play/${play.playid}";

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
                            var displayData = "The play <strong> " + jsonData.play.title + " </strong>has been updated in the system";
                            var newUrl =  "playview?playid=" + jsonData.play.playid;
                            addSuccessMessageUrl(displayData,newUrl);

                        } else {
                            addErrorMessage();
                        }
                    }
                };

            });

        $("#delete").click(function(e) {

            //TRADITIONAL AJAX CALL

            var url = "./content/play/" +(new URLSearchParams(window.location.search)).get('playid');

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
                        var displayData = "The play <strong> " + jsonData.play.title + " </strong>has been deleted from the system, you will be redeirected to the play list in a few seconds";
                        addWarningMessage(displayData);
                        setTimeout(function() {
                            window.location.href = "playlist";
                        }, 2000);

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