<%@ page contentType="text/html;charset=utf-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>Edit Department | Ruzzante TMS</title>
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
                            <h3>Manage Department</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    

                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Department Edit</h2>
                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-12">

                                            <!-- page content --> 
                                            <form class="form-horizontal form-label-left" action="" method="post" name="newEntry" id="updateDepEntry">
                                        
                                                <div class="field item form-group">
                                                    <label class="col-form-label col-md-3 col-sm-3  label-align">name</label>
                                                    <div class="col-md-6 col-sm-6">
                                                        <input class="form-control" name="name" disabled id = "name" value="${department.name}"/>
                                                    </div>
                                                </div>
    
                                           

                                            
                                            
                                            

                                            

                                            

                                            
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Description</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <textarea class="form-control" name="description" rows="3" >${department.description}</textarea>
                                                </div>
                                            </div>

                                           

                                           

                                            <div class="divider-dashed"></div>
    
                                            <div class="form-group">
                                                <div class="col-md-6 offset-md-4"> 
                                                    
                                                    <button type='button' class="btn btn-primary" id="submit">Submit</button>
                                                    <button type='button' class="btn btn-danger" id="delete">Delete</button>
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

                    <div id="servMessage"></div>

                            
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

            $("#submit").click(function(e) {

                e.preventDefault();




                var elem = document.getElementById('updateDepEntry');

                var fData = new FormData(elem);
                var x = {};

                x.name = '<c:out value="${department.name}"/>'
             
              
                x.description = fData.get("description");
              
                
                var jsonData = JSON.stringify(x);

                jsonData = '{ "department": ' + jsonData + "}";

                console.log(jsonData);

                
                //TRADITIONAL AJAX CALL

                var url = "./content/department/${department.name}";

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
                            var displayData = "The department <strong> " + jsonData.department.name + " </strong>has been updated in the system";
                            var newUrl =  "departmentdetail?name=" + jsonData.department.name;
                            addSuccessMessage(displayData,newUrl);
                        } else {
                            addErrorMessage();
                        }
                    }
                };

            });

        $("#delete").click(function(e) {

            //TRADITIONAL AJAX CALL

            var url = "./content/department/${department.name}";

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
                        var displayData = "The department <strong> " + jsonData.department.name + " </strong>has been deleted from the system, you will be redeirected to the department list in a few seconds";
                        addWarningMessage(displayData);
                        setTimeout(function() {
                            window.location.href = "departmentlist";
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