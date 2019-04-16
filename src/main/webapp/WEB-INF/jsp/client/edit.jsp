<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="operation" scope="page" value="${not empty client.clientId ? 'Edit' : 'Create'}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:out value="${operation}"></c:out> Client</title>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.js"></script>
        <script src="/js/crudApp.js" ></script>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    </head>
    <body>
        <div id="content" class="container">
            <div class="row">
                <h1><c:out value="${operation}"></c:out> Client</h1>
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

                <form:form id="clientForm" class="needs-validation" novalidate="novalidate" action="${pageContext.request.contextPath}/client/edit" method="POST" modelAttribute="client">
                    <form:input type="hidden" name="clientId" path="clientId"/>

                    <div class="form-group">
                        <label class="control-label" for="name">Client Name:</label>
                        <div>
                            <form:input cssClass="form-control" type="text" name="name" path="name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="websiteUri">Website URI:</label>
                        <div>
                            <form:input cssErrorClass="is-invalid" cssClass="form-control" type="text" name="websiteUri" path="websiteUri"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="phoneNumber">Phone Number:</label>
                        <div>
                            <form:input cssErrorClass="is-invalid" cssClass="form-control" type="text" name="phoneNumber" path="phoneNumber"/>
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
                        <label class="control-label" for="personId">Contacts:</label>
                        <div>
                            <form:select multiple="true" id="personId" cssErrorClass="is-invalid" cssClass="form-control" path="contacts">
                                <form:options itemValue="personId" itemLabel="displayName" items="${contacts}"/>
                            </form:select>
                        </div>
                    </div>

                    <button id="submitButton" name="submit" class="btn btn-primary" value="Submit">Submit</button>
                    <%--todo: this cancel button needs to be fixed to go back to the client list rather than the person list--%>
                    <button id="cancelButton" name="Cancel" class="btn btn-secondary" value="Cancel">Cancel</button>
                    <%--uncomment to test server-side validation--%>
                    <%--<button id="clientValidationToggleButton" name="clientValidation" value="Client Validation">Turn client validation off</button>--%>
                </form:form>
            </div>
        </div>

    </body>
</html>
