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
        <title>Client Listing</title>

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
                            <a class="nav-link active" href="${pageContext.request.contextPath}/person/list">Person Listing</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="#">Client Listing</a>
                        </li>
                    </ul>
                </div>

                <div class="col-sm-8">
                    <h1>Client Listing</h1>
                    <a href="${pageContext.request.contextPath}/client/create" class="btn btn-primary">Create New Client</a>

                    <c:choose>
                        <c:when test="${fn:length(clients) gt 0}">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Client Name</th>
                                    <th>Website</th>
                                    <th>Phone Number</th>
                                    <th>Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${clients}" var="client">
                                    <tr>
                                        <td>${client.name}</td>
                                        <td><a href="${client.websiteUri}">${client.websiteUri}</a></td>
                                        <td><span class="phone">${client.phoneNumber}</span></td>
                                        <td>
                                            <button type="button" class="btn btn-default" onclick="window.location.href='${pageContext.request.contextPath}/client/edit/${client.clientId}';">Edit client</button>
                                            <button type="button" class="btn btn-default" data-toggle="modal"
                                                    data-client-client-id="${client.clientId}"
                                                    data-client-name="${client.name}"
                                                    data-target="#deleteClientModal">Delete Client</button>
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
        <div class="modal fade" id="deleteClientModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteModalLabel">Delete Client</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        You are about to delete the client <span id="modalClientName"></span>:  Are you sure?
                        <form id="modalForm" action="${pageContext.request.contextPath}/client/delete" method="post">
                            <input type="hidden" id="clientId" name="clientId" value=""/>
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
