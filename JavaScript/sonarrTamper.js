// ==UserScript==
// @name         Sonarr Tamper
// @namespace    http://tampermonkey.net/
// @version      2024-10-22
// @description  try to take over the world!
// @author       You
// @match        http://mksherbini.ddns.net:8989/*
// @icon         https://www.google.com/s2/favicons?sz=64&domain=sonarr.tv
// @grant        none
// @require http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js
// ==/UserScript==

(function () {
    'use strict';

    setTimeout(
        () => {
            let parent = $(".PageToolbarSection-left-E_sPJ");
            // parent.append("<div class=\"PageToolbarSeparator-separator-N1WHF\"></div>")
            createBtn("MD url", copyMdUrl);
        }, 5000
    )

    function copyMdUrl() {
        let title = $(".SeriesDetails-title-pJv1g")[0].innerText;
        console.log(title)
        console.log(document.querySelector(".SeriesDetails-title-pJv1g"));
        let content = `[${title}](${window.location.href})`;
        unsecuredCopyToClipboard(content)
    }

    function unsecuredCopyToClipboard(text) {
        const textArea = document.createElement("textarea");
        textArea.value = text;
        document.body.appendChild(textArea);
        textArea.focus();
        textArea.select();
        try {
            document.execCommand('copy');
        } catch (err) {
            console.error('Unable to copy to clipboard', err);
        }
        document.body.removeChild(textArea);
    }


    function createBtn(name, onclick) {
        try {
            let parent = $(".PageHeader-right-e8LU4");
            let outer = document.createElement('button');
            outer.className = "PageToolbarButton-toolbarButton-j8a_b Link-link-RInnp Link-link-RInnp";
            outer.onclick = onclick;
            outer.innerHTML = `<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="clipboard" class="svg-inline--fa fa-clipboard Icon-default-ZpHc_" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512" style="font-size: 21px;">
  <path fill="currentColor" d="M336 64h-80.8C252.4 25.8 221.6 0 184 0s-68.4 25.8-71.2 64H32C14.3 64 0 78.3 0 96v368c0 17.7 14.3 32 32 32h320c17.7 0 32-14.3 32-32V96c0-17.7-14.3-32-32-32zM184 32c15.2 0 28.1 9.1 33.9 22.1C214.6 54.1 199.8 56 184 56s-30.6-1.9-33.9-1.9C155.9 41.1 168.8 32 184 32zM336 464H48V96h72.8C131.6 70.1 156.3 56 184 56s52.4 14.1 63.2 40H336v368zm-88-48H136c-13.3 0-24-10.7-24-24v-24c0-13.3 10.7-24 24-24h112c13.3 0 24 10.7 24 24v24c0 13.3-10.7 24-24 24zm0-96H136c-13.3 0-24-10.7-24-24v-24c0-13.3 10.7-24 24-24h112c13.3 0 24 10.7 24 24v24c0 13.3-10.7 24-24 24zm0-96H136c-13.3 0-24-10.7-24-24v-24c0-13.3 10.7-24 24-24h112c13.3 0 24 10.7 24 24v24c0 13.3-10.7 24-24 24z"/>
</svg>
                                <div class="PageToolbarButton-labelContainer-QhTz_">
                                    <div class="PageToolbarButton-label-QIVQh">${name}</div></div>`
            // let inner = document.createElement('div');
            // inner.className = "relative text-[16px] leading-[normal] before:block before:h-4 before:w-4";
            // inner.innerText = name;
            // outer.appendChild(inner);
            parent.append(outer);
            console.log(outer);
            console.log(`added button ${name}`);
        } catch (error) {
            console.log(`Couldn't add button ${name}, because ${error}`);
        }
    }

    function createBtnOld(name, onclick) {
        try {
            let parent = $(".PageToolbarSection-left-E_sPJ");
            let outer = document.createElement('button');
            outer.className = "PageToolbarButton-toolbarButton-j8a_b Link-link-RInnp Link-link-RInnp";
            outer.onclick = onclick;
            outer.innerHTML = `<svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="clipboard" class="svg-inline--fa fa-clipboard Icon-default-ZpHc_" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512" style="font-size: 21px;">
  <path fill="currentColor" d="M336 64h-80.8C252.4 25.8 221.6 0 184 0s-68.4 25.8-71.2 64H32C14.3 64 0 78.3 0 96v368c0 17.7 14.3 32 32 32h320c17.7 0 32-14.3 32-32V96c0-17.7-14.3-32-32-32zM184 32c15.2 0 28.1 9.1 33.9 22.1C214.6 54.1 199.8 56 184 56s-30.6-1.9-33.9-1.9C155.9 41.1 168.8 32 184 32zM336 464H48V96h72.8C131.6 70.1 156.3 56 184 56s52.4 14.1 63.2 40H336v368zm-88-48H136c-13.3 0-24-10.7-24-24v-24c0-13.3 10.7-24 24-24h112c13.3 0 24 10.7 24 24v24c0 13.3-10.7 24-24 24zm0-96H136c-13.3 0-24-10.7-24-24v-24c0-13.3 10.7-24 24-24h112c13.3 0 24 10.7 24 24v24c0 13.3-10.7 24-24 24zm0-96H136c-13.3 0-24-10.7-24-24v-24c0-13.3 10.7-24 24-24h112c13.3 0 24 10.7 24 24v24c0 13.3-10.7 24-24 24z"/>
</svg>
                                <div class="PageToolbarButton-labelContainer-QhTz_">
                                    <div class="PageToolbarButton-label-QIVQh">${name}</div></div>`
            // let inner = document.createElement('div');
            // inner.className = "relative text-[16px] leading-[normal] before:block before:h-4 before:w-4";
            // inner.innerText = name;
            // outer.appendChild(inner);
            parent.append(outer);
            console.log(outer);
            console.log(`added button ${name}`);
        } catch (error) {
            console.log(`Couldn't add button ${name}, because ${error}`);
        }
    }

    // Your code here...
})();
