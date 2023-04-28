<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>generate report</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
       <div class="container">
           <h3>Report Application</h3>
       </div>
       
       <form:form action="search" modelAttribute="searchRequest" method="post">
              
              <table>
                   <tr>
                      <td>Plan Name:</td>
                      <td>
                         <form:select path="planName">
                              <form:option value="">-Select-</form:option>
                              <form:options items="${names}"/>
                         </form:select>
                      </td>
                      
                       
                      
                       <td>Plan Status:</td>
                      <td>
                         <form:select path="planStatus">
                              <form:option value="">-Select-</form:option>
                              <form:options items="${status}"/>
                         </form:select>
                      </td>
                      
                      
                      
                       <td>Gender:</td>
                      <td>
                         <form:select path="gender">
                              <form:option value="">-Select-</form:option>
                              <form:option value="Male">Male</form:option>
                              <form:option value="Fe-Male">Fe-Male</form:option>
                         </form:select>
                      </td>
                      
                      
                      </tr>
                      
                      
                      <tr>
                      <td>Start Date:</td>
                      <td>
                         <form:input path="startDate"/>
                      </td>
                      
                      
                      <td>End Date:</td>
                      <td>
                         <form:input path="endDate"/>
                      </td>
                      </tr>
                      
                      <tr>
                          <td>
                             <input type="submit" value="Search">
                          </td>
                      </tr>
                      
                   
              </table>
       
       </form:form>
       <hr/>
       <hr/>
       Export: <a href="excel">Excel</a>  <a href="">Pdf</a>
       
       <hr/>
       <hr/>
       
       <table class="table table-striped table-hover">
           <thead>
               <tr>
                  <th>Id</th>
                  <th>Holder name</th>
                  <th>Plan Name</th>
                  <th>Plan Status</th>
                  <th>Gender</th>
                  <th>Start Date</th>
                  <th>End Date</th>
                  <th>Benefit Amount</th>
               </tr>
           </thead>
           <tbody>
                <c:forEach items="${plans}" var="plan" varStatus="index">
                       <tr>
                           <td>${index.count}</td>
                           <td>${plan.citizenName}</td>
                           <td>${plan.planName}</td>
                           <td>${plan.planStatus}</td>
                          
                           <td>${plan.gender}</td>
                           <td>${plan.planStartdate}</td>
                           <td>${plan.planEndDate}</td>
                           <td>${plan.benefitAmt}</td>
                           <td></td>
                       </tr>
                </c:forEach>
                <c:if test="${empty plans}">
                     <td colspan="8" style="text-align:center">No Records Found</td>
                </c:if>
           </tbody>
       </table>
       
       
       
       
       
       
       
       

</body>
</html>