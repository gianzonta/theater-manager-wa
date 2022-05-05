<%@ page contentType="text/html;charset=utf-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>


<html lang="en">

<head>
  <jsp:include page="layout/header.jsp" />
  <title>All the activities | Ruzzante TMS</title>
  <jsp:include page="layout/assets_header.jsp" />
</head>

<body class="nav-md">
  <div class="container body">
    <div class="main_container">
      <jsp:include page="layout/sidebar.jsp" />
      <jsp:include page="layout/top_navigation.jsp" />

      <!-- page content -->
      <div class="right_col" role="main">
        <div class="clearfix"></div>

        <div class="row">
          <div class="animated flipInY col-lg-4 col-md-4 col-sm-6">
            <div class="tile-stats p-2">
              <div class="count d-flex justify-content-between align-items-center">
                <h3 class="ml-0">Total activities</h3>
                <span>${cActivity}</span>
              </div>
            </div>
          </div>
          <div class="animated flipInY col-lg-4 col-md-4 col-sm-6">
            <div class="tile-stats p-2">
              <div class="count d-flex justify-content-between align-items-center">
                <h3 class="ml-0">Audience Index</h3>
                <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${aiActivity}" /></span>
              </div>
            </div>
          </div>
          <div class="animated flipInY col-lg-4 col-md-4 col-sm-6">
            <div class="tile-stats p-2">
              <div class="count d-flex justify-content-between align-items-center">
                <h3 class="ml-0">Total Audience</h3>
                <span>${tActivity}</span>
              </div>
            </div>
          </div>
        </div>

        <div class="row">

                   <div class="col-md-12">
                    <div class="x_panel">
                      <div class="x_title">
                        <h2>Upcoming Activities</h2>

                        <ul class="nav navbar-right panel_toolbox">
                          <li><a href="<%= response.encodeURL(request.getContextPath() + "/activitynew") %>"><i class="fa fa-plus"></i> New Activity</a>
                          </li>
                        </ul>

                        <div class="clearfix"></div>
                      </div>
                      <div class="x_content">
                        

                        <table id="datatable-responsive" class="table table-bordered dt-responsive nowrap ">
                            <thead>
                              <tr>
                                <th>Date</th>
                                <th>Title</th>
                                <th>Location</th>
                                <th>PrivacyTag</th>
                                <th>Actions</th>
        
                              </tr>
                            </thead>
                            <tbody>
                              
                              <!--place here the dinamically built table-->

                              <c:forEach var="activity" items="${uActivityList}">
                                <tr>
                                  <td>
                                    <c:set value="${activity.date}" var="dateString" />
                                    <fmt:parseDate value="${dateString}" var="dateObject" pattern="yyyy-MM-dd HH:mm:ss" />
                                    
                                    <article class="media event mb-3" style="margin-bottom: 0rem!important;">
                                      <a class="pull-left date">
                                          <p class="month" ><fmt:formatDate value="${dateObject }" pattern="MMMM" /></p>
                                          <p class="day"><fmt:formatDate value="${dateObject }" pattern="dd" /></p>
                                      </a>
                                  </article>
                                  </td>

                                  <td>
                                    <div class="media-body">
                                      <p class= "actTime" ><span class="badge badge-secondary event-type" name="activityBadge"><c:out value="${activity.type}"/></span> <fmt:formatDate value="${dateObject }" pattern="HH:mm" /></p>
                                      <h5><a href="activityview?activityid=${activity.activityID}"> <c:out value="${activity.title}"/></a></h5>
                                    </div>
                                  </td>

                                  <td>
                                      <c:out value="${activity.location}"/>
                                  </td>
                                  <td>
                                    <c:out value="${activity.privacyTag}"/>
                                  </td>
                                  <td>
                                    <a href="activityedit?activityid=${activity.activityID}"> <button class="btn btn-primary" type="button">Edit</button></a>
                                  </td>

                                </tr>
                              </c:forEach>

                              
                              
                              <!--<tr>
                                <td>
                                    <article class="media event mb-3" style="margin-bottom: 0rem!important;">
                                        <a class="pull-left date">
                                            <p class="month">April</p>
                                            <p class="day">29</p>
                                        </a>
                                    </article>
                                </td>
                                <td>
                                    <div class="media-body">
                                      <p class= "actTime" ><span class="badge badge-primary event-type">Play</span> 10:00</p>
                                      <h5><a href="activityview"> La farsa dell'oratore palpellius</a></h5>
                                    </div>
                                  </td>
                                  <td>
                                    Odeo Cornaro, Padua
                                </td>
                                <td>
                                    Pro
                                </td>

                                  
                                </tr>
        
                              -->
                                  </tbody>
                                </table>
        
                              </div>
                            </div>
                          </div>

                  <div class="col-md-12">
                    <div class="x_panel">
                      <div class="x_title">
                        <h2>Past Activities</h2>

                        User:<c:out value="${user}"/>

                        <div class="clearfix"></div>
                      </div>
                      <div class="x_content">
        
                        <table id="datatable-responsive" class="table table-bordered dt-responsive nowrap ">
                            <thead>
                              <tr>
                                <th>Date</th>
                                <th>Title</th>
                                <th>Location</th>
                                <th>PrivacyTag</th>
                                <th>Actions</th>
        
                              </tr>
                            </thead>
                            <tbody>

                              <c:forEach var="activity" items="${pActivityList}">
                                <tr>
                                  <td>
                                    <c:set value="${activity.date}" var="dateString" />
                                    <fmt:parseDate value="${dateString}" var="dateObject" pattern="yyyy-MM-dd HH:mm:ss" />
                                    
                                    <article class="media event mb-3" style="margin-bottom: 0rem!important;">
                                      <a class="pull-left date">
                                          <p class="month" ><fmt:formatDate value="${dateObject }" pattern="MMMM" /></p>
                                          <p class="day"><fmt:formatDate value="${dateObject }" pattern="dd" /></p>
                                      </a>
                                  </article>
                                  </td>

                                  <td>
                                    <div class="media-body">
                                      <p class= "actTime" ><span class="badge badge-secondary event-type" name="activityBadge"><c:out value="${activity.type}"/></span> <fmt:formatDate value="${dateObject }" pattern="HH:mm" /></p>
                                      <h5><a href="activityview?activityid=${activity.activityID}"> <c:out value="${activity.title}"/></a></h5>
                                    </div>
                                  </td>

                                  <td>
                                      <c:out value="${activity.location}"/>
                                  </td>
                                  <td>
                                    <c:out value="${activity.privacyTag}"/>
                                  </td>
                                  <td>
                                    <a href="activityedit?activityid=${activity.activityID}"> <button class="btn btn-primary" type="button">Edit</button></a>
                                  </td>

                                </tr>
                              </c:forEach>


                              <!--<tr>
                                <td>
                                    <article class="media event mb-3" style="margin-bottom: 0rem!important;">
                                        <a class="pull-left date">
                                            <p class="month">April</p>
                                            <p class="day">20</p>
                                        </a>
                                    </article>
                                </td>
                                <td>
                                    <div class="media-body">
                                      <p class= "actTime" ><span class="badge badge-primary event-type">Play</span> 10:00</p>
                                      <h5><a href="activityview"> La farsa dell'oratore palpellius</a></h5>
                                    </div>
                                  </td>
                                  <td>
                                    Odeo Cornaro, Padua
                                </td>
                                <td>
                                    Pro
                                </td>
                                <td>
                                  <a href=""> <button class="btn btn-primary" type="button">Edit</button></a>
                              </td>
                                  
                                </tr>-->
        
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
      <!-- /page content -->

      <jsp:include page="layout/footer.jsp" />
    </div>
  </div>

  <jsp:include page="layout/assets_footer.jsp" />

  <script>
    $(document).ready(automateBadgeColor());


  </script>

</body>

</html>