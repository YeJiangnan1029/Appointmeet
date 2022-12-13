package zju.apd.yjn.appointmeet.mapper;

import zju.apd.yjn.appointmeet.entity.Meeting;
import java.util.List;

public interface MeetingMapper {

    Integer addMeeting(Meeting meeting);
    Integer deleteMeeting(Integer meetingId);
    Meeting findMeetingByMeetingId(Integer id);
    List<Meeting> findMeetingsByOrganizerId(Integer organizerId);

}
