function setElementValById(id, val) {

    element = document.getElementById(id);

    const valueSetter = Object.getOwnPropertyDescriptor(element, 'value').set;
    const prototype = Object.getPrototypeOf(element);
    const prototypeValueSetter = Object.getOwnPropertyDescriptor(prototype, 'value').set;

    if (valueSetter && valueSetter !== prototypeValueSetter) {
        prototypeValueSetter.call(element, val);
    } else {
        valueSetter.call(element, val);
    }

    // var x = document.getElementById(id);
    // Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, "value").set.call(x, val);
    var ev2 = new Event('input', { bubbles: true });
    element.dispatchEvent(ev2);
}

// main course page
document.getElementById("finaid_button").click();
document.getElementById("financial_aid_modal_apply_button").click();

// setTimeout(function () {
    // alert("in");
    //form v1
    document.getElementById("info_checkbox").click();
    document.getElementById("completion_checkbox").click();

    x = document.getElementById("accept-terms-field")
    var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, "value").set;
    nativeInputValueSetter.call(x, 'I agree to the terms above');

    var ev2 = new Event('input', { bubbles: true });
    x.dispatchEvent(ev2);

    document.getElementById("continue_finaid_application_button").click();

    //form v2
    // document.getElementById("finaid-educationalBackground").selectedIndex = "1";
    // document.getElementById("finaid-employmentStatus").selectedIndex = "4";

    document.getElementById('finaid-educationalBackground').value = 'HIGH_SCHOOL'
    document.getElementById('finaid-employmentStatus').value = 'STUDENT'
    setElementValById("finaid-income", "0");
    setElementValById("finaid-amount-can-pay", "0");
    setElementValById("finaid-reason", "I'm a computer and systems engineering student, mansoura university, egypt . i need that financial aid because i can't afford paying for it at all as im still a student and can barely make ends meet , i don’t receive money for my part time job instead i receive lodging and such needs . i'm really happy that you can give people like me the opportunity to take these courses even tho we can't afford it and gave us a fair chance to compete with other people who can pay for it.I plan to complete all assignments on or before time as I have done in previous Courses. Also I intend to participate in Discussion Forums, which I have found to supplement my learning immensely in the other online courses I have taken on Coursera. I also plan to grade assignments which are to peer reviewed which I believe will an invaluable learning opportunity.");
    setElementValById("finaid-goal", "I'm a computer and systems engineering student, mansoura university, egypt . so i'm interested in this field and do my best to become good in it, but to better improve myself i need this course series really bad please accept my request lemme be able to get better in what i love in return if u need me when i get better i'll gladly help you.i'm really happy that you can give people like me the opportunity to take these courses even tho we can't afford it and gave us a fair chance to compete with other people who can pay for it.I plan to complete all assignments on or before time as I have done in previous Courses. Also I intend to participate in Discussion Forums, which I have found to supplement my learning immensely in the other online courses I have taken on Coursera. I also plan to grade assignments which are to peer reviewed which I believe will an invaluable learning opportunity.");
    document.getElementById("would-not-take-loan").click();
    setElementValById("finaid-loanReason", "I'm still a student and can barely make ends meet , i don’t receive money for my part time job instead i receive lodging and such needs .so paying for a course is something i wont be able to do anytime soon and when i get enough knowledge to get a good job, i'll make sure to pay.");
    document.getElementById("submit_application_button").click();

// }, 500)

