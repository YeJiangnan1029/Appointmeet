package zju.apd.yjn.appointmeet.mapper;

import zju.apd.yjn.appointmeet.entity.Meeting;
import zju.apd.yjn.appointmeet.entity.User;

import java.util.Date;
import java.util.List;

public interface ParticipationMapper {
    Integer addParticipationRecord(Integer userId, Integer meetingId, Date joinTime);
    Integer deleteParticipationRecordByMeetingId(Integer meetingId);
    Integer getNumOfParticipationByUserIdAndMeetingId(Integer userId, Integer meetingId);
    Integer deleteParticipationRecord(Integer userId, Integer meetingId);
    List<Meeting> findParticipatedMeetingsByUserId(Integer userId);
    List<User> findParticipatorsByMeetingId(Integer meetingId);

}
