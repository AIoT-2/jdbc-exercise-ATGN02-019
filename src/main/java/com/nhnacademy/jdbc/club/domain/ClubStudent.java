package com.nhnacademy.jdbc.club.domain;

import java.util.Objects;

public class ClubStudent {

    private final String studentId;

    private final String studentName;

    private final String clubId;

    private final String clubName;

    public ClubStudent(String studentId, String studentName, String clubId, String clubName) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.clubId = clubId;
        this.clubName = clubName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getClubId() {
        return clubId;
    }

    public String getClubName() {
        return clubName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClubStudent that = (ClubStudent) o;
        return Objects.equals(studentId, that.studentId)
                && Objects.equals(studentName, that.studentName)
                && Objects.equals(clubId, that.clubId)
                && Objects.equals(clubName, that.clubName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName, clubId, clubName);
    }

    @Override
    public String toString() {
        return "ClubStudent{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", clubId='" + clubId + '\'' +
                ", clubName='" + clubName + '\'' +
                '}';
    }
}
