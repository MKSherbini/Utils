// ==UserScript==
// @name         LeetCode util
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       MKSherbini
// @match        https://leetcode.com/problems/*
// @icon         https://www.google.com/s2/favicons?domain=leetcode.com
// @grant        none
// @require http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js
// ==/UserScript==


(function () {
    'use strict';
    let $ = window.jQuery;

    setTimeout(
        () => {
            createBtn("Generate CPP Class", CreateTemplate);
            createBtn("Auto Resubmit", runAutoResubmit);
        }, 2000
    )

    function runAutoResubmit() {
        AutoResubmit.idx = void 0;
        AutoResubmit.runtime = 99999999;
        AutoResubmit.runtimePercent = 0;
        AutoResubmit.memory = 99999999;
        AutoResubmit.memoryPercent = 0;
        AutoResubmit();
    }

    function AutoResubmit() {
        if (AutoResubmit.idx === void 0)
            AutoResubmit.idx = 1;
        else
            AutoResubmit.idx++;

        $("button[data-cy='submit-code-btn']").click();

        let refreshIntervalId = setInterval(
            () => {
                let values = $(".data__HC-i").map((i, x) => x.innerText);
                if (values.length < 4) return;
                console.log("submition: " + AutoResubmit.idx);

                values[0] = values[0].substr(0, values[0].length - 3);
                values[1] = values[1].substr(0, values[1].length - 1);
                values[2] = values[2].substr(0, values[2].length - 3);
                values[3] = values[3].substr(0, values[3].length - 1);
                AutoResubmit.runtime = Math.min(AutoResubmit.runtime === void 0 ? 99999999 : AutoResubmit.runtime, values[0]);
                AutoResubmit.runtimePercent = Math.max(AutoResubmit.runtimePercent === void 0 ? 0 : AutoResubmit.runtimePercent, values[1]);
                AutoResubmit.memory = Math.min(AutoResubmit.memory === void 0 ? 99999999 : AutoResubmit.memory, values[2]);
                AutoResubmit.memoryPercent = Math.max(AutoResubmit.memoryPercent === void 0 ? 0 : AutoResubmit.memoryPercent, values[3]);

                clearInterval(refreshIntervalId);

                let content = `${AutoResubmit.runtime} ms, faster than ${AutoResubmit.runtimePercent}% : ${AutoResubmit.memory} MB, less than ${AutoResubmit.memoryPercent}%`;
                console.log(content);
                if (AutoResubmit.idx < 12) {
                    setTimeout(AutoResubmit, 6000);
                } else {
                    navigator.clipboard.writeText(content);
                }
            }, 1000);
    }

    function targetCaseFromFilter(filter) {
        return filter.nextSibling.textContent.replaceAll(/\n */g, '').trim();
    }

    function getCases() {
        let filter = $("pre strong").filter((i, e) => e.innerText.indexOf("Input") !== -1 || e.innerText.indexOf("Output") !== -1);
        let cases = Array(filter.length / 2);
        for (let i = 0; i < filter.length; i += 2) {
            cases[i / 2] = new TestCase(targetCaseFromFilter(filter[i]), targetCaseFromFilter(filter[i + 1]));
            console.log(`case[${i / 2}]= ${JSON.stringify(cases[i / 2])}`);
        }
        return cases;
    }

    function getClassName() {
        return /problems\/(.*)\//.exec(window.location.href)[1].split('-')
            .map(s => s[0].toUpperCase() + s.substr(1))
            .join("");
    }

    function CreateTemplate() {
        console.log('Creating template...');
        let cases = getCases();
        let name = getClassName();

        let content = fillTemplate(name, cases);
        console.log(content);
        navigator.clipboard.writeText(content).then(() =>
            setTimeout(() =>
                navigator.clipboard.writeText(name), 500)
        )
    }

    function createCPPCase(cases, idx) {
        return `vector<${cases[0].inputs[idx].type}> input${idx + 1} = {
${cases.map(c => "\t" + c.inputs[idx].value).join(",\n")}
};`;
    }

    function fillTemplate(className, cases) {
        return `#pragma once
#include "stdc++.h"
using namespace std;

//${window.location.href}

class ${className}
{
public:
//
${cases[0].output.type} solution(${cases[0].inputs.map(param => param.type + " " + param.name).join(", ")}) {

}
vector<${cases[0].output.type}> output = {
${cases.map(c => "\t" + c.output.value).join(",\n")}
};
static constexpr int const& inputs = ${cases[0].inputs.length};
${cases[0].inputs.map((input, idx) => createCPPCase(cases, idx)).join("\n")}
};`;
    }

    function TestCase(input, output) {
        this.output = new OutputParam(output);
        console.log(`TestCase.output: ${output}`)
        console.log(`TestCase.outputParam: ${JSON.stringify(this.output)}`)

        let splits = input.split(", ");
        this.inputs = Array(splits.length);
        for (let i = 0; i < splits.length; i++) {
            console.log(`TestCase.input: ${splits[i]}`)
            this.inputs[i] = new InputParam(splits[i]);
            console.log(`TestCase.inputParam: ${JSON.stringify(this.inputs[i])}`)
        }
    }

    function guessLevelType(sample) {
        let type = "char";
        if (/^-?\d+/.test(sample))
            type = "int";
        else if (/^(true|false)/.test(sample))
            type = "bool";
        else if (/^"[^"]{2,}"?$/.test(sample))
            type = "string";
        console.log(`guessLevelType.sample: ${sample} : ${type}`)
        return type;
    }

    function guessType(value) {
        let levelTypeRegexRes = /^({*)([^,}]{1,5})/.exec(value);
        console.log(`guessType.levelTypeRegexRes: ${levelTypeRegexRes}`)

        let level = levelTypeRegexRes[1].length;
        let type = guessLevelType(levelTypeRegexRes[2]);

        return getTypeMultiLevel(level, type);
    }

    function InputParam(value) {
        let splits = /^([\w]+)(?: = )?(.*)/.exec(value);
        console.log(`InputParam.splits: ${splits}`)

        this.name = splits[1]
        this.value = splits[2]
            .replaceAll("[", "{")
            .replaceAll("]", "}");
        this.type = guessType(this.value);

        if (this.type.includes("char"))
            this.value = this.value.replaceAll('"', "'");
    }

    function OutputParam(value) {
        this.value = value
            .replaceAll("[", "{")
            .replaceAll("]", "}");
        this.type = guessType(this.value);

        if (this.type.includes("char"))
            this.value = this.value.replaceAll('"', "'");
    }

    function getTypeMultiLevel(level, type) {
        if (level === 0) return type;
        return "vector<" + getTypeMultiLevel(level - 1, type) + ">";
    }

    function createBtn(name, onclick) {
        try {
            let parent = $(".navbar-left-container__3-qz");
            let div = document.createElement('div');
            div.className = "nav-item-container__16kF";
            div.onclick = onclick;
            let a = document.createElement('a');
            a.className = "nav-item__5BvG";
            a.innerText = name;
            div.appendChild(a);
            parent.append(div);
            console.log(div);
            console.log(`added button ${name}`);
        } catch (error) {
            console.log(`Couldn't add button ${name}, because ${error}`);
        }
    }
})();
