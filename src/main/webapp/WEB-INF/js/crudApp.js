"use strict";

$(document).ready(function () {

    // format #
    $(".phone").text(function(i, text) {
        text = text.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
        return text;
    });

    $("#deletePersonModal").on("shown.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        // is lowercase from bootstrap attr
        $("#modalPersonFirstName").html("<b>" + button.data("personFirstName") + "</b>");
        $("#modalPersonLastName").html("<b>" + button.data("personLastName") + "</b>");
        $("#personId").val(button.data("personPersonId"));
    });

    $("#deleteClientModal").on("shown.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        // is lowercase from bootstrap attr
        $("#modalClientName").html("<b>" + button.data("clientName") + "</b>");
        $("#deleteClientModal").find("#clientId").val(button.data("clientClientId"));
    });

    $("#modalSubmitButton").click(function() {
        $("#modalForm").submit();
    });

    // clicking a Cancel button will go back to person
    $("#cancelButton").click(function (event) {
        event.preventDefault();
        $(location).attr("href", "/person/list");
    });

    $("#clientValidationToggleButton").click(function (event) {
        event.preventDefault();
        $("#submitButton").toggleClass("cancel");
        if ($("#submitButton").hasClass("cancel")) {
            $(this).html("Turn client validation on");
        } else {
            $(this).html("Turn Client validation off");
        }

    });

    $("#clientForm").validate({
        rules: {
            name: {
                required: true,
                minlength: 1,
                maxlength: 50
            },
            phoneNumber: {
                required: true,
                digits: true,
                // doesn't support international numbers yet....
                minlength: 10,
                maxlength: 10
            },
            websiteUri: {
                required: true,
                //url: true,
                maxlength: 50
            },
            streetAddress: {
                required: true,
                minlength: 1,
                maxlength: 50
            },
            city: {
                required: true,
                minlength: 1,
                maxlength: 50
            },
            state: {
                required: true,
                minlength: 2,
                maxlength: 2
            },
            zipCode: {
                required: true,
                minlength: 5,
                maxlength: 5,
                digits: true
            }
        },
        messages: {
            name: "Please enter a first name",
            phoneNumber: {
                required: "Please enter a phone number",
                minlength: "Phone number area code required"
            },
            websiteUri: "Please enter a website URI",
            streetAddress: "Please enter a street address",
            city: "Please enter a city",
            state: "Please enter a state 2 letter abbreviation",
            zipCode: {
                required: "Please enter a valid zip",
                minlength: jQuery.validator.format("{0} characters required.")
            }
        },

        errorElement: "em",
        errorPlacement: function (error, element) {
            error.addClass("help-block");

            if (element.prop("type") === "checkbox") {
                error.insertAfter(element.parent("label"));
            } else {
                error.insertAfter(element);
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).parent("div").addClass("has-error").removeClass("has-success");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).parent("div").addClass("has-success").removeClass("has-error");
        }
    });

    $("#personForm").validate({
        rules: {
            firstName: {
                required: true,
                minlength: 1,
                maxlength: 50
            },
            lastName: {
                required: true,
                minlength: 1,
                maxlength: 50
            },
            emailAddress: {
                required: true,
                email: true,
                maxlength: 50
            },
            streetAddress: {
                required: true,
                minlength: 1,
                maxlength: 50
            },
            city: {
                required: true,
                minlength: 1,
                maxlength: 50
            },
            state: {
                required: true,
                minlength: 2,
                maxlength: 2
            },
            zipCode: {
                required: true,
                minlength: 5,
                maxlength: 5,
                digits: true
            }
        },
        messages: {
            firstName: "Please enter a first name",
            lastName: "Please enter a last name",
            emailAddress: "Please enter a valid email address",
            streetAddress: "Please enter a street address",
            city: "Please enter a city",
            state: "Please enter a state 2 letter abbreviation",
            zipCode: {
                required: "Please enter a valid zip",
                minlength: jQuery.validator.format("{0} characters required.")
            }
        },

        errorElement: "em",
        errorPlacement: function (error, element) {
            error.addClass("help-block");

            if (element.prop("type") === "checkbox") {
                error.insertAfter(element.parent("label"));
            } else {
                error.insertAfter(element);
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).parent("div").addClass("has-error").removeClass("has-success");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).parent("div").addClass("has-success").removeClass("has-error");
        }
    });
});
