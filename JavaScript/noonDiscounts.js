// ==UserScript==
// @name         Noon Discounts
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        https://www.noon.com/*
// @icon         https://www.google.com/s2/favicons?domain=noon.com
// @grant        none
// @require http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js
// ==/UserScript==

(function () {
  'use strict';

  let $ = window.jQuery;

  // save(renderPage(discounts))
  //setTimeout(()=>{
  createBtn("Discounts Sort", () => save(renderPage(getDiscounts())));
  //},5000);

  function getDiscounts() {
    let discounts = [];
    $(".discount").each((i, e) => discounts.push(e));
    discounts.sort((a, b) => discountToInt(b) - discountToInt(a));
    return discounts;
  }
  function save(htmlContent) {
    let bl = new Blob([htmlContent], { type: "text/html" });
    let a = document.createElement("a");
    a.href = URL.createObjectURL(bl);
    a.download = "discounts.html";
    a.hidden = true;
    document.body.appendChild(a);
    a.innerHTML = "something random - nobody will see this, it doesn't matter what you put here";
    a.click();
    URL.revokeObjectURL(bl);
    a.remove();
    document.body.removeChild(a);
  }
  function discountToInt(a) {
    return a.innerText.split('%')[0];
  }

  function renderTable(elements) {
    console.log(elements.map(renderRow))
    return `
  <table class="table table-striped table-dark table-hover">
  <thead>
    <tr>
      <th scope="col">Discount</th>
      <th scope="col">Item</th>
      <th scope="col">Price</th>
      <th scope="col">OldPrice</th>
      <th scope="col">Images</th>
    </tr>
  </thead>
  <tbody>
    ${elements.map(renderRow).join("")}
  </tbody>
</table>`
  }
  function renderRow(el) {
    console.log(el)
    let img = el.parentElement.parentElement.parentElement.parentElement.previousSibling.querySelector("img[alt]:not([alt='wishlist'])");
    if (img !== null)
      img = img.src;
    return `
  <tr>
      <th scope="row">${el.innerText}</th>
      <td> <a href="${el.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.href}">${el.parentElement.parentElement.parentElement.parentElement.firstChild.innerText}</a></td>
      <td>${el.parentElement.parentElement.firstChild.innerText}</td>
      <td>${el.previousSibling.innerText}</td>
      <td><img src=${img} width=100px></td>
  </tr>
  `
  }

  function renderPage(discounts) {
    return `
  <!doctype html>
<html lang="en">
<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

  <title>Discounts</title>
</head>
<body>
  ${renderTable(discounts)}

  <!-- Optional JavaScript -->
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
  `
  }



  function createBtn(name, onclick) {
    // let btn = `<button type="button" class="btn btn-success">${name} onclick=${onclick}</button>`;
    let btn = document.createElement("button");
    btn.innerText = name;
    btn.onclick = onclick;
    btn.className = "btn btn-success"
    btn.style.cssText = `color:#00f;font-size:20px;background-color:black;text-align: center;display: block;margin: 0px auto;`;
    let added = false;
    try {
      let body = $("header");
      body.append(btn);
      console.log(btn);
      added = true;
      console.log(`added button ${name}`);
    } catch (error) {
      console.log(`Couldn't add button ${name}, because ${error}`);

    }
    if (added) {
      return btn;
    } else {
      return null;
    }
  }

})();