<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>New Department | Ruzzante TMS</title>
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
                            <h3>Add New Department</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    

                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>New Department Info</h2>
                                    <div class="clearfix"></div>
                                </div>

                                Fill the following fields, the ones marked with * are mandatory.

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-12">

                                            <!-- page content --> 
                                            <form class="form-horizontal form-label-left" action="" method="post" id="newDepEntry">
    
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Name*</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="name" required="required"/>
                                                </div>
                                            </div>


                                           

                                           

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Description</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <textarea class="form-control" name="description" rows="3" ></textarea>
                                                </div>
                                            </div>

                                            

                                        
                                            <div class="divider-dashed"></div>
    
                                            <div class="form-group">
                                                <div class="col-md-6 offset-md-5"> 
                                                    <button type='button' id='submitButton' class="btn btn-primary">Create Department</button>
                                                    <a href="<%= response.encodeURL(request.getContextPath() + "/departmentlist") %>"><button type='button' class="btn btn-warning">Cancel</button></a>
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



            $("#submitButton").click(function(e) {

                e.preventDefault();
                
                var elem = document.getElementById('newDepEntry')

                var fData = new FormData(elem);
                var x = {};

                /*fData.forEach(function(value, key){
                    x[key] = value;
                }); */

                x.name = fData.get("name");
               
                x.description = fData.get("description");
               


                
                var jsonData = JSON.stringify(x);

                jsonData = '{ "department": ' + jsonData + "}";

                
                //TRADITIONAL AJAX CALL

                var url = "http://localhost:8080/ruzzantetms/content/department";

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
                            var displayData = "The department <strong> " + jsonData.department.name + " </strong>has been added to the system";
                            var newUrl =  "departmentdetail?name=" + jsonData.department.name;
                            console.log(newUrl);
                            addSuccessMessage(displayData,newUrl);
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