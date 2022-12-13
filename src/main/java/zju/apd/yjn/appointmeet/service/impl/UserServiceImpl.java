package zju.apd.yjn.appointmeet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import zju.apd.yjn.appointmeet.entity.Meeting;
import zju.apd.yjn.appointmeet.entity.User;
import zju.apd.yjn.appointmeet.mapper.MeetingMapper;
import zju.apd.yjn.appointmeet.mapper.ParticipationMapper;
import zju.apd.yjn.appointmeet.mapper.UserMapper;
import zju.apd.yjn.appointmeet.service.IUserService;
import zju.apd.yjn.appointmeet.service.ex.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired(required=false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private ParticipationMapper participationMapper;

    @Autowired(required = false)
    private MeetingMapper meetingMapper;

    private String md5Encryption3Times(String password){
        for (int i=0; i<3; i++){
            password = DigestUtils.md5DigestAsHex(password.getBytes());
        }
        return password;
    }

    private User ensureUserExistAndGet(Integer userId){
        // 根据学号查询是否存在 或者存在但是已注销
        User result = userMapper.findUserById(userId);
        if (result == null){
            throw new UserIdNotFoundException("用户id不存在");
        }
        return result;
    }

    private User ensureUserExistAndGet(String email){
        // 根据学号查询是否存在 或者存在但是已注销
        User result = userMapper.findUserByEmail(email);
        if (result == null){
            throw new UserEmailNotFoundException("邮箱不存在");
        }
        return result;
    }

    private User loginFromQueryResultAndReturnUserBriefInfo(User result, String password){
        // 密码匹配
        String passwordEncrypted = md5Encryption3Times(password);
        if (!passwordEncrypted.equals(result.getPassword())){
            throw new PasswordNotMatchException("密码错误");
        }
        // 登录成功 更新登录信息
        Integer rows = userMapper.updateLastLoginDate(result.getId(), new Date());
        if (rows != 1){
            throw new UpdateLoginDateException("更新登录信息时错误");
        }

        // 只返回部分数据给前端
        User userBriefInfo = new User();
        userBriefInfo.setId(result.getId());
        userBriefInfo.setName(result.getName());
        userBriefInfo.setEmail(result.getEmail());
        userBriefInfo.setAvatar(result.getAvatar());

        return userBriefInfo;
    }

    @Override
    public Integer register(User user){
        String email = user.getEmail();
        User result = userMapper.findUserByEmail(email);
        if (result != null) {
            throw new UserEmailDuplicatedException("邮箱已存在");
        }

        // 补全注册所需信息
        user.setCreateDate(new Date());

        // 对密码进行md5加密
        String oldPassword = user.getPassword();
        user.setPassword(md5Encryption3Times(oldPassword));

        Integer rowChangedNum = userMapper.addUser(user);
        if (rowChangedNum != 1){
            throw new InsertException("在用户注册过程中出现未知错误");
        }

        return user.getId();
    }

    @Override
    public User loginById(Integer id, String password){
        // 根据学号查询是否存在 或者存在但是已注销
        System.out.println("loginById");
        User result = ensureUserExistAndGet(id);
        return loginFromQueryResultAndReturnUserBriefInfo(result, password);
    }

    @Override
    public User loginByEmail(String email, String password){
        // 根据学号查询是否存在 或者存在但是已注销
        System.out.println("loginByEmail");
        User result = ensureUserExistAndGet(email);
        return loginFromQueryResultAndReturnUserBriefInfo(result, password);
    }

    @Override
    public User getUserInfoById(Integer id, Boolean isDetailed){
        User result = ensureUserExistAndGet(id);

        // 只返回部分数据给前端
        User userBriefInfo = new User();
        userBriefInfo.setId(result.getId());
        userBriefInfo.setName(result.getName());
        userBriefInfo.setEmail(result.getEmail());
        userBriefInfo.setAvatar(result.getAvatar());

        if (isDetailed){
            userBriefInfo.setCreateDate(result.getCreateDate());
            userBriefInfo.setLastLoginDate(result.getLastLoginDate());
        }

        return userBriefInfo;
    }

    @Override
    public void modifyPassword(Integer id, String oldPassword, String newPassword){
        // 根据学号查询是否存在
        User result = ensureUserExistAndGet(id);

        // 密码匹配
        String oldPasswordEncrypted = md5Encryption3Times(oldPassword);
        if (!oldPasswordEncrypted.equals(result.getPassword())){
            throw new PasswordNotMatchException("密码错误");
        }

        // 原始密码正确 更新密码
        String newPasswordEncrypted = md5Encryption3Times(newPassword);
        Integer rows = userMapper.updatePasswordById(id, newPasswordEncrypted);
        if (rows != 1){
            throw new UpdateException("更新密码时错误");
        }
    }

    @Override
    public void modifyName(Integer id, String newName){
        // 根据学号查询是否存在
        ensureUserExistAndGet(id);
        Integer rows = userMapper.updateNameById(id, newName);
        if (rows != 1){
            throw new UpdateException("更新用户名时错误");
        }
    }

    @Override
    public void modifyEmail(Integer id, String newEmail){
        // 根据学号查询是否存在
        ensureUserExistAndGet(id);
        // 邮箱不能重复
        User result = userMapper.findUserByEmail(newEmail);
        if (result != null) {
            throw new UserEmailDuplicatedException("邮箱已存在");
        }

        Integer rows = userMapper.updateEmailById(id, newEmail);
        if (rows != 1){
            throw new UpdateException("更新邮箱时错误");
        }

    }

    @Override
    public void modifyAvatar(Integer id, String newAvatar){
        // 根据学号查询是否存在
        ensureUserExistAndGet(id);
        Integer rows = userMapper.updateAvatarById(id, newAvatar);
        if (rows != 1){
            throw new UpdateException("更新用户头像时错误");
        }
    }

    @Override
    public void createMeeting(Meeting meeting){
        // 先看组织者id是否存在
        if (meeting.getOrganizerId() == null){
            throw new UserIdNotFoundException("会议组织者id不能为空");
        }
        ensureUserExistAndGet(meeting.getOrganizerId());
        // id自动分配 不能设置
        if (meeting.getId() != null) meeting.setId(null);
        // isPrivate字段不能为空
        if (meeting.getPrivate() == null) meeting.setPrivate(false);
        // 非私密的会议不能有密码
        if (!meeting.getPrivate() && meeting.getJoinPassword()!=null) meeting.setJoinPassword(null);
        // 如果开始时间未指定 默认当前时间
        if (meeting.getStartTime()==null) meeting.setStartTime(new Date());
        // 开会的时间不能在过去
        else {
            Date startTime = meeting.getStartTime();
            if (startTime.before(new Date()) ){
                throw new DateTimeIllegalException("不能预定过去开始的会议");
            }
        }

        // 如果持续时间未指定 默认30分钟
        if (meeting.getDuration()==null) meeting.setDuration(1800);
        Integer rows = meetingMapper.addMeeting(meeting);
        if (rows != 1){
            throw new InsertException("创建会议时错误");
        }

        // 创建会议后，组织者自动进入会议
        participationMapper.addParticipationRecord(meeting.getOrganizerId(), meeting.getId(), new Date());

    }

    @Override
    public void dismissMeeting(Integer meetingId){
        Meeting result = meetingMapper.findMeetingByMeetingId(meetingId);
        if (result == null){
            throw new MeetingIdNotFoundException("会议不存在");
        }

        Integer rows = meetingMapper.deleteMeeting(meetingId);
        if (rows != 1){
            throw new DeleteException("删除会议时错误");
        }
        // 还要删除掉所有参会者参与记录
        participationMapper.deleteParticipationRecordByMeetingId(meetingId);

    }

    @Override
    public void joinMeeting(Integer userId, String password, Integer meetingId){
        // 根据学号查询是否存在
        ensureUserExistAndGet(userId);
        // 会议是否存在
        Meeting result = meetingMapper.findMeetingByMeetingId(meetingId);
        if (result == null){
            throw new MeetingIdNotFoundException("会议不存在");
        }
        // 是否已经加入该会议
        Integer num = participationMapper.getNumOfParticipationByUserIdAndMeetingId(userId, meetingId);
        if (num > 0){
            throw new ParticipationDuplicatedException("用户已经加入会议");
        }
        // 不能加入已经结束的会议
        Date now = new Date();
        Date endTime = new Date(result.getStartTime().getTime() + result.getDuration() * 1000);
        if (now.after(endTime)){
            throw new ExpiredOperationException("不能加入已结束的会议");
        }
        // 验证密码
        if ( result.getPrivate() && !password.equals(result.getJoinPassword())){
            throw new MeetingPasswordNotMatchException("会议口令错误");
        }

        // 加入会议
        Integer rows = participationMapper.addParticipationRecord(userId, meetingId, new Date());
        if (rows != 1) {
            throw new InsertException("加入会议时错误");
        }
    }

    @Override
    public void quitMeeting(Integer userId, Integer meetingId){
        // 根据学号查询是否存在
        ensureUserExistAndGet(userId);
        // 会议是否存在
        Meeting result = meetingMapper.findMeetingByMeetingId(meetingId);
        if (result == null){
            throw new MeetingIdNotFoundException("会议不存在");
        }
        // 是否已经加入该会议
        Integer num = participationMapper.getNumOfParticipationByUserIdAndMeetingId(userId, meetingId);
        if (num == 0){
            throw new ParticipationDuplicatedException("用户已经退出会议");
        }
        // 不能退出已经结束的会议
        Date now = new Date();
        Date endTime = new Date(result.getStartTime().getTime() + result.getDuration() * 1000);
        if (now.after(endTime)){
            throw new ExpiredOperationException("不能退出已结束的会议");
        }
        // 退出会议
        Integer rows = participationMapper.deleteParticipationRecord(userId, meetingId);
        if (rows == 0) {
            throw new InsertException("退出会议时错误");
        }
    }

    @Override
    public List<Meeting>[] findParticipatedMeetingsByUserId(Integer userId){
        // 根据学号查询是否存在
        ensureUserExistAndGet(userId);
        // 获取参与的会议信息列表
        List<Meeting> participatedMeetingList =
            participationMapper.findParticipatedMeetingsByUserId(userId);
        // 调整列表顺序，把已结束的会议放到另一个列表
        List<Meeting> expiredMeetingList = new ArrayList<>();
        List<Meeting>[] retMeetingLists = new List[2];
        retMeetingLists[0] = participatedMeetingList;
        retMeetingLists[1] = expiredMeetingList;
        for (Meeting meetingInfo: participatedMeetingList){
            Long startTimeMilliseconds = meetingInfo.getStartTime().getTime();
            Long durationMilliseconds = (long)meetingInfo.getDuration() * 1000;
            long endTimeMilliseconds = startTimeMilliseconds + durationMilliseconds;
            Date endTime = new Date(endTimeMilliseconds);
            if (endTime.before(new Date())){
                expiredMeetingList.add(meetingInfo);
            }
        }
        participatedMeetingList.removeAll(expiredMeetingList);
        return retMeetingLists;
    }

    @Override
    public Meeting getMeetingDetailedInfoByMeetingId(Integer meetingId){
        Meeting result = meetingMapper.findMeetingByMeetingId(meetingId);
        if (result == null){
            throw new MeetingIdNotFoundException("会议不存在");
        }
        return result;
    }

    @Override
    public List<User> getParticipatorsByMeetingId(Integer meetingId) {
        // 查询这个会议
        Meeting result = meetingMapper.findMeetingByMeetingId(meetingId);
        if (result == null){
            throw new MeetingIdNotFoundException("会议不存在");
        }
        // 查找参会者
        return participationMapper.findParticipatorsByMeetingId(meetingId);
    }


}
