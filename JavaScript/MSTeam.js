// var output = [];
var output = new Set();
var regex = RegExp('join-btn*');
for (let i of document.querySelectorAll("button[data-tid]")) {
    if (regex.test(i.getAttribute('data-tid'))) {
        output.add({
            'element': i,
            'attribute name': i.getAttribute('data-tid').name,
            'attribute value': i.getAttribute('data-tid').value
        });
    }
}


// var output = new Set();
// var regex = RegExp('join-btn*');
// for (let i of document.querySelectorAll("button[data-tid]")) {
//     if (regex.test(i.getAttribute('data-tid'))) {
//         output.add(i.getAttribute('data-tid'));
//     }
// }

prejoin-join-button
var output = [];
var regex = RegExp('join-btn-.+');
for (let i of document.querySelectorAll("button[data-tid]")) {
    if (regex.test(i.getAttribute('data-tid'))) {
        output.push(i);
        i.click();
    }
}

