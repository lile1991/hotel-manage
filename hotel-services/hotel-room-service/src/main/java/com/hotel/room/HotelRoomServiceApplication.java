package com.hotel.room;

import com.hotel.room.config.HotelRoomServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@Import(HotelRoomServiceConfig.class)
public class HotelRoomServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelRoomServiceApplication.class);
    }
}