<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>Profile | Ruzzante TMS</title>
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
                                    <h2>Personal Info</h2>
                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    <div class="row">

                                        <div class="col-md-3 col-sm-3 profile_right">
                                            <div class="profile_img">
                                              <div id="crop-avatar">
                                                <!-- Current avatar -->
                                                <img class="img-responsive avatar-view" src=${uPhoto} alt="Avatar" style="height:215px" title="Change the avatar">
                                              </div>
                                            </div>
                                            <br />
                                            <!--
                                            <button class="input-group-btn" data-toggle="tooltip" title="" data-original-title="Import image">
                                                <input type="file" class="fa fa-upload">                                                         
                                            </button>           
                                            <label class="custom-file-upload-usredit">
                                                <input type="file" style  = "display:none;"/>
                                                <i class="fa fa-upload"></i> Upload Image
                                            </label>                                                                                        
                                            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="Import image">
                                                <span class="fa fa-upload"></span>
                                            </span> -->
                                        </div>                            

                                        <div class="col-sm-9 profile_right">

                                            <!-- page content --> 
                                            <form class="form-horizontal form-label-left" action="" id="formEditInfo" method="post" name="newEntry">
                                        
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Name</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" data-validate-length-range="1" name="name" value=${uName} disabled/>
                                                </div>
                                            </div>                                                                                      
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Surname</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" data-validate-length-range="1" name="surname" value=${uSurname} disabled/>
                                                </div>
                                            </div>                                      
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Email</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" name="email" class='email' type="email" required="required" />
                                                </div>
                                            </div>                 
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Phone Number</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" type="tel" class='tel' name="phoneNumber" required="required"
                                                        data-validate-length-range="8,20" />
                                                </div>
                                            </div>
                                            
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Upload Image</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" type='photo' name="photo" />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Group</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <select name="userGroup" class="form-control" disabled >
                                                        <option value="Actor">Actor</option>
                                                        <option value="Crew">Crew</option>
                                                        <option value="Director">Director</option>
                                                        <option value="Company Manager">Company Manager</option>    
                                                    </select>                                                    
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-md-12 col-sm-12">
                                                </div>
                                            </div>


                                            <div class="x_title">
                                                <h2>Change Password</h2>
                                                <div class="clearfix"></div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">New Password</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" type='password' name="pswHash1" required="required" />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Confirm Password</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" type='password' name="pswHash2" required="required" />
                                                </div>
                                            </div> 
                                                    
                                            <div class="form-group">
                                                <div class="col-md-6 offset-md-4">                                                   
                                                    <button type='button' id="userEditMember" class="btn btn-primary">Confirm</button>                                                  
                                                </div>
                                            </div>
                                        </form> 
                                        
                                        <div id="servMessage"></div>     
                                                                          

                                        </div>
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>

                    <!--
                    <div class="divider-dashed"></div>

                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Change Password</h2>
                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-9">
                    -->                            
                                            <!-- page content --> 
                                            <!--   
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">New Password</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" type='password' name="pswHash1" required="required" />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Confirm Password</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" type='password' name="pswHash2" required="required" />
                                                </div>
                                            </div> 

                                            <div class="form-group">
                                                <div class="col-md-6 offset-md-4">                                                     
                                                    <button type="button" id="userEditMemberForm" class="btn btn-primary">Confirm</button>                                                    
                                                </div>
                                            </div>
                                        </form>
                                        <div id="servMessage"></div
                                        -->


                                        </div>
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>
                    



                </div>
            </div>

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
            <script src="./js/ajax_useronly.js"></script>
            <script>load_member_data();</script>
            <script>
                $( document ).ready(function() {
                console.log("ready!");

                $("#userEditMember").click(function(e) {

                //e.preventDefault();
                console.log("Entered click function");
                
                var editform = document.getElementById('formEditInfo');

                var fData = new FormData(editform);

                x={};
                /**
                for(let [name, value] of fData) {
                    x[name] = `${value}`; 
                    }
                */
                x.phoneNumber = fData.get("phoneNumber");
                x.photo = fData.get("photo");
                x.email = fData.get("email");
                x.username = (new URLSearchParams(window.location.search)).get('username');
                
                console.log(x);

                var jsonData = JSON.stringify(x);
                jsonData = '{ "member": ' + jsonData + "}";
                    
                //TRADITIONAL AJAX CALL
                
                var url = "./content/member/"+(new URLSearchParams(window.location.search)).get('username');
                console.log(url);

                var xhreq = new XMLHttpRequest();
                xhreq.onreadystatechange = alertState;

                xhreq.open('PUT', url);
                xhreq.setRequestHeader('Content-type', 'application/json');
                xhreq.setRequestHeader('Data-type', 'application/json');
                xhreq.send(jsonData);

                function alertState() {
                    if (xhreq.readyState === XMLHttpRequest.DONE) {
                        if (xhreq.status == 200) {
                            var jsonData = JSON.parse(xhreq.responseText);
                            var displayData = "Your personal information has been updated successfully";            
                            addSuccessMessage(displayData);
                        } else {
                            addErrorMessage();
                            console.log(xhreq.responseText);
                            console.log(xhreq.status);
                        }
                    }
                };

                /**
                 * Check PASSWORD
                */                 
                console.log("Entered click function");
                
                var editform = document.getElementById('formEditInfo');

                var pData = new FormData(editform);

                y={};
                /**
                for(let [name, value] of fData) {
                    x[name] = `${value}`; 
                    }
                */
                y.pswHash1 = pData.get("pswHash1");
                y.pswHash2 = pData.get("pswHash2");
                y.username = (new URLSearchParams(window.location.search)).get('username');
                               
                if(y.pswHash1 != "") {
                    if(y.pswHash1 != y.pswHash2 || y.pswHash2 == "") {
                        // Error Message
                        addErrorMessage("The two passwords must be the same");
                        console.log("Password do not match.");
                        return;
                    }

                    y.pswHash = y.pswHash1;
                    console.log(y);
                    
                    var jsonData = JSON.stringify(y);
                    jsonData = '{ "member": ' + jsonData + "}";
                        
                    //TRADITIONAL AJAX CALL
                    
                    var url = "./content/member/"+(new URLSearchParams(window.location.search)).get('username');
                    console.log(url);

                    var xhreq = new XMLHttpRequest();
                    xhreq.onreadystatechange = alertState;

                    xhreq.open('PUT', url);
                    xhreq.setRequestHeader('Content-type', 'application/json');
                    xhreq.setRequestHeader('Data-type', 'application/json');
                    xhreq.send(jsonData);

                    function alertState() {
                        if (xhreq.readyState === XMLHttpRequest.DONE) {
                            if (xhreq.status == 200) {
                                var jsonData = JSON.parse(xhreq.responseText);
                                var displayData = "Your personal information has been updated successfully";            
                                addSuccessMessage(displayData);
                            } else {
                                addErrorMessage();
                                console.log(xhreq.responseText);
                                console.log(xhreq.status);
                            }
                        }
                    };
                }
              });
            });
            </script>

            <!-- /page content -->
            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />


</body>

</html>