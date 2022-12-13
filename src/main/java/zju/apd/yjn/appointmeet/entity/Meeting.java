package zju.apd.yjn.appointmeet.entity;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Meeting {
    private Integer id;
    private String subject;
    private String detail;
    private String location;
    private Integer organizerId;
    private Date startTime;
    private Integer duration;
    private Boolean isPrivate;
    private String joinPassword;

    public Meeting(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Integer organizerId) {
        this.organizerId = organizerId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getJoinPassword() {
        return joinPassword;
    }

    public void setJoinPassword(String joinPassword) {
        this.joinPassword = joinPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(getId(), meeting.getId()) && Objects.equals(getSubject(), meeting.getSubject()) && Objects.equals(getDetail(), meeting.getDetail()) && Objects.equals(getLocation(), meeting.getLocation()) && Objects.equals(getOrganizerId(), meeting.getOrganizerId()) && Objects.equals(getStartTime(), meeting.getStartTime()) && Objects.equals(getDuration(), meeting.getDuration()) && Objects.equals(isPrivate, meeting.isPrivate) && Objects.equals(getJoinPassword(), meeting.getJoinPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubject(), getDetail(), getLocation(), getOrganizerId(), getStartTime(), getDuration(), isPrivate, getJoinPassword());
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", detail='" + detail + '\'' +
                ", location='" + location + '\'' +
                ", organizerId=" + organizerId +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", isPrivate=" + isPrivate +
                ", joinPassword='" + joinPassword + '\'' +
                '}';
    }
}
