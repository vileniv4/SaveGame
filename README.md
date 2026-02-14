# Задача 2: Сохранение

## Описание

В данной задаче Вы потренируетесь сериализовывать Java класс, используя интерфейс Serializable, записывать сериализованные файлы на жесткий диск, используя класс FileOutputStream, и упаковывать их в архив с помощью ZipOutputStream.

Для дальнейшей работы потребуется создать класс GameProgress, хранящий информацию об игровом процессе. Предлагаем следующую реализацию:

````
public class GameProgress implements Serializable {
private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }
}
````

Таким образом, для выполнения задания потребуется проделать следующие шаги:

1. Создать три экземпляра класса GameProgress.
2. Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
3. Созданные файлы сохранений из папки savegames запаковать в один архив zip.
4. Удалить файлы сохранений, лежащие вне архива.

## Реализация

Создайте три экземпляра класса GameProgress.

Реализуйте метод saveGame(), принимающий в качестве аргументов полный путь к файлу типа String (например, "/Users/admin/Games/GunRunner/savegames/save3.dat") и объект класса GameProgress. Для записи Вам потребуются такие классы как FileOutputStream и ObjectOutputStream. У последнего есть метод writeObject(), подходящий для записи сериализованного объекта . Во избежание утечек памяти, не забудьте либо использовать try с ресурсами, либо вручную закрыть файловые стримы (это касается всех случаев работы с файловыми потоками).

Таким образом, вызов метода saveGame() должен приводить к созданию файлов сохранений в папке savegames.

Далее реализуйте метод zipFiles(), принимающий в качестве аргументов String полный путь к файлу архива (например, "/Users/admin/Games/GunRunner/savegames/zip.zip") и список запаковываемых объектов в виде списка строчек String полного пути к файлу (например, "/Users/admin/Games/GunRunner/savegames/save3.dat"). В методе Вам потребуется реализовать блок try-catch с объектами ZipOutputStream и FileOutputStream. Внутри него пробегитесь по списку файлов и для каждого организуйте запись в блоке try-catch через FileInputStream. Для этого создайте экземпляр ZipEntry и уведомьте ZipOutputStream о новом элементе архива с помощью метода putNextEntry(). Далее необходимо считать упаковываемый файл с помощью метода read() и записать его с помощью метода write(). После чего уведомьте ZipOutputStream о записи файла в архив с помощью метода closeEntry().

Далее, используя методы класса File, удалите файлы сохранений, не лежащие в архиве.

# Задача 3: Загрузка (со звездочкой *)

## Описание

В данной задаче необходимо произвести обратную операцию по разархивации архива и десериализации файла сохраненной игры в Java класс.

Таким образом, для выполнения задания потребуется проделать следующие шаги:

1. Произвести распаковку архива в папке savegames.
2. Произвести считывание и десериализацию одного из разархивированных файлов save.dat.
3. Вывести в консоль состояние сохранненой игры.
## Реализация

Реализуйте метод openZip(), который принимал бы два аргумента: путь к файлу типа String (например, "/Users/admin/Games/GunRunner/savegames/zip.zip") и путь к папке, куда стоит разархивировать файлы типа String (например, "/Users/admin/Games/GunRunner/savegames"). Для распаковки Вам потребуются такие стримовые классы как FileInputStream, ZipInputStream и класс объекта архива ZipEntry. Считывание элементов аврхива производится с помощью метода getNextEntry() класса ZipInputStream, а узнать название объекта ZipEntry можно с помощью метода getName(). Запись в файл распакованных объектов можно произвести с помощью FileOutputStream.

Далее реализуйте метод openProgress(), который бы в качестве аргумента принимал путь к файлу с сохраненной игрой типа String (например, "/Users/admin/Games/GunRunner/savegames/save2.dat") и возвращал объект типа GameProgress. В данном методе Вам потребуются классы FileInputStream и ObjectInputStream. С помощью метода класса ObjectInputStream readObject() можно десериализовать объект, а далее привести (скастить) его к GameProgress.

Так как в классе GameProgress метод toString() уже переопределен, поэтому достаточно вывести полученный объект в консоль.