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
        () => createBtn("Generate CPP Class", CreateTemplate), 2000
    )

    function getCases() {
        let filter = $("pre strong").filter((i, e) => e.innerText.indexOf("Input") !== -1 || e.innerText.indexOf("Output") !== -1);
        let cases = Array(filter.length / 2);
        for (let i = 0; i < filter.length; i += 2) {
            cases[i / 2] = new TestCase(filter[i].nextSibling.textContent.trim(), filter[i + 1].nextSibling.textContent.trim());
            console.log(cases[i / 2]);
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

        console.log(fillTemplate(name, cases));
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
        this.output = new Parameter(output);

        let splits = input.split(", ");
        this.inputs = Array(splits.length);
        for (let i = 0; i < splits.length; i++) {
            this.inputs[i] = new Parameter(splits[i]);
        }
    }

    function Parameter(value) {
        let splits = /^([a-zA-Z]*)(?: = )?(.*)/.exec(value);
        this.name = splits[1]
        this.value = splits[2]
            .replace("[", "{")
            .replace("]", "}");

        let regexRes = /^({*)(.{1,2})/.exec(this.value);
        let level = regexRes[1].length;
        let sample = regexRes[2];
        let type = "char";
        if (/^-?\d+/.test(sample))
            type = "int";
        else if (/^(true|false)/.test(sample))
            type = "bool";
        else if (/^([a-zA-Z]{2,}),*/.test(sample))
            type = "string";

        this.type = getTypeMultiLevel(level, type);
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
