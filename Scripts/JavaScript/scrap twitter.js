var following = new Set();
(function FkingScrapIT() {
    var timer = null;
    var cnt = 0;

    function FkingCountIT() {
        window.scrollBy(0, 2000);
        var got = document.getElementsByClassName("css-901oao css-bfa6kz r-111h2gw r-18u37iz r-1qd0xha r-a023e6 r-16dba41 r-ad9z0x r-bcqeeo r-qvutc0");
        for (let i = 0; i < got.length; i++) {
            const tag = got[i].firstChild.innerHTML;
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
                clearInterval(timer);
            }
        } else { cnt = 0; }
    };
    timer = setInterval(FkingCountIT, 2000);
}());
