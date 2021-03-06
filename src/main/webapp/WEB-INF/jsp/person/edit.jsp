<%-- 
    Document   : edit
    Created on : Apr 22, 2011, 3:04:46 PM
    Author     : FMilens
--%>

<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="operation" scope="page" value="${not empty person.personId ? 'Edit' : 'Create'}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:out value="${operation}"></c:out> Person</title>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.js"></script>
        <script src="/js/crudApp.js" ></script>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    </head>
    <body>
        <div id="content" class="container">
            <div class="row">
                <h1><c:out value="${operation}"></c:out> Person</h1>
                <c:if test="${fn:length(errors) gt 0}">
                    <div class="alert alert-danger" role="alert">
                        <p>Please correct the following errors in your submission:</p>
                        <ul>
                            <c:forEach items="${errors}" var="error">
                                <li class="alert-danger">${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <form:form id="personForm" class="needs-validation" novalidate="novalidate" action="${pageContext.request.contextPath}/person/save" method="POST" modelAttribute="person">
                    <form:input type="hidden" name="personId" path="personId"/>

                    <div class="form-group">
                        <label class="control-label" for="firstName">First Name:</label>
                        <div>
                            <form:input cssClass="form-control" type="text" name="firstName" path="firstName"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="lastName">Last Name:</label>
                        <div>
                            <form:input cssErrorClass="is-invalid" cssClass="form-control" type="text" name="lastName" path="lastName"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="emailAddress">Email Address:</label>
                        <div>
                            <form:input cssErrorClass="is-invalid" cssClass="form-control" type="text" name="emailAddress" path="emailAddress"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="streetAddress">Street Address:</label>
                        <form:input   cssErrorClass="is-invalid" cssClass="form-control" type="text" name="streetAddress" path="streetAddress"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="city">City:</label>
                        <div>
                            <form:input cssErrorClass="is-invalid" cssClass="form-control" type="text" name="city" path="city"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="state">State:</label>
                        <form:input   cssErrorClass="is-invalid" class="form-control" type="text" name="state" path="state"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="zipCode">Zip Code:</label>
                        <div>
                            <form:input cssErrorClass="is-invalid" cssClass="form-control" type="text" name="zipCode" path="zipCode"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label" for="clientId">Client:</label>
                        <div>
                            <form:select id="clientId" cssErrorClass="is-invalid" cssClass="form-control" path="client.clientId">
                                <form:option value="" label="No Selection"/>
                                <form:options itemValue="clientId" itemLabel="name" items="${clients}"/>
                            </form:select>
                        </div>
                    </div>

                    <button id="submitButton" name="submit" class="btn btn-primary" value="Submit">Submit</button>
                    <button id="cancelButton" name="Cancel" class="btn btn-secondary" value="Cancel">Cancel</button>
                    <%--uncomment to test server-side validation--%>
                    <%--<button id="clientValidationToggleButton" name="clientValidation" value="Client Validation">Turn client validation off</button>--%>
                </form:form>
            </div>
        </div>

    </body>
</html>
