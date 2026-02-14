package org.example;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class App {
    public static void main(String[] args) {

        System.out.println("Сериализация");
        saveGame();

        System.out.println("Архивирование");
        zipFiles();

        System.out.println("Удаление файлов сохранений, не лежащих в архиве");
        deleteSavedFiles();
    }

    public static void saveGame() {

        // 1 игра
        GameProgress gameProgress = new GameProgress(80, 5, 3, 121.3);

        System.out.println("Создан объект; " + gameProgress);

        try (FileOutputStream fos = new FileOutputStream("/Users/ekaterina/Games/savegames/save.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            System.out.println("Объект успешно сохранен в файл 'save.dat'");
        } catch (Exception ex) {
            System.out.println("Ошибка при сохранении: " + ex.getMessage());
        }

        // 2 игра
        GameProgress gameProgress1 = new GameProgress(100, 12, 10, 250.5);

        System.out.println("Создан объект; " + gameProgress1);

        try (FileOutputStream fos = new FileOutputStream("/Users/ekaterina/Games/savegames/save1.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress1);
            System.out.println("Объект успешно сохранен в файл 'save1.dat'");
        } catch (Exception ex) {
            System.out.println("Ошибка при сохранении: " + ex.getMessage());
        }


        // 3 игра
        GameProgress gameProgress2 = new GameProgress(30, 55, 17, 381.11);

        System.out.println("Создан объект; " + gameProgress2);

        try (FileOutputStream fos = new FileOutputStream("/Users/ekaterina/Games/savegames/save2.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress2);
            System.out.println("Объект успешно сохранен в файл 'save2.dat'");
        } catch (Exception ex) {
            System.out.println("Ошибка при сохранении: " + ex.getMessage());
        }
    }

    public static void zipFiles() {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("/Users/ekaterina/Games/savegames/zip.zip"));
             FileInputStream fis = new FileInputStream("/Users/ekaterina/Games/savegames/save.dat")) {
            ZipEntry entry = new ZipEntry("save.dat");
            ZipEntry entry1 = new ZipEntry("save1.dat");
            ZipEntry entry2 = new ZipEntry("save2.dat");
            zout.putNextEntry(entry);
            zout.putNextEntry(entry1);
            zout.putNextEntry(entry2);
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            // добавляем содержимое к архиву
            zout.write(buffer);
            // закрываем текущую запись для новой записи
            zout.closeEntry();
            System.out.println("Архивация прошла успешно!");
        } catch (Exception ex) {
            System.out.println("Ошибка при архивировании: " + ex.getMessage());
        }
    }

    // Удаление файлов сохранений, не лежащие в архиве
    public static void deleteSavedFiles() {

        String[] filesToDelete = {
                "/Users/ekaterina/Games/savegames/save.dat",
                "/Users/ekaterina/Games/savegames/save1.dat",
                "/Users/ekaterina/Games/savegames/save2.dat"
        };

        for (String filePath : filesToDelete) {
            File file = new File(filePath);

            // Проверяем, существует ли файл
            if (file.exists()) {
                // Удаляем файл
                if (file.delete()) {
                    System.out.println("Файл удалён: " + file.getName());
                } else {
                    System.out.println("Не удалось удалить файл: " + file.getName());
                }
            } else {
                System.out.println("Файл уже удалён или не существует: " + file.getName());
            }
        }
    }
}
