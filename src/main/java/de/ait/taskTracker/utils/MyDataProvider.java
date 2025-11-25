package de.ait.taskTracker.utils;

import de.ait.taskTracker.dto.AuthRequestDto;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyDataProvider {

    @DataProvider
    public Iterator<Object[]> createNewUserAPI() throws IOException {
        List<Object[]> list = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/data/positiveReg.csv")));

        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(",");
            list.add(new Object[]{AuthRequestDto.builder()
                    .email(split[0])
                    .password(split[1])
                    .build()});
            line = reader.readLine();
        }
        return list.iterator();
    }
}
