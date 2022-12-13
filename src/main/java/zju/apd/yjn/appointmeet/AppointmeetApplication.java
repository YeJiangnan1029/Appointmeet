package zju.apd.yjn.appointmeet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("zju.apd.yjn.appointmeet.mapper")
@SpringBootApplication
public class AppointmeetApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointmeetApplication.class, args);
    }

}
