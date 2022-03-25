// ==UserScript==
// @name         AnirenaAllMagnets
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        https://www.anirena.com/*
// @icon         https://www.google.com/s2/favicons?sz=64&domain=anirena.com
// @grant        none
// @require http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js
// ==/UserScript==

(function () {
    'use strict';
    let $ = window.jQuery;

    setTimeout(
        () => {
            createBtn("Download All", () => {
                $("img[src='./styles/tracker/imageset/magnet.png']").click();
            });
        }, 100
    )

    function createBtn(name, onclick) {
        try {
            let parent = $("#tabs ul");
            let div = document.createElement('li');
            div.onclick = onclick;
            let a = document.createElement('a');
            a.innerText = name;
            a.href = "#"
            div.appendChild(a);
            parent.append(div);
            console.log(div);
            console.log(`added button ${name}`);
        } catch (error) {
            console.log(`Couldn't add button ${name}, because ${error}`);
        }
    }
})();


// function getElementByXpath(path) {
//     return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
// }
//
// for (let i = 7; i <= 43; i += 2) {
//     getElementByXpath(`/html/body/div[2]/div[${i}]/table/tbody/tr/td[3]/div/a[2]`).click();
// }
