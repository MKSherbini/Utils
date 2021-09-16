function getElementByXpath(path) {
    return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}

for (let i = 7; i <= 43; i += 2) {
    getElementByXpath(`/html/body/div[2]/div[${i}]/table/tbody/tr/td[3]/div/a[2]`).click();
}


// a better sol
$("img[src='./styles/tracker/imageset/magnet.png']").click()