<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>Member Profile | Ruzzante TMS</title>
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
                            <h3>Member Profile</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>                        

                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>${uName} ${uSurname}</h2>
                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    <div class="row">

                                        <div class="col-md-3 col-sm-3 profile_left">
                                            <div class="profile_img">
                                              <div id="crop-avatar">
                                                <!-- Current avatar -->
                                                <img class="img-responsive avatar-view" src=${uPhoto} alt="Avatar" style="height:150px" title="Profile photo">
                                              </div>
                                            </div>
                                            <br />
                                        </div>                            

                                        <div class="col-sm-9 profile_right">                                         
                                            <table class="table">
                                                <thead>
                                                  <tr>
                                                    <th>Season</th>
                                                    <th>Play</th>
                                                    <th>Task</th>
                                                    <th>Role / Spec. task</th>
                                                   
                                                  </tr>
                                                </thead>
                                                <tbody id="userrole_list">
                      
                                                </tbody>
                                              </table>
                                        </div>
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>

                    <div class="divider-dashed"></div>


                </div>
            </div>
            <!-- /page content -->
            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />

    <input type="hidden" name="username" value="${user}" id="sessionUserId" />
</body>
<script type="text/javascript" src="./js/userviewplays.js"></script>
<script>ajax_userrole_list()</script>

<script>fill_dep_selector()</script>
<script>fill_play_selector()</script>

</html>