<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>View Play  | Ruzzante TMS</title>
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
                            <h3>Play View</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>


                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                         <!-- page content -->

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>${play.title} - Play details</h2>

                                        <ul class="nav navbar-right panel_toolbox">
                                            <li><a href="/ruzzantetms/playedit?playid=${play.playid}"><i class="fa fa-wrench"></i> Edit this play</a>
                                             </li>
                                        </ul>

                                       <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    <div class="row">

                                       <!-- play details -->
                                         <div class="col-md-3 col-sm-3 profile_left">
                                            <div class="play_img">
                                              <div id="crop-avatar">
                                                <!-- Current avatar -->
                                                <img class="img-responsive avatar-view" src="${play.posterimage}" alt="Avatar" style="height:242px" title="Poster image">
                                              </div>
                                            </div>
                                        </div>


                                        <div class="col-sm-6 offset-sm-2">

                                            <table class="table table-hover">


                                                <tbody>

                                                      <tr>

                                                        <th scope="row">Play ID</th>
                                                        <td>${play.playid} </td>

                                                      </tr>


                                                      <tr>

                                                        <th scope="row">Title</th>

                                                        <td>${play.title} </td>

                                                      </tr>


                                                      <tr>

                                                        <th scope="row">Description</th>

                                                        <td>${play.description} </td>

                                                      </tr>


                                                      <tr>

                                                        <th scope="row">Duration:</th>

                                                        <td>${play.duration} Minutes</td>

                                                      </tr>

                                                     <!-- <tr>

                                                        <th scope="row">Related activity:</th>

                                                        <td>[feature to be implemented]</td>

                                                      </tr>


                                                      <tr>

                                                        <th scope="row">Related Items:</th>

                                                        <td>[feature to be implemented]</td>

                                                      </tr>


                                                      <tr>

                                                        <th scope="row">Director:</th>

                                                        <td>[feature to be implemented]</td>

                                                      </tr>

                                                        <tr>

                                                        <th scope="row">Members:</th>

                                                        <td>[feature to be implemented]</td>

                                                      </tr>-->
                                                       <tr>

                                                         <th scope="row">Rehearsal:</th>

                                                         <td>${play.rehearsalschedule}</td>

                                                       </tr>

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


</body>

</html>