(function submit() {
    if (submit.idx == void 0)
        submit.idx = 1;
    else
        submit.idx++;

    $("button[data-cy='submit-code-btn']").click();

    var refreshIntervalId = setInterval(
        () => {
            var values = $$(".data__HC-i").map(x => x.innerText);
            if (values.length < 4) return;
            console.log("submition: " + submit.idx);

            values[0] = values[0].substr(0, values[0].length - 3);
            values[1] = values[1].substr(0, values[1].length - 1);
            values[2] = values[2].substr(0, values[2].length - 3);
            values[3] = values[3].substr(0, values[3].length - 1);
            submit.runtime = Math.min(submit.runtime || 99999999, values[0]);
            submit.runtimePercent = Math.max(submit.runtimePercent || 0, values[1]);
            submit.memory = Math.min(submit.memory || 99999999, values[2]);
            submit.memoryPercent = Math.max(submit.memoryPercent || 0, values[3]);

            clearInterval(refreshIntervalId);

            if (submit.idx < 10)
                setTimeout(submit, 6000);
            else
                console.log(`${submit.runtime} ms, faster than ${submit.runtimePercent}% : ${submit.memory} MB, less than ${submit.memoryPercent}%`);
        }, 1000);
})();