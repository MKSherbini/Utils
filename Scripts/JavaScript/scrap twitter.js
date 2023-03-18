var following = new Set();
(function FkingScrapIT() {
    var timer = null;
    var cnt = 0;

    function FkingCountIT() {
        window.scrollBy(0, 1000);
        var got = document.querySelectorAll("div[data-testid='cellInnerDiv']");
        for (let i = 0; i < got.length; i++) {
            var tag = got[i].querySelector("a").href;
            following.add(tag);
        }

        var body = document.body,
            html = document.documentElement;
        var height = Math.max(body.scrollHeight, body.offsetHeight,
            html.clientHeight, html.scrollHeight, html.offsetHeight);
        if (window.innerHeight + window.scrollY === height) {
            cnt++;
            if (cnt == 2) {
                console.log(following);
                console.log(Array.from(following).join('\n'));
                navigator.clipboard.writeText(Array.from(following).join('\n'));
                clearInterval(timer);
            }
        } else { cnt = 0; }
    };
    timer = setInterval(FkingCountIT, 2000);
}());

setTimeout(()=>{
    navigator.clipboard.writeText(Array.from(following).join('\n'))
},1000);