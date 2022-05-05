<%@ page contentType="text/html;charset=utf-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="layout/header.jsp" />
    <title>View Activity | Ruzzante TMS</title>
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
                            <h3>View activity</h3>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    

                    <div class="row">
                        <div class="col-md-8 col-sm-8 ">

                            <div class="x_panel ">
                                <div class="x_title">
                                    <h2>Activity details</h2>

                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a href="/ruzzantetms/activityedit?activityid=${activity.activityID}"><i class="fa fa-wrench"></i> Edit this activity</a>
                                        </li>
                                    </ul>

                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content ">
                                    <div class="row">
                                        <div class="col-sm-10 offset-sm-1">
                
                
                                            <!-- transaction details --> 

                                            <table class="table table-hover">


                                                <tbody>

                                                  <tr>
                                                    <th scope="row">Activity ID</th>
                                                    <td>${activity.activityID} </td>
                                                  </tr>

                                                  <tr>
                                                    <th scope="row">Title</th>
                                                    <td>${activity.title}</td>
                                                  </tr>

                                                  <tr>
                                                    <th scope="row">Type</th>
                                                    <td>${activity.type}</td>
                                                  </tr>


                                                  <tr>
                                                    <th scope="row">Assigned Play</th>
                                                    <td>${play.title}</td>
                                                  </tr>

                                                  <tr>
                                                    <c:set value="${activity.date}" var="dateString" />
                                                    <fmt:parseDate value="${dateString}" var="dateObject" pattern="yyyy-MM-dd HH:mm:ss" />

                                                    <th scope="row">Date</th>
                                                    <td><fmt:formatDate value="${dateObject }" pattern="dd/MM/yyyy" /></td>
                                                  </tr>

                                                  <tr>
                                                    <th scope="row">Start time</th>
                                                    <td><fmt:formatDate value="${dateObject }" pattern="HH:mm" /></td>
                                                  </tr>


                                                  <tr>
                                                    <th scope="row">Location</th>
                                                    <td>${activity.location}</td>
                                                  </tr>

                                                  <tr>
                                                    <th scope="row">Description</th>
                                                    <td>${activity.description}</td>
                                                  </tr>


                                                  <tr>
                                                    <th scope="row">PrivacyTag</th>
                                                    <td>${activity.privacyTag}</td>
                                                  </tr>

                                                  <tr>
                                                    <th scope="row">Audience Size</th>
                                                    <td>${activity.audienceSize}</td>
                                                  </tr>

                                                  <tr>
                                                    <th scope="row">Max Audience</th>
                                                    <td>${activity.maxAudience}</td>
                                                  </tr>

                                                  <tr>
                                                    <th scope="row">Season</th>
                                                    <td>${activity.seasonID}</td>
                                                  </tr>

                                                </tbody>

                                              </table>                                         


                                        </div>
                                        
            
            
                                    </div>
            
                                </div>
            

                            </div>

                        </div>

                        <div class="col-md-4 mb-3">

                            <div class="x_panel h-100">
                                <div class="x_title">
                                    <h2>Related Play poster</h2>

                                    <ul class="nav navbar-right panel_toolbox">
                                        <li><a href="playview?playid=${play.playid}" ><i class="fa fa-arrow-right"></i> View play</a>
                                        </li>
                                    </ul>

                                    <div class="clearfix"></div>
                                </div>

                                <div class="x_content">
                                    
                                    <div class="row">
                                        <div class="col-sm-10 offset-sm-1">
                                            
                
                                            <!-- play poster --> 
                                            <img src="${play.posterimage}" class ="play-poster" >

                                        </div>
                                        
                                    </div>
            
                                </div>
            

                            </div>

                        </div>
                    </div>

                    <div class="row" >
                      <div class="col-md-12 col-sm-12">

                        <div class="x_panel">
                            <div class="x_title">
                                <h2>Related transactions</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/transactionnew?activityid=") %>${activity.activityID}"><i class="fa fa-plus"></i> New Transaction</a>
                                    </li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>

                            <div class="x_content">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="card-box table-responsive">
                                <!--table1 palcement-->
                                    <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive table-hover nowrap" cellspacing="0" width="100%">
                                        <thead>
                                            <tr>
                                                <th>Name</th> <!-- from here, link to the transaction details and edit page-->
                                                <th>Amount</th>
                                                <th>Date</th>
                                                <th>Managed By</th> <!-- from here, link to the related activity details and edit page-->
                                                <th>Actions</th>
                                            </tr>
                                        </thead>

                                        <tbody id="transactions_table">
                                            <!--<tr onclick="openDetails(12568)">
                                                <td><a class="transaction-item-ref" >Rehearsals rooms, AY 2018/2019</a></td> 
                                                <td>-850,55</td>
                                                <td>15/09/2018</td>
                                                <td>Jack Linn</td>
                                                    <td><a href="transactionedit">Edit</a></td>
                                            </tr>

                                            <tr onclick="openDetails(12558)">
                                                <td>Facebook sponsorship</td>
                                                <td>-20</td>
                                                <td>25/04/2019</td>
                                                <td>Marta Brown</td>
                                                <td><a href="transactionedit">Edit</a></td>
                                            </tr>

                                            <tr>
                                                <td>Event offers</td>
                                                <td>30</td>
                                                <td>11/05/2019</td>
                                                <td>Roland Berry</td>
                                                <td><a href="transactionedit">Edit</a></td>
                                            </tr> -->
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
            <!-- /page content -->
            <jsp:include page="layout/footer.jsp" />
        </div>
    </div>

    <jsp:include page="layout/assets_footer.jsp" />
    <!--<script type="text/javascript" src="./js/ajax_activityview.js"></script>-->

    <script>
        var httpRequest; // gets the list of transactions related to the specified activity

        var url = "./content/transaction/activity/${activity.activityID}";

        httpRequest = new XMLHttpRequest();
        /*
        if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
        }
        */
        httpRequest.onreadystatechange = listTransactions;
        httpRequest.open('GET', url);
        httpRequest.send();



        function listTransactions() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
        
            if (httpRequest.status == 200) {


                var tbody = document.getElementById('transactions_table');

                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData['resource-list'];

                for (var i = 0; i < resource.length; i++) {
                var transaction = resource[i].transaction;

                var tr = document.createElement('tr');

                var td_name = document.createElement('td');
                td_name.appendChild(document.createTextNode(transaction['name']));
                tr.appendChild(td_name);

                var td_amount = document.createElement('td');
                td_amount.appendChild(document.createTextNode(transaction['amount']));
                tr.appendChild(td_amount);

                var td_date = document.createElement('td');
                td_date.appendChild(document.createTextNode(transaction['date']));
                tr.appendChild(td_date);

                var td_username = document.createElement('td');
                td_username.appendChild(document.createTextNode(transaction['username']));
                tr.appendChild(td_username);

                var td_edit = document.createElement('td');

                var a = document.createElement('a');
                a.href = "transactionedit?transid=" + transaction['transID']

                var edit_button = document.createElement('button');
                edit_button.className = "btn btn-primary small-tab-btn";
                edit_button.type="button";
                edit_button.innerHTML= 'Edit';

                a.appendChild(edit_button);
                td_edit.appendChild(a);
                tr.appendChild(td_edit);

                tr.className = "transEntry";
                tr.id= transaction['transID'];

                tbody.appendChild(tr);

                }


            } else {
                alert('There was a problem with the request.');
            }
        }
        }
    </script>

</body>
</html>