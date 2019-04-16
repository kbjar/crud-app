<%-- 
    Document   : list
    Created on : Apr 22, 2011, 2:25:22 PM
    Author     : FMilens
--%>

<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Person Listing</title>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.js"></script>
        <script src="/js/crudApp.js" ></script>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2">
                    <ul class="nav">
                        <li class="nav-item">
                            <a class="nav-link active" href="#">Person Listing</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/client/list">Client Listing</a>
                        </li>
                    </ul>
                </div>

                <div class="col-sm-8">
                    <h1>Person Listing</h1>
                    <a href="${pageContext.request.contextPath}/person/create" class="btn btn-primary">Create New Person</a>
                    <c:choose>
                        <c:when test="${fn:length(persons) gt 0}">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Email Address</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${persons}" var="person">
                                        <tr>
                                            <td>${person.firstName}</td>
                                            <td>${person.lastName}</td>
                                            <td>${person.emailAddress}</td>
                                            <td>
                                                <button type="button" class="btn btn-default" onclick="window.location.href='${pageContext.request.contextPath}/person/edit/${person.personId}';">Edit Person</button>
                                                <button type="button" class="btn btn-default" data-toggle="modal"
                                                        data-person-person-id="${person.personId}"
                                                        data-person-first-Name="${person.firstName}"
                                                        data-person-last-name="${person.lastName}"
                                                        data-target="#deletePersonModal">Delete Person</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p>No results found.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="deletePersonModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteModalLabel">Delete Person</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        You are about to delete the person <span id="modalPersonFirstName"></span>
                        <span id="modalPersonLastName"></span>: Are you sure?
                        <form id="modalForm" action="${pageContext.request.contextPath}/person/delete" method="post">
                            <input type="hidden" id="personId" name="personId" value=""/>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="button" id="modalSubmitButton" class="btn btn-primary">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
