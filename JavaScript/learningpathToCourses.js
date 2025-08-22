// must focus page in the 2sec period to get data in clipboard
setTimeout(() => {
    var items = $$(".ember-view.entity-link");
    var links = "";
    for (let i = 0; i < items.length; i += 2) {
        links += items[i].href + "\n";
    }
    async function writeToClipboard(text) { try { await navigator.clipboard.writeText(text); } catch (error) { console.error(error); } }
    console.log(links);
    writeToClipboard(links);
}, 2000);

