<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>Play/Event list | Ruzzante TMS</title>
    <jsp:include page="layout/assets_header.jsp" />
</head>

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <jsp:include page="layout/sidebar.jsp" />
            <jsp:include page="layout/top_navigation.jsp" />

            <!-- page content -->
            <div class="right_col" role="main">
                <div class="clearflix"></div>
                    <div class="page-title">
                        <div class="title_left">
                            <h3>List of Play or Event</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    

                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                 <div class="x_title">
                                      <h2>Latest</h2>
                                       <ul class="nav navbar-right panel_toolbox">
                                       <li><a href="<%= response.encodeURL(request.getContextPath() + "/new") %>"><i class="fa fa-plus"></i> New Play/Event Type</a>
                                       </li>
                                       </ul>
                                      <div class="clearfix"></div>
                                 </div>

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-12">

                                         <div class="card-box table-responsive">

                                            <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive table-hover nowrap" cellspacing="0" width="100%">
                                              <thead>
                                                      <tr>
                                                        <th>Title</th>
                                                        <th>Description</th>
                                                        <th>Duration</th>
                                                        <th>Edit Play</th>
                                                      </tr>
                                              </thead>
                                                <tbody>
                                                       <!--place here the dinamically built table-->

                              <c:forEach var="play" items="${uPlayList}">
                                <tr>
                                  <td>
                                    <div class="media-body">

                                      <h5><a href="playview?playid=${play.playid}"> <c:out value="${play.title}"/></a></h5>
                                    </div>
                                  </td>

                                  <td>
                                      <c:out value="${play.description}"/>
                                  </td>
                                  <td>
                                    <c:out value="${play.duration}"/>
                                  </td>
                                  <td>
                                    <a href="playedit?playid=${play.playid}"> <button class="btn btn-primary" type="button">Edit</button></a>
                                  </td>

                                </tr>
                              </c:forEach>






                                                        <!--tr>
                                                          <td><a href="playview">Shakespeare in Love</a></td>
                                                            <td>This is an opera that involved...</td>
                                                            <td>45 minutes</td>
                                                            <td>Claudia Penzo</td>
                                                            <td><a href="playedit"> <button class="btn btn-primary" type="button">Edit</button></a></td>
                                                        </tr> -->

                                                        <!--tr>
                                                            <td><a href="playview">Scarecrow</a></td>
                                                            <td>This is an musical that involved...</td>
                                                            <td>Three hours</td>
                                                            <td>John Smith</td>
                                                            <td><a href="playedit"> <button class="btn btn-primary" type="button">Edit</button></a></td>
                                                        </tr>-->

                                                        <!--tr>
                                                            <td><a href="playview">Romeo and Juliet</a></td>
                                                            <td>This is an opera that involved...</td>
                                                            <td>2 hours 30 minutes</td>
                                                            <td>Camilo Moretti</td>
                                                            <td><a href="playedit"> <button class="btn btn-primary" type="button">Edit</button></a></td>
                                                        </tr>-->
                                                        <!--tr>
                                                            <td><a href="playview">Summer Dream</a></td>
                                                            <td>This is a musical...</td>
                                                            <td>2 hours 30 minutes</td>
                                                            <td>Carolina La Spina</td>
                                                            <td><a href="playedit"> <button class="btn btn-primary" type="button">Edit</button></a></td>
                                                        </tr>-->
                                                            <!--tr>
                                                                <td><a href="playview">Mary Poppins</a></td>
                                                                <td>This a monologue...</td>
                                                                <td>2 hours 30 minutes</td>
                                                                <td>Giacomo Lopez</td>
                                                                <td><a href="playedit"> <button class="btn btn-primary" type="button">Edit</button></a></td>
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
            </div>
        </div>                            

            <jsp:include page="layout/footer.jsp" />
    </div>


    <jsp:include page="layout/assets_footer.jsp" />

     <script>
        $(document).ready(automateBadgeColor());


      </script>

</body>

</html>