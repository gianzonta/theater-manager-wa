<%@ page contentType="text/html;charset=utf-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html lang="en">

<head>
  <jsp:include page="layout/header.jsp" />
  <title>All Department | Ruzzante TMS</title>
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
              <h3>All Department</h3>
            </div>
            <!-- <div class="title_right">
              <div class="col-md-5 col-sm-5   form-group pull-right top_search">
                <div class="input-group">
                  <input type="text" class="form-control" placeholder="Search for...">
                  <span class="input-group-btn">
                    <button class="btn btn-secondary" type="button">Go!</button>
                  </span>
                </div>
              </div>
            </div> -->
          </div>
          <div class="clearfix"></div>

          <div class="row">
            <div class="col-md-12 col-sm-12">

              <div class="x_panel">
                <div class="x_title">
                  <h2>Department Info</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/addnewdepartment") %>"><i class="fa fa-plus"></i> New Department</a>
                    </li>
                  </ul>
                  <div class="clearfix"></div>
                </div>

                <div class="x_content">

                  <!--table1 palcement-->
                  <table class="table font-weight-bold">
                    <thead>
                      <tr>
                       
                        <th>Name</th>
                        <th>Description</th>
                        <th>Actions</th>

                      </tr>
                    </thead>
                    <tbody>
                  <c:forEach var="department" items="${uDepartmentList}">
                                <tr>
                                  

                                 

                                  <td><a href="departmentdetail?name=${department.name}">
                                      <c:out value="${department.name}"/></a>
                                  </td>
                                  <td>
                                    <c:out value="${department.description}"/>
                                  </td>
                                  <td>
                                    <a href="/ruzzantetms/editdepartment?name=${department.name}"> <button class="btn btn-primary" type="button">Edit</button></a>
                                  </td>

                                </tr>
                              </c:forEach>
                     
                          
                          </tbody>
                        </table>

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

</body>
<!-- <script type="text/javascript" src="./js/ajax_depaetment.js"></script> -->
</html>