// var els = $$("button.chakra-button.css-1gd0xxf");
var els = $$("div.cursor-pointer[type=button]")

for (let i = 0; i < els.length; i++) {
    setTimeout(() => { els[i].click() }, i * 250);
}
