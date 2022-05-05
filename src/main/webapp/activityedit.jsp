<%@ page contentType="text/html;charset=utf-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE htxml>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>Edit Activity | Ruzzante TMS</title>
    <jsp:include page="layout/assets_header.jsp" />
</head>

<body class="nav-md">
    <div class="container body">
        <div class="main_container">
            <jsp:include page="layout/sidebar.jsp" />
            <jsp:include page="layout/top_navigation.jsp" />

            <!-- page content -->
            <div class="right_col" role="main">

                <div id="servMessage"></div>

                <div class="">
                    <div class="page-title">
                        <div class="title_left">
                            <h3>Manage Activity</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    

                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Activity edit</h2>
                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    <div class="row">
                                        <div class="col-sm-12">

                                            <!-- page content --> 
                                            <form class="form-horizontal form-label-left" action="" method="post" name="newEntry" id="updateActEntry">
                                        
                                                <div class="field item form-group">
                                                    <label class="col-form-label col-md-3 col-sm-3  label-align">Activity ID</label>
                                                    <div class="col-md-6 col-sm-6">
                                                        <input class="form-control" name="activityID" disabled id = "activityID" value="${activity.activityID}"/>
                                                    </div>
                                                </div>
    
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Title</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="title" value="${activity.title}"/>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Type</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="type" value="${activity.type}"/>
                                                </div>
                                            </div>
                                            
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Assigned play (if any)</label>    
                                                <div class="col-md-6 col-sm-6">
                                                    <select class="select2_group form-control" name="play" id="play_list">
                                                        <option value="${play.playid}">${play.title}</option>
                                                        <option value="null">None</option>       
                                                    </select>
                                                    
                                                </div>
                                            </div>

                                            <c:set value="${activity.date}" var="dateString" />
                                            <fmt:parseDate value="${dateString}" var="dateObject" pattern="yyyy-MM-dd HH:mm:ss" />
                         
                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Date</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" class="date" type='date' name="date" value='<fmt:formatDate value="${dateObject }" pattern="yyyy-MM-dd" />' />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Start Time</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" type='time' name="time" value='<fmt:formatDate value="${dateObject }" pattern="HH:mm" />' />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Location</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control"  name="location" value="${activity.location}"/>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Description</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <textarea class="form-control" name="description" rows="3" >${activity.description}</textarea>
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">PrivacyTag</label>    
                                                <div class="col-md-6 col-sm-6">
                                                    <select class="select2_group form-control" name="privacyTag" value="${activity.privacyTag}">
                                                            <option value="Pro">Pro</option>
                                                            <option value="Uni">Uni</option>
                                                            <option value="Public">Public</option>
                                                            <option value="Private">Private</option>
                                                            <option value="Private">Restricted</option>
                                                    </select>
                                                    
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Audience Size</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" name="audienceSize" type="number" step="1" value="${activity.audienceSize}" />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Max. Audience</label>
                                                <div class="col-md-6 col-sm-6">
                                                    <input class="form-control" name="maxAudience" type="number" step="1" value="${activity.maxAudience}" />
                                                </div>
                                            </div>

                                            <div class="field item form-group">
                                                <label class="col-form-label col-md-3 col-sm-3  label-align">Season</label>    
                                                <div class="col-md-6 col-sm-6">

                                                    <input class="form-control" name="seasonID" name="seasonID" value="${activity.seasonID}" disabled/>
                                                    
                                                </div>
                                            </div>

                                            <div class="divider-dashed"></div>
    
                                            <div class="form-group">
                                                <div class="col-md-6 offset-md-4"> 
                                                    <button type='reset' class="btn btn-info">Reset Fields</button>
                                                    <button type='submit' class="btn btn-primary">Submit Edit</button>
                                                    <button type='button' class="btn btn-danger" id="delete">Delete Activity</button>
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

                        </div>

                    </div>


                </div>
            </div>
            <!-- /page content -->
            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />


    <script type="text/javascript" src="./js/ajax_activityedit.js"></script>


</body>

</html>