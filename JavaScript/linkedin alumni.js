var ppl = document.querySelectorAll(".artdeco-entity-lockup.artdeco-entity-lockup--stacked-center.artdeco-entity-lockup--size-7.ember-view");
// var localStorageSet = JSON.parse(localStorage.getItem('set'));
// var st = localStorageSet != null ? new Set(...localStorageSet) : new Set();
var res = "";
for (let i = 0; i < ppl.length; i++) {
    var url = ppl[i].querySelector("a");
    if (url == null) continue;
    url = url.href.split("?")[0]
    var img = ppl[i].querySelector("img").outerHTML;
    var name = ppl[i].querySelector(".org-people-profile-card__profile-title").innerText;
    name = name.replaceAll("|", "\\|");
    var title = ppl[i].querySelector(".ember-view.lt-line-clamp.lt-line-clamp--multi-line").innerText;
    title = title.replaceAll("|", "\\|");

    res += `| ${name} | ${title} | ${url} | ${img} |\n`;
    // st.add(`| ${name} | ${title} | ${url} | ${img} |\n`)
}
// console.log(st.size)
// localStorage.setItem('set', JSON.stringify(Array.from(st)));
console.log(res)
