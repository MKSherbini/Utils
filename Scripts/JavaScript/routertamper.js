// ==UserScript==
// @name         Router Tamper
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        https://192.168.1.1/*
// @grant        none
// ==/UserScript==
// @match        http://*/*
// @match        https://*/*

(function () {
    'use strict';
    //setTimeout(function(){ window.location.href = window.location.href; }, 5000);
    if (document.URL == "https://192.168.1.1/") {
        var pass = "ETIS_0502544213";
        var user = "admin";
        var passField = document.querySelector('input[type="password"]');
        var userField = document.querySelector('input[name="Username"]');
        passField.value = pass;
        userField.value = user;
        //frame.contentWindow.document.getElementById("link_Admin_0_1").click();
        //Login
        SubmitFormWithChallange();
    } else {

        var hi = 0, wd = 0;
        var btnArr = [
            CreateBtn("Restart", () => {

                document.getElementById("listfrm").contentWindow.document.getElementById("link_Admin_3").click();
                document.getElementById("listfrm").contentWindow.document.getElementById("link_Admin_3_1").click();

                // let rebootBtn = document.getElementById("contentfrm").contentWindow.document.getElementsByName("btnReboot")[0];
                // rebootBtn.click();
                // window.confirm = function (e) { return true };
                // return true;
            }),
            CreateBtn("Stats", () => {
                document.getElementById("listfrm").contentWindow.document.getElementById("link_Admin_0").click();
                document.getElementById("listfrm").contentWindow.document.getElementById("link_Admin_0_1").click();
                document.getElementById("listfrm").contentWindow.document.getElementById("td2_Admin_0_1_1").click();
            }),
        ];

        // console.log(btnArr.length);
        // console.log(btnArr[0]);

        try {
            var column = document.getElementById("listfrm").contentWindow.document.getElementsByTagName("body")[0].children[0];
        } catch (error) {
            // console.log(error);
        }

        if (btnArr[0] && column) {
            // console.log(btnArr[0]);
            btnArr.forEach(element => {
                wd += element.clientWidth;

                if (wd > column.clientWidth) {
                    wd = element.clientWidth < column.clientWidth ? element.clientWidth : 0;
                    hi += element.clientHeight + 6;
                }
            });

            column.style.height = column.clientHeight - hi;
        }


    }

    function CreateBtn(name, onclick) {
        var btn = document.createElement("button");
        btn.innerHTML = name;
        btn.onclick = onclick;
        var added = false;
        // () => {
        //     alert("My button clicked !");
        //     return false;
        // };
        try {
            var body = document.getElementById("listfrm").contentWindow.document.getElementsByTagName("body")[0];
            body.appendChild(btn);
            // console.log(body);
            console.log(btn);
            added = true;
            console.log(`added button ${name}`);
        } catch (error) {
            console.log(`Couldn't add button ${name}`);

        }
        if (added) {
            return btn;
        } else {
            return null;
        }
    }

})();