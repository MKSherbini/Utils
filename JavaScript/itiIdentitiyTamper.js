// ==UserScript==
// @name         ITI Identity Tamper
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       MKSherbini
// @match        https://*.iti.gov.eg/*
// @icon         https://www.google.com/s2/favicons?sz=64&domain=gov.eg
// @grant        none
// ==/UserScript==
// @match        http://*/*
// @match        https://*/*

(function () {
    'use strict';

    function checkUrl(url) {
        return document.URL.toLowerCase().startsWith(url.toLowerCase());
    }

    let mainInterval = setInterval(function () {
        if (checkUrl("https://identity.iti.gov.eg/account/login")) {
            console.log("login")
            let pass = "msherbini";
            let user = "xx";
            let passField = document.querySelector('input#Input_UserNameOrEmail');
            let userField = document.querySelector('input#Input_Password');
            let submitButton = document.querySelector('button#login__submit');
            passField.value = pass;
            userField.value = user;
            setInterval(function () {
                submitButton.click();
            }, 500);
        } else if (checkUrl("https://internal.iti.gov.eg/home")) {
            console.log("home")
            setInterval(function () {
                let loginDiv = Array.from(document.querySelectorAll("cm-action-menu div.menu-item__head div"))
                    .find(el => el.textContent.trim() === "Login");

                if (loginDiv) {
                    loginDiv.click();
                } else {
                    window.location.href = "https://internal.iti.gov.eg/interviews/interviews/interviewing-day-management";
                }
            }, 500);
        } else if (checkUrl("https://internal.iti.gov.eg/interviews/interviews/interviewing-day-management")) {
            console.log("interview")
            setTimeout(function () {
                document.querySelector(".textbox__control-input").click();
                document.querySelector(".date-picker__calendar").querySelector(".active").click();
                clearInterval(mainInterval);
            }, 2500);
        }
    }, 1000);

})();
