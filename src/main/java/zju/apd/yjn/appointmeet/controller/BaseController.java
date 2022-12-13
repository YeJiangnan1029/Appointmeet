package zju.apd.yjn.appointmeet.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import zju.apd.yjn.appointmeet.controller.ex.*;
import zju.apd.yjn.appointmeet.service.ex.*;
import zju.apd.yjn.appointmeet.util.JsonResult;


public class BaseController {

    public static final int OK = 200;

    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> exceptionHandler(Exception e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UserIdDuplicatedException){
            result.setState(4000);
            result.setMessage("用户ID已被占用");
        } else if (e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时出现未知错误");
        } else if (e instanceof UserIdNotFoundException){
            result.setState(5001);
            result.setMessage("用户ID不存在");
        } else if (e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("用户名或密码错误");
        } else if (e instanceof UpdateLoginDateException){
            result.setState(5003);
            result.setMessage("更新登录信息时出现错误");
        } else if (e instanceof UpdateException){
            result.setState(5004);
            result.setMessage("更新信息时出现未知错误");
        } else if (e instanceof MeetingIdNotFoundException){
            result.setState(5005);
            result.setMessage("会议ID不存在");
        } else if (e instanceof UserEmailDuplicatedException){
            result.setState(5006);
            result.setMessage("邮箱已被使用");
        } else if (e instanceof ParticipationDuplicatedException){
            result.setState(5007);
            result.setMessage("已经加入该会议");
        } else if (e instanceof DeleteException){
            result.setState(5008);
            result.setMessage("删除记录出现错误");
        } else if (e instanceof ExpiredOperationException){
            result.setState(5009);
            result.setMessage("不能操作已经过期的数据");
        } else if (e instanceof DateTimeIllegalException){
            result.setState(5010);
            result.setMessage("预定时间不能在过去");
        } else if (e instanceof MeetingPasswordNotMatchException){
            result.setState(5011);
            result.setMessage("会议口令错误");
        } else if (e instanceof UserEmailNotFoundException){
            result.setState(5012);
            result.setMessage("邮箱不存在");
        } else if (e instanceof FileEmptyException){
            result.setState(6000);
            result.setMessage("上传文件为空");
        } else if (e instanceof FileSizeException){
            result.setState(6001);
            result.setMessage("上传文件大小超出限制");
        } else if (e instanceof FileStateException){
            result.setState(6002);
            result.setMessage("上传文件状态异常");
        } else if (e instanceof FileTypeException){
            result.setState(6003);
            result.setMessage("上传文件类型错误");
        } else if (e instanceof FileUploadIOException){
            result.setState(6004);
            result.setMessage("上传文件过程中出现未知错误");
        }
        return result;
    }

}

