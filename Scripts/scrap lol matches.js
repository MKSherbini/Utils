
(function FkingScrapIT() {
    window.scrollBy(0, 10000);
    (function FkingCountIT() {
        var bar = 0;
        var heal = 0;

        [].forEach.call(document.getElementsByClassName("spell-icon binding"), function countSums(el) {
            if (el.firstChild.attributes["data-rg-id"].nodeValue == "SummonerHeal") {
                heal++;
            }
            if (el.firstChild.attributes["data-rg-id"].nodeValue == "SummonerBarrier") {
                bar++;
            }
        });
        console.log(`barrier = ${bar}`);
        console.log(`heal = ${heal}`);
    }());
    setTimeout(FkingScrapIT, 2000);
}());

function FkingScrapIT() {
    window.scrollBy(0, 10000);
    (function FkingCountIT() {
        var bar = 0;
        var heal = 0;

        [].forEach.call(document.getElementsByClassName("spell-icon binding"), function countSums(el) {
            if (el.firstChild.attributes["data-rg-id"].nodeValue == "SummonerHeal") {
                heal++;
            }
            if (el.firstChild.attributes["data-rg-id"].nodeValue == "SummonerBarrier") {
                bar++;
            }
        });
        console.log(`barrier = ${bar}`);
        console.log(`heal = ${heal}`);
    }());
}
function FkingScrapIT() {
    window.scrollBy(0, 10000);
    (function FkingCountIT() {
        var sr = 0;
        var aram = 0;
        var tt = 0;

        [].forEach.call(document.getElementsByClassName("mode map-mode"), function countSums(el) {
            if (el.firstChild["textContent"] == "Summoner's Rift") {
                sr++;
            }
            if (el.firstChild["textContent"] == "Howling Abyss") {
                aram++;
            }
            if (el.firstChild["textContent"] == "Twisted Treeline") {
                tt++;
            }
        });
        console.log(`Summoner's Rift = ${sr}`);
        console.log(`Howling Abyss = ${aram}`);
        console.log(`Twisted Treeline = ${tt}`);
    }());
}
const FkingScrapITId = setInterval(() => {
    FkingScrapIT();
}, 2000);

// document.getElementsByClassName("spell-icon binding")[0].firstChild.attributes["data-rg-id"].nodeValue