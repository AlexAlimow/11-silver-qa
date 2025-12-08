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

    /**
     * Универсальный DataProvider для любого CSV-файла.
     * @param filePath путь к CSV файлу, например "src/test/resources/data/positiveReg.csv"
     * @return Iterator<Object[]> для теста
     */
    public Iterator<Object[]> fromCsv(String filePath) throws IOException {
        List<Object[]> list = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
        String line = reader.readLine();

        while (line != null) {
            String[] split = line.split(",");
            // Создаем DTO только если есть хотя бы 2 колонки
            if (split.length >= 2) {
                list.add(new Object[]{AuthRequestDto.builder()
                        .email(split[0].trim())
                        .password(split[1].trim())
                        .build()});
            }
            line = reader.readLine();
        }
        reader.close();
        return list.iterator();
    }

    // Можно добавить “обёртку” для TestNG с конкретным файлом по умолчанию
    @DataProvider(name = "defaultUserData")
    public Iterator<Object[]> defaultUserData() throws IOException {
        return fromCsv("src/test/resources/data/positiveReg.csv");
    }

    // Пример, если хочешь другой CSV
    @DataProvider(name = "otherData")
    public Iterator<Object[]> otherData() throws IOException {
        return fromCsv("src/test/resources/data/otherFile.csv");
    }
}
