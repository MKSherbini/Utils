function autoRunCheater() {
    var sheet = SpreadsheetApp.getActiveSpreadsheet().getActiveSheet();
    var dataRange = sheet.getDataRange();
    var values = dataRange.getValues();
    var lastCol = -1;
    for (var col = 1; col <= values[2].length; col++) {
        var cellValue = values[2][col];
        if (cellValue === "Solved") {
            lastCol = col - 1;
            break;
        }
    }
    // Logger.log(lastCol);

    cheater(3, 30, 1, lastCol);
}
function cheater(row_st, row_end, col_st, col_end) {
    // Logger.log("%s %s %s %s", row_st, row_end, col_st, col_end);
    const regex = /.*submissions\/(?:detail\/)?([^\/a-z]+).*/m;
    var sheet = SpreadsheetApp.getActiveSpreadsheet().getActiveSheet();
    var dataRange = sheet.getDataRange();
    var values = dataRange.getValues();
    var cheaterCol = col_end + 3;
    var cheaters = new Map();
    var solutions = new Map();
    for (var row = row_st; row <= row_end; row++) {
        for (var col = col_st; col <= col_end; col++) {
            var cellValue = values[row][col];
            var cell = sheet.getRange(row + 1, col + 1);
            // Logger.log("%s %s", row + 1, col + 1);
            if (cellValue !== '' && cellValue !== null && cell.getRichTextValue() !== null) {
                Logger.log("%s %s %s %s", row + 1, col + 1, cell.getRichTextValue().getLinkUrl(), regex.exec(cell.getRichTextValue().getLinkUrl()));
                if (regex.exec(cell.getRichTextValue().getLinkUrl()) === null) {
                    addReview(cheaters, row + 1, cell.getRichTextValue().getLinkUrl())
                    continue;
                }
                var submissionId = regex.exec(cell.getRichTextValue().getLinkUrl())[1];
                if (solutions.has(submissionId)) {
                    addCheating(cheaters, row + 1, cell.getRichTextValue().getLinkUrl())
                    addCheating(cheaters, solutions.get(submissionId)[0], solutions.get(submissionId)[1])
                } else {
                    solutions.set(submissionId, [row + 1, cell.getRichTextValue().getLinkUrl()]);
                }
            }
        }
    }
    for (var row = row_st + 1; row <= row_end + 1; row++) {
        var cheaterCell = sheet.getRange(row, cheaterCol);
        if (cheaters.has(row))
            cheaterCell.setValue("Review " + [...cheaters.get(row)].join(", "));
        else
            cheaterCell.setValue("")
    }
    cheaters.forEach(function (sett, key) {
        Logger.log('Key: ' + key);
        sett.forEach(function (value) {
            Logger.log('Value: ' + value);
        });
    });
    return "";
};

function addCheating(cheaters, cheater, url) {
    // Logger.log("%s %s", cheater, url);
    if (!cheaters.has(cheater)) cheaters.set(cheater, new Set());
    cheaters.get(cheater).add(getClassName(url));
}
function addReview(cheaters, cheater, url) {
    // Logger.log("%s %s", cheater, url);
    if (!cheaters.has(cheater)) cheaters.set(cheater, new Set());
    cheaters.get(cheater).add(url);
}
function getClassName(input) {
    let splits = /problems\/(.*)\/submissions.*/.exec(input)[1].split('-');
    if (splits[splits.length - 1].replace(/[iv]+/, "") === "")
        splits[splits.length - 1] = splits[splits.length - 1].toUpperCase();
    return splits.map(s => s[0].toUpperCase() + s.substr(1))
        .join("");
}

