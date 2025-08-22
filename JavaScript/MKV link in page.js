var arr = [], l = document.links;
for (var i = 0; i < l.length; i++) {
    if (l[i].href.search("mkv") != -1 && l[i].href.search("720") != -1)
        arr.push(l[i].href);
}
arr