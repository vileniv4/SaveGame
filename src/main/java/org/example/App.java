package org.example;


import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class App {
    public static void main(String[] args) {

        System.out.println("Сериализация");
        saveGame();

        System.out.println("Архивирование");
        zipFiles();

        System.out.println("Удаление файлов сохранений, не лежащих в архиве");
        deleteSavedFiles();

        System.out.println("Распаковка архива и десериализация");
        openZip();

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

        String zipPath = "/Users/ekaterina/Games/savegames/zip.zip";
        String[] files = {
                "/Users/ekaterina/Games/savegames/save.dat",
                "/Users/ekaterina/Games/savegames/save1.dat",
                "/Users/ekaterina/Games/savegames/save2.dat"
        };

        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            byte[] buffer = new byte[1024];

            for (String filePath : files) {
                File file = new File(filePath);

                if (!file.exists()) {
                    System.out.println("Файл не найден " + filePath);
                    continue;
                }
                ZipEntry entry = new ZipEntry(file.getName());
                zos.putNextEntry(entry);

                try (FileInputStream fis = new FileInputStream(file)) {
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                }
                zos.closeEntry();
                System.out.println("Добавлен в архив " + file.getName());
            }
            System.out.println("Архивация прошла успешно");
        } catch (Exception ex) {
            System.out.println("Ошибка при архивации " + ex.getMessage());
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


    // Распаковка архива
    public static void openZip() {

        // Путь для распаковки
        String extractPath = "/Users/ekaterina/Games/savegames/";

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(extractPath + "zip.zip"))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                String fileName = entry.getName(); // Получаем имя файла из архива

                // Формируем ПОЛНЫЙ путь для распаковки: папка + имя файла
                String fullPath = extractPath + fileName;

                System.out.println("Распаковываем: " + fileName + " → " + fullPath);

                // Создаём поток для записи в нужную папку
                FileOutputStream fout = new FileOutputStream(fullPath);

                // Буфер для быстрого чтения/записи (1024 байта)
                byte[] buffer = new byte[1024];
                int length;
                while ((length = zin.read(buffer)) > 0) {
                    fout.write(buffer, 0, length);
                }

                fout.close();
                zin.closeEntry();

                System.out.println("Файл распакован: " + fileName);
            }
            System.out.println("Все файлы успешно распакованы в папку: " + extractPath);

        } catch (Exception ex) {
            System.out.println("Ошибка при распаковке: " + ex.getMessage());
            ex.printStackTrace();
        }


        // Десериализация

        GameProgress gameProgress = null;

        // откроем входной поток для чтения файла
        try (FileInputStream fis = new FileInputStream("/Users/ekaterina/Games/savegames/save2.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            // десериализуем объект и скастим его в класс
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Состояние сохранненой игры:");
        System.out.println(gameProgress);

    }
}
