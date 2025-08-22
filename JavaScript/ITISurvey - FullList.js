(function FITISurveyIt() {
  if (FITISurveyIt.idx === void 0) {
      FITISurveyIt.idx = 0;
  }
  $("#ContentPlaceHolder1_UcCourseEval1_ddlCourseName_chzn").mousedown()
  var arr = $("#ContentPlaceHolder1_UcCourseEval1_ddlCourseName_chzn li");
  if (FITISurveyIt.idx >= arr.length) return;
  $(arr[0]).trigger("mouseup");
  setTimeout(function () {
      var star4 = document.querySelectorAll('[id*=Star_4]');
      for (var j = star4.length - 1; j >= 0; j--) {
          star4[j].dispatchEvent(new Event('mouseover'));
          star4[j].dispatchEvent(new Event('click'));
      }
      $("html, body").animate({scrollTop: $(document).height() - $(window).height()});
      setTimeout(function () {
          $("html, body").animate({scrollTop: $(document).height() - $(window).height()});
          $("#ContentPlaceHolder1_UcCourseEval1_btnSave").click();
      }, 15000);
  }, 6000);
  FITISurveyIt.idx++;
  console.log(FITISurveyIt.idx);
  setTimeout(FITISurveyIt, 40000);
}());

