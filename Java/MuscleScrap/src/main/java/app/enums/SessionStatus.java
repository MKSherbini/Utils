package app.enums;

import lombok.Getter;

public enum SessionStatus {
    NEW_SESSION("New Session"),
    EXAM_GENERATED("Exam Generated"),
    EXAM_COMPLETED("Exam Completed"),
    ADMISSION_SUBMITTED("Admission Submitted");

    @Getter
    private final String value;


    SessionStatus(String value) {
        this.value = value;
    }
}
