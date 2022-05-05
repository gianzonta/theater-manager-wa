<!DOCTYPE html>
<html lang="en">

<head>
  <jsp:include page="layout/header.jsp" />
  <title>Add New Member - Ruzzante TMS</title>
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
              <h3>Add new Member</h3>
            </div>
          </div>
          <div class="clearfix"></div>

          <div class="row">
            <div class="col-md-9 col-sm-9">
              <div class="x_panel">
                <!--<div class="x_title">
                      <h2>Edit User data</h2>
                      <div class="clearfix"></div>
                    </div>-->
                <div class="x_content">
                  <form class="" action="" method="post" id="newMemberForm">
                    <span class="section">Personal Info</span>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Name<span
                          class="required">*</span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" data-validate-length-range="1" name="name" required="required" >
                      </div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Surname<span
                          class="required">*</span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" data-validate-length-range="1" name="surname" required="required" >
                      </div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Birth Date<span
                          class="required">*</span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" class='date' type="date" name="birthDate" required='required'></div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">email<span
                          class="required">*</span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" name="email" class='email' required="required" type="email" ></div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Telephone<span
                          class="required">*</span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" type="tel" class='tel' name="phoneNumber" required='required'
                          data-validate-length-range="8,20" ></div>
                    </div>

                    <span class="section">Ruzzante Related</span>

                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Hiring Date<span
                          class="required">*</span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" class='date' type="date" name="hiringDate" required='required'>
                      </div>
                    </div>
                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">Username<span
                          class="required">*</span></label>
                      <div class="col-md-6 col-sm-6">
                        <input class="form-control" data-validate-length-range="1" id="username" name="username"
                          required="required">
                      </div>
                    </div>

                    <div class="field item form-group">
                      <label class="col-form-label col-md-3 col-sm-3  label-align">User Group<span
                          class="required">*</span></label>
                      <div class="col-md-6 col-sm-6 ">
                        <select name="userGroup" class="form-control" required='required'>
                          <option value="Actor">Actor</option>
                          <option value="Crew">Crew</option>
                          <option value="Director">Director</option>
                          <option value="Company Manager">Company Manager</option>
                        </select></div>
                    </div>

                    <div class="form-group row">
                      <label class="col-md-3 col-sm-3  control-label">
                        <br>
                        <small class="text-navy"></small>
                      </label>
                      <!---->
                      <div class="col-md-9 col-sm-9 ">
                        <div class="checkbox">
                          <label>
                            <input type="checkbox" name="isUnipdStudent" value=true class="flat"> Is Unipd
                          </label>
                        </div>
                        <div class="checkbox">
                          <label>
                            <input type="checkbox" name="isMemberPro" value=true class="flat"> Is Pro
                          </label>
                        </div>
                      </div>
                    </div>

                    <div class="ln_solid"></div>
                    <div class="form-group">
                      <div class="col-md-6 offset-md-3">
                        <button type='submit' class="btn btn-primary">Submit</button>
                        <button type='button' class="btn btn-success" onclick="window.location.href='./members'">Back</button>
                  
                      </div>
                    </div>
                  </form>
                  <div id="servMessage"></div>
                </div>
              </div>
            </div>

            <div class="col-md-3 col-sm-3">

              <div class="x_panel">
                <div class="x_title">
                  <h2>Departments</h2>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">

                  <form id="user2dep_form">

                    <div class="col-md-12 col-sm-12">
                      <div class="card-box table-responsive">
                        <table class="table table-striped table-bordered bulk_action">
                          <tbody id="dep_list">
                            <!--filled by JS-->

                          </tbody>
                        </table>
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






    <jsp:include page="layout/footer.jsp" />
  </div>
  </div>

  <jsp:include page="layout/assets_footer.jsp" />
</body>

<script src="./js/addnewuser.js"></script>
<script>get_dep_list();</script>
</html>