var items = $$("#content #video-title");

var str="";

items.forEach(el => {
    str += el.innerText+"\n";
});

str