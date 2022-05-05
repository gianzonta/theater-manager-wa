<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <jsp:include page="layout/header.jsp" />
  <title>Dashboard | Ruzzante TMS</title>
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
          <div class="animated flipInY col-lg-3 col-md-3 col-sm-6">
            <div class="tile-stats p-2">
              <div class="count d-flex justify-content-between align-items-center">
                <h3 class="ml-0">Members</h3>
                <span>
                  <c:out value="${uMemberCount}" />
                </span>
              </div>
            </div>
          </div>
          <div class="animated flipInY col-lg-3 col-md-3 col-sm-6">
            <div class="tile-stats p-2">
              <div class="count d-flex justify-content-between align-items-center">
                <h3 class="ml-0">Plays</h3>
                <span>
                  <c:out value="${uPlayCount}" />
                </span>
              </div>
            </div>
          </div>
          <div class="animated flipInY col-lg-3 col-md-3 col-sm-6">
            <div class="tile-stats p-2">
              <div class="count d-flex justify-content-between align-items-center">
                <h3 class="ml-0">Activities</h3>
                <span>
                  <c:out value="${uActivityCount}" /></span>
              </div>
            </div>
          </div>
          <div class="animated flipInY col-lg-3 col-md-3 col-sm-6">
            <div class="tile-stats p-2">
              <div class="count d-flex justify-content-between align-items-center">
                <h3 class="ml-0">Audience</h3>
                <span>
                  <c:out value="${uAudienceSizeCount}" /></span>
              </div>
            </div>
          </div>
        </div>

        <div class="row">

          <div class="col-md-4 mb-3">
            <div class="x_panel h-100">
              <div class="x_title">
                <h2>Current Plays</h2>
                <ul class="nav navbar-right panel_toolbox">
                  <li><a href="<%= response.encodeURL(request.getContextPath() + "/playlist") %>"><i
                        class="fa fa-list"></i> Show All</a></li>
                  <li><a href="<%= response.encodeURL(request.getContextPath() + "/new") %>"><i class="fa fa-plus"></i>
                      New Play</a></li>
                </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content">
                <table class="table table-dark table-borderless table-hover projects" style="font-size: 1rem;">
                  <thead>
                    <tr>
                      <th>Play Name</th>
                      <!--
                      <th><i class="fa fa-users"></i></th>
                      -->
                    </tr>
                  </thead>
                  <tbody>

                    <c:forEach var="play" items="${uPlayList}">

                      <tr>
                        <td>
                          <a href='playview?playid=${play.playid}' class="dashPlays">
                            <c:out value="${play.title}" /></a>
                          <br />
                          <!-- <small>Director: Mary Jane</small> -->
                        </td>
                        <!--
                        <td>18</td>
                        -->
                      </tr>

                    </c:forEach>

                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <div class="col-md-4 mb-3">
            <div class="x_panel h-100">
              <div class="x_title">
                <h2>Upcoming Activities</h2>
                <ul class="nav navbar-right panel_toolbox">
                  <li><a href="<%= response.encodeURL(request.getContextPath() + "/activitylist") %>"><i
                        class="fa fa-list"></i> Show All</a></li>
                  <li><a href="<%= response.encodeURL(request.getContextPath() + "/activitynew") %>"><i
                        class="fa fa-plus"></i> New Activity</a></li>
                </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content">

                <c:forEach var="activity" items="${uActivityList}">
                  <c:set value="${activity.date}" var="dateString" />
                  <fmt:parseDate value="${dateString}" var="dateObject" pattern="yyyy-MM-dd HH:mm:ss" />
                  <article class="media event mb-3">
                    <a class="pull-left date">
                      <p class="month">
                        <fmt:formatDate value="${dateObject}" pattern="MMM" />
                      </p>
                      <p class="day">
                        <fmt:formatDate value="${dateObject}" pattern="dd" />
                      </p>
                    </a>
                    <div class="media-body">
                      <h5>
                        <span class="badge badge-primary">
                          <c:out value="${activity.type}" /></span>
                        <a href="activityview?activityid=${activity.activityID}">
                          <c:out value="${activity.title}" /></a>
                      </h5>
                      <p>
                        <fmt:formatDate value="${dateObject}" pattern="HH:mm" />
                      </p>
                    </div>
                  </article>
                </c:forEach>

              </div>
            </div>
          </div>

          <div class="col-md-4 mb-3">
            <div class="x_panel h-100">
              <div class="x_title">
                <h2>Latest members</h2>
                <ul class="nav navbar-right panel_toolbox">
                  <li><a href="<%= response.encodeURL(request.getContextPath() + "/members") %>"><i
                        class="fa fa-list"></i> Show All</a></li>
                  <li><a href="<%= response.encodeURL(request.getContextPath() + "/addnewuser") %>"><i
                        class="fa fa-plus"></i> New User</a></li>
                </ul>
                <div class="clearfix"></div>
              </div>
              <ul class="list-unstyled top_profiles scroll-view">


                <c:forEach var="member" items="${uMembersList}">

                  <li class="media event">
                    <a class="pull-left border-aero profile_thumb"><i class="fa fa-user aero"></i></a>
                    <div class="media-body">
                      <a class="title" href="adminedituser?username=${member.username}">
                        <c:out value="${member.name}" />
                        <c:out value="${member.surname}" />
                      </a>
                      <p><strong>
                          <c:out value="${member.hiringDate}" /> </strong> Join Date </p>
                      <p><small>
                          <c:out value="${member.userGroup}" /></small></p>
                    </div>
                  </li>

                </c:forEach>

              </ul>
            </div>
          </div>

          <c:if test="${uGroup == 'Company Manager'}">

            <div class="col-md-12">
              <div class="x_panel">
                <div class="x_title">
                  <h2>Latest transactions</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/transactionlist") %>"><i
                          class="fa fa-list"></i> Show All</a></li>
                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/transactionnew") %>"><i
                          class="fa fa-plus"></i> New Transaction</a></li>
                  </ul>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                  <div class="row">
                    <div class="col-sm-12">
                      <div class="card-box table-responsive">
                        <!--table1 palcement-->
                        <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap"
                          cellspacing="0" width="100%">
                          <thead>
                            <tr>
                              <th>Name</th> <!-- from here, link to the transaction details and edit page-->
                              <th>Amount</th>
                              <th>Date</th>
                              <th>Related activity</th>
                              <!-- from here, link to the related activity details and edit page-->
                              <th>Managed By</th>
                            </tr>
                          </thead>

                          <tbody>

                            <c:forEach var="transaction" items="${uTransactionList}">

                              <tr>
                                <td><a class="transaction-item-ref"
                                    href="transactionedit?transid=${transaction.transID}">
                                    <c:out value="${transaction.name}" /></a></td>
                                <td>
                                  <c:out value="${transaction.amount}" />
                                </td>
                                <td>
                                  <c:out value="${transaction.date}" />
                                </td>
                                <td><a class="transaction-item-ref"
                                    href="activityedit?activityid=${transaction.activityID}">
                                    <c:out value="${transaction.relatedActivity}" /></a>
                                </td>
                                <td>
                                  <c:out value="${transaction.username}" />
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

          </c:if>


        </div>
      </div>
      <!-- /page content -->

      <jsp:include page="layout/footer.jsp" />
    </div>
  </div>

  <jsp:include page="layout/assets_footer.jsp" />
</body>

</html>