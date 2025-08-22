var btns = document.querySelectorAll('button[aria-live="assertive"]');
for (let i = 0; i < btns.length; i++) {
    const element = btns[i];
    element.click();
}