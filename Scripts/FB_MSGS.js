// msgs = document.getElementsByClassName("_aok _7i2m")
msgs = document.getElementsByTagName("iframe")[0].contentWindow.document.getElementsByClassName("_aok _7i2m")

var myArray = [];
var myArray1 = [];
var myArray2 = [];

for (let index = 0; index < msgs.length; index++) {
    const rect = msgs[index].getBoundingClientRect();
    if (rect.right >= 1023 - 50) {
        myArray.push(msgs[index]);
    }
    else if (rect.left <= 415 + 50) {
        myArray1.push(msgs[index]);
    } else {
        console.log(rect.right);
        console.log(rect.left);
        myArray2.push(msgs[index]);
    }
}