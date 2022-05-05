<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>Department Detail | Ruzzante TMS</title>
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
                            <h3>Department Detail</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    
                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Department Info</h2>

                                
                                    <div class="clearfix"></div>
                                </div>

                                <div class="panel-body has-table">

                                    <div class="col-sm-9 offset-sm-2">

                                        
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2 text-left">
                                                <p><span class="h6 font-weight-bold">Name:
                                                </span></p>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-10 text-left">
                                                <p><span class="h6"><c:out value="${department.name}" />
                                                </span></p>
                                            </div>
                                        </div>
                                        

                                        <div class="row">
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2 text-left">
                                                <p><span class="h6 font-weight-bold">Description:
                                                </span></p>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-10 text-left">
                                                <p><span class="h6"><c:out value="${department.description}" />
                                                </span></p>
                                            </div>
                                        </div>

                                        
                                        






                                    </div>

                                </div>





                            </div>
                            <div class="form-group">
                                <div class="col-md-6 offset-md-3">
                                    <a href="/ruzzantetms/editdepartment?name=${department.name}"><button type='Edit' class="btn btn-primary">Edit</button></a>
                                    <a href="<%= response.encodeURL(request.getContextPath() + "/departmentlist") %>"><button type='button' class="btn btn-warning">Cancel</button></a>
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

</body>

</html>