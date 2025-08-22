package app.enums;

import lombok.Getter;

@Getter
public enum Page {
    MAIN("https://jes.iti.gov.eg"),
    LOGIN("https://jes.iti.gov.eg/login"),
    ADMISSION_VIEW_EXAM_SESSIONS_MAIN("https://jes.iti.gov.eg/admission/getExamSessionList.htm"),
    ADMISSION_VIEW_EXAM_SESSIONS("https://jes.iti.gov.eg/admission/getExamSessionList.htm?d-7484297-p=%s");

    private final String url;

    Page(String url) {
        this.url = url;
    }

    public String formatBy(String... args) {
        return url.formatted((Object[]) args);
    }

    public String formatBy(Integer... args) {
        return url.formatted((Object[]) args);
    }
}
