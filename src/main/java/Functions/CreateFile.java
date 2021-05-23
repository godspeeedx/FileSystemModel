package Functions;

import Structure.struct.*;

import java.util.Scanner;


public class CreateFile extends BaseCommand implements iCommand {
    protected String fileName;
    protected int fileLength;


    public CreateFile(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }

    public static int createFile(FileSystem fs, String filename, int length) {
        if (!MethodsForFunctions.checkFileExist(fs, filename)) {
            if (length <= MethodsForFunctions.maxToInsert(fs)) {
                for (int i = 0; i < fs.segments.size(); i++) { // пробег по всем сегментам
                    for (int j = 0; j < fs.segments.get(i).dataRecords.size(); j++) { // пробег по сзаписям  в сегменте
                        if (!fs.segments.get(i).dataRecords.get(j).type) { // вставляем на удалённое, когда всё совпало
                            if (length == fs.segments.get(i).dataRecords.get(j).size) {
                                fs.segments.get(i).dataRecords.get(j).type = true;
                                fs.segments.get(i).currentDataNum += 1;
                                fs.segments.get(i).dataRecords.get(j).name = filename;
                                return 0; // файл успешно создан
                            } // случ когда вставляем в самый конец при том, что она была удалена
                            else if (length < fs.segments.get(i).dataRecords.get(j).size) {
                                if ((j != fs.maxDataNum - 1 && fs.segments.get(i).dataRecords.size() - j == 1) ||
                                        (j == fs.maxDataNum - 1 && fs.segments.size() - i == 1)) {
                                    int difference = fs.segments.get(i).dataRecords.get(j).size - length;
                                   // Segment.lastBlockNumber -= difference;
                                    fs.segments.get(i).currentDataNum += 1;
                                    fs.segments.get(i).dataRecords.get(j).type = true;
                                    fs.segments.get(i).dataRecords.get(j).size = length;
                                    fs.segments.get(i).dataRecords.get(j).name = filename;
                                    return 0; // файл успешно создан
                                } // если меньшк и следующий удалён
                                else {
                                    if (j + 1 < fs.segments.get(i).dataRecords.size()
                                            && !fs.segments.get(i).dataRecords.get(j + 1).type) {
                                        int difference = fs.segments.get(i).dataRecords.get(j).size - length;
                                        fs.segments.get(i).dataRecords.get(j).type = true;
                                        fs.segments.get(i).dataRecords.get(j).name = filename;
                                        fs.segments.get(i).dataRecords.get(j).size = length;
                                        fs.segments.get(i).currentDataNum += 1;
                                        fs.segments.get(i).dataRecords.get(j + 1).size += difference;
                                        return 0; // файл успешно создан
                                    }
                                }

                            } else {
                                if (i + 1 == fs.segments.size() && j + 1 == fs.segments.get(i).dataRecords.size()) {
                                    if (MethodsForFunctions.howMuchSpace(fs) >= length) {
                                       // Segment.lastBlockNumber -= fs.segments.get(i).datas.get(j).size;
                                      //  Segment.lastBlockNumber += length;
                                        fs.segments.get(i).dataRecords.get(j).type = true;
                                        fs.segments.get(i).dataRecords.get(j).name = filename;
                                        fs.segments.get(i).currentDataNum += 1;
                                        fs.segments.get(i).dataRecords.get(j).size = length;
                                        return 0; // файл успешно создан
                                    }
                                }
                            }
                        }
                    }
                }
                // Если добавляем в последний сегмент
                if (fs.segments.size() == 0) {
                    fs.segments.add(new Segment(fs.maxDataNum));
                    fs.segments.get(0).dataRecords.add(new DataRecord(filename, length));
                    fs.segments.get(0).currentDataNum += 1;
                   // Segment.lastBlockNumber += length;
                    return 0;
                }
               // int dataSize = fs.segments.get(fs.segments.size() - 1).dataRecords.size() - 1;
                int segmentSize = fs.segments.size() - 1;
                if (fs.segments.get(segmentSize).dataRecords.size() != fs.maxDataNum) {
                    if(MethodsForFunctions.maxToInsertInEnd(fs) >= length) {
                        fs.segments.get(segmentSize).dataRecords.add(new DataRecord(filename, length));
                        fs.segments.get(segmentSize).currentDataNum += 1;
                        return 0; // файл успешно создан
                    }
                      //  Segment.lastBlockNumber += length;
                        return -1; // не хватило места
                }
                // Если добавляем в новый сегмент
                else {
                    if (fs.segments.size() < fs.maxSegmentNum) {
                        if(MethodsForFunctions.maxToInsertInEnd(fs) >= length) {
                            fs.segments.add(new Segment(fs.maxDataNum));
                            fs.segments.get(segmentSize + 1).dataRecords.add(new DataRecord(filename, length));
                            fs.segments.get(segmentSize + 1).currentDataNum += 1;
                            //   Segment.lastBlockNumber += length;
                            return 0; // файл успешно создан
                        }
                        return -1; // не хватило места
                    }
                    else {
                        return -1; // не хватило места
                    }
                }
            } else {
                return -1;// не хватило места
            }

        }
        return 1;//файл уже сущетсвует 
    }

    @Override
    public void execute(FileSystem fs) {
        readParameters();
        int isFileCreate = createFile(fs, this.fileName, this.fileLength);
        if (isFileCreate == 0) {
            monitor.writeMessage("Файл успешно создан");
        } else if (isFileCreate == -1) {
            monitor.writeMessage("Недостаточно свободного места");
        } else if (isFileCreate == 1) {
            monitor.writeMessage("Файл уже существует");
        }
        monitor.writeMessage(MethodsForFunctions.saveSystem(fs));
    }

    @Override
    public void readParameters() {
        this.fileName = monitor.readString("Введите имя файла");
        boolean check = true;
        int fileLength = 0;
        do{
            fileLength = monitor.readInt("Введите длину файла");
        } while (MethodsForFunctions.fileSizeLogic(fileLength));
        this.fileLength = fileLength;

    }
}
