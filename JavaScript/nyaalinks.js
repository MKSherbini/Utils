var links = $("td.text-center a:nth-child(2)");
var str = "";
for (let index = 0; index < links.length; index++) {
    const element = links[index];
    // console.log(element.parentElement.parentElement.querySelector("td a:nth-child(2)").title);
    str += element.href + "\n";
}
console.log(str);