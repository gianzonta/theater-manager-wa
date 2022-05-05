<!DOCTYPE html>
<html lang="en">

<head>
  <jsp:include page="layout/header.jsp" />
  <title>Assign Member to Play | Ruzzante TMS</title>
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
              <h3>Assign Member to Play</h3>
            </div>
          </div>
          <div class="clearfix"></div>
          <div class="row">
            <div class="col-md-6 col-sm-6">

              <div class="x_panel">
                <div class="x_title">
                  <h2>Task in the play</h2>
                  <div class="clearfix"></div>
                </div>

                <div class="x_content">
                  <div class="row">
                    <div class="col-sm-12">
                      
                      <form class="form-horizontal form-label-left" action="" method="post" id="addroleform" name="addroleform">
                        <div class="field item form-group">
                          <label class="col-form-label col-md-3 col-sm-3  label-align">Member</label>
                          <div class="col-md-6 col-sm-6">
                            <input class="form-control" id="username" name="username" value="" readonly />
                          </div>
                        </div>

                        <div class="field item form-group">
                          <label class="col-form-label col-md-3 col-sm-3  label-align">Add task</label>
                          <div class="col-md-6 col-sm-6">
                            <select class="select2_group form-control" id="task_selector" name="name">
                            </select>
                          </div>
                        </div>
                        <div class="field item form-group">
                          <label class="col-form-label col-md-3 col-sm-3  label-align ">Role /<br>Specific task</label>
                          <div class="col-md-6 col-sm-6 ">
                            <input type="text" name="role" id="role"
                              class="form-control col-md-12" placeholder="e.g. Amleto, elettrician...">
                          </div>
                        </div>

                        <div class="field item form-group">
                          <label class="col-form-label col-md-3 col-sm-3  label-align ">Play</label>
                          <div class="col-md-6 col-sm-6 ">
                          <select class="select2_group form-control" name="playID" id="play_list">
                          </select>
                         </div>
                        </div>

                        <div class="field item form-group">
                          <label class="col-form-label col-md-3 col-sm-3  label-align ">Season</label>
                          <div class="col-md-6 col-sm-6 ">
                            <select class="select2_group form-control" name="seasonID" id="season_list">
                              <option value="20202021">2020</option>
                              <option value="20192020">2019</option>
                              <option value="20182019">2018</option>

                            </select>
                          </div>
                        </div>

                        <div class="ln_solid"></div>
                        <div class="form-group">
                          <div class="col-md-12 offset-md-2">

                            <button type="submit" class="btn btn-primary">Add role</button>
                            <button type="button" class="btn btn-info" onclick="window.location.href='./members'">Back</button>
                            <button type="button" class="btn btn-danger" onclick=delete_userrole()>Delete Role</button>
                          </div>
                        </div>
                      
                      </form>
                      <div id="servMessage"></div>
                    </div>
                  </div>
                </div>



              </div>

            </div>

            <div class="col-md-6 col-sm-6">

              <div class="x_panel">
                <div class="x_title">
                  <h2>Assigned Plays</h2>
                  <div class="clearfix"></div>
                </div>

                <div class="x_content">

                  <!--table1 placement-->
                  <table class="table table-hover">
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

    </div>
    <!-- /page content -->

    <jsp:include page="layout/footer.jsp" />
  </div>
  </div>

  <jsp:include page="layout/assets_footer.jsp" />
</body>
<script type="text/javascript" src="./js/user2play.js"></script>
<script>fill_play_selector()</script>
<script>fill_dep_selector()</script>


</html>