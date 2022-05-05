<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div class="col-md-3 left_col menu_fixed">
    <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
            <a class="site_title"  href="<%= response.encodeURL(request.getContextPath() + "/dashboard") %>" >
                <img src="images/logo_clear2.png"><span class="sidebar_title">Ruzzante TMS</span>
            </a>
        </div>

        <div class="clearfix"></div>

        <!-- menu profile quick info -->
        <div class="profile clearfix">
            <div class="profile_pic">
                <img src=${uPhoto} alt="..." class="img-circle profile_img">
            </div>
            <div class="profile_info">
                <span>Welcome,</span>
                <h2> ${uName} ${uSurname} </h2>
            </div>
        </div>
        <!-- /menu profile quick info -->

        <br />

        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">
                <h3>${uGroup}</h3> <!-- ${uRole} -->
                <ul class="nav side-menu">
                    <li>
                        <a href="<%= response.encodeURL(request.getContextPath() + "/dashboard") %>">
                            <i class="fa fa-home"></i> Dashboard
                        </a>
                    </li>

                    <li>

                    <c:if test = "${uGroup == 'Company Manager' || uGroup == 'Director'}">

                    <li><a style="text-transform: none; "><i class="fa fa-bar-chart-o"></i> Financial balance <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            
                            <li>
                                <a href="<%= response.encodeURL(request.getContextPath() + "/financial") %>">
                                    Financial Dashboard
                                </a>
                            </li>

                            <li>
                                <a href="<%= response.encodeURL(request.getContextPath() + "/transactionlist") %>">
                                    All Transactions
                                </a>
                            </li>


                        </ul>
                    </li>
                    </c:if>


                    <c:if test = "${uGroup == 'Company Manager' || uGroup == 'Crew' || uGroup == 'Director'}">
                    <li>
                        <a href="<%= response.encodeURL(request.getContextPath() + "/itemlist") %>">
                            <i class="fa fa-archive"></i> Items
                        </a>
                    </li>
                    

                    <li>
                        <a href="<%= response.encodeURL(request.getContextPath() + "/departmentlist") %>">
                            <i class="fa fa-building"></i> Departments
                        </a>
                    </li>
                    </c:if>

                    <li>
                        <a href="<%= response.encodeURL(request.getContextPath() + "/playlist") %>">
                            <i class="fas fa-theater-masks"></i> Play/Events
                        </a>
                    </li>

                    <c:if test = "${uGroup == 'Company Manager' || uGroup == 'Director'}">
                    <li>
                        <a href="<%= response.encodeURL(request.getContextPath() + "/activitylist") %>">
                            <i class="fa fa-calendar-check-o"></i> Activities
                        </a>
                    </li>
                    </c:if>

                    
                    <c:if test = "${uGroup == 'Company Manager'}">
                    <li>
                        <a href="<%= response.encodeURL(request.getContextPath() + "/members") %>">
                            <i class="fa fa-users"></i> Members
                        </a>
                    </li>
                    </c:if>

                </ul>
            </div>
        </div>
        <!-- /sidebar menu -->
    </div>
</div>
<input type="hidden" name="username" value="${user}" id="sessionUserId" />

