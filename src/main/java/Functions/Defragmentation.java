package Functions;

import Structure.struct.DataRecord;
import Structure.struct.FileSystem;
import Structure.struct.Segment;
import Structure.struct.iCommand;
import java.util.*;

import Structure.struct.iMonitor;

public class Defragmentation extends BaseCommand implements iCommand {

    public Defragmentation(iMonitor im, FileSystem fileSystem) {
        super(im, fileSystem);
    }
    public static void defragmentation(FileSystem fs){
        //создаём процессор дефрагментации
        FSDProcessor processor = new FSDProcessor();
        //меняем местами дырки файлами
        processor.swapPinsAndHoles(fs);
        //удаляем дырки, которые не получилось заткнуть
        processor.deleteHoles(fs);
        //сжимаем фс
        processor.squeezeFS(fs);
    }
    @Override
    public void execute(FileSystem fs) {
        monitor.writeMessage("Степень фрагментации до дефрагментации "
                + MethodsForFunctions.defragExt(fs));
        defragmentation(fs);
        monitor.writeMessage("Степень фрагментации после дефрагментации "
                + MethodsForFunctions.defragExt(fs));
        monitor.writeMessage(MethodsForFunctions.saveSystem(fs));
        monitor.writeMessage("Готово!");
    }

    @Override
    public void readParameters() {

    }

    private static class FSDProcessor{
        //сегменты, которые в процессе обработки заполнились файлами до конца и их нужно очистить от дырок
        private ArrayList<Segment> segmentsToClean = new ArrayList<>();
        //контейнер с блоками,пригодными к обработке
        //затыкаем дырки блоками
        public void swapPinsAndHoles(FileSystem fs){
            boolean checkFirstStep = true, checkSecondStep = true;
            //цикл выполняется до тех пор, пока у нас получается хоть что-то заткнуть
            while(checkFirstStep || checkSecondStep) {
                //первый шаг - затыкаем файлами дырки по одному
                checkFirstStep = this.firstStepOfSwapping(fs);
                //очищаем сегменты от ненужных дырок
                this.clearGarbageFromSegments(fs);
                //второй шаг-затыкаем несколькими файлами одну дырку
                checkSecondStep = this.secondStepOfSwapping(fs);
                this.clearGarbageFromSegments(fs);
            }
        }

        private boolean firstStepOfSwapping(FileSystem fs){
            int swapCount=0;
            for(int i=0; i<fs.segments.size();i++){
                Segment segment = fs.segments.get(i);
                for (int j = 0; j < segment.dataRecords.size(); j++) {
                    DataRecord dataRecord = segment.dataRecords.get(j);
                    if (!dataRecord.isType()) {
                        if(segment.currentDataNum < fs.maxDataNum) {
                            DataRecord pin = findPin(fs, dataRecord, i, j);
                            if (pin != null) {
                                dataRecord.setType(true);
                                dataRecord.setName(pin.getName());
                                segment.currentDataNum++;
                                swapCount++;
                            }
                        }
                        else{
                            if (!this.segmentsToClean.contains(segment)){
                                segmentsToClean.add(segment);
                            }
                        }
                    }
                }
            }
            return swapCount != 0;
        }
        //ищем файл подходящего размера, начиная с конца фс и до дырки, которую планируем этим фалйом заткнуть
        private DataRecord findPin(FileSystem fs, DataRecord hole, int holeSegmentIndex, int holeDataIndex ){
            for(int i=fs.segments.size()-1; i>=holeSegmentIndex;i--){
                Segment segment = fs.segments.get(i);
                if (i==holeSegmentIndex){
                    for (int j = segment.dataRecords.size() - 1; j > holeDataIndex; j--) {
                        DataRecord dataRecord = segment.dataRecords.get(j);
                        if (dataRecord.isType() && dataRecord.getSize() == hole.getSize()) {
                            DataRecord tmp = new DataRecord(dataRecord.getName(), dataRecord.getSize());
                            dataRecord.setType(false);
                            dataRecord.setName(hole.getName());
                            segment.currentDataNum--;
                            return tmp;
                        }
                    }
                }
                else if (i>holeSegmentIndex){
                    for (int j = segment.dataRecords.size() - 1; j >= 0; j--) {
                        DataRecord dataRecord = segment.dataRecords.get(j);
                        if (dataRecord.isType() && dataRecord.getSize() == hole.getSize()) {
                            DataRecord tmp = new DataRecord(dataRecord.getName(), dataRecord.getSize());
                            dataRecord.setType(false);
                            dataRecord.setName(hole.getName());
                            segment.currentDataNum--;
                            return tmp;
                        }
                    }
                }
            }
            return null;
        }

        private boolean secondStepOfSwapping(FileSystem fs){
            int swapCount=0;
            for(int i=0; i<fs.segments.size();i++) {
                Segment segment = fs.segments.get(i);
                for (int j = 0; j < segment.dataRecords.size(); j++) {
                    DataRecord dataRecord = segment.dataRecords.get(j);
                    if (!dataRecord.isType()){
                        int freeSpace = fs.maxDataNum - segment.currentDataNum;
                        if(freeSpace>0) {
                            if(dataRecord.getSize()>1){
                                ArrayList<DataRecord> pins = findSeveralPins(fs, dataRecord.getSize(), i, j, freeSpace);
                                if(pins != null){
                                    segment.dataRecords.remove(dataRecord);
                                    for(DataRecord pin: pins){
                                        segment.dataRecords.add(pin);
                                        segment.currentDataNum++;
                                    }
                                    swapCount++;
                                }
                            }
                        }
                        else{
                            if (!this.segmentsToClean.contains(segment)){
                                segmentsToClean.add(segment);
                            }
                        }
                    }
                }
            }
            return swapCount != 0;
        }
        private ArrayList<DataRecord> findSeveralPins(FileSystem fs, int reqSize, int holeSegmentIndex, int holeDataIndex, int freeSpace){
            ArrayList<DataRecord> pinsCombination = new ArrayList<>();
            for(int i=fs.segments.size()-1; i>=holeSegmentIndex;i--){
                Segment segment = fs.segments.get(i);
                if (i==holeSegmentIndex){
                    for (int j = segment.dataRecords.size() - 1; j > holeDataIndex; j--) {
                        DataRecord dataRecord = segment.dataRecords.get(j);
                        if (dataRecord.isType()) {
                            if(dataRecord.getSize()<=reqSize) {
                                pinsCombination.add(dataRecord);
                                reqSize -= dataRecord.getSize();
                                freeSpace--;
                                if(reqSize==0){
                                    break;
                                }
                                if(freeSpace==0 && reqSize>0){
                                    reqSize+=pinsCombination.get(0).getSize();
                                    freeSpace++;
                                    pinsCombination.remove(0);
                                }
                            }
                        }
                    }
                    if(reqSize==0){
                        break;
                    }
                }
                else if (i>holeSegmentIndex){
                    for (int j = segment.dataRecords.size() - 1; j >= 0; j--) {
                        DataRecord dataRecord = segment.dataRecords.get(j);
                        if (dataRecord.isType()) {
                            if(dataRecord.getSize()<=reqSize) {
                                pinsCombination.add(dataRecord);
                                reqSize -= dataRecord.getSize();
                                freeSpace--;
                                if(reqSize==0){
                                    break;
                                }
                                if(freeSpace==0 && reqSize>0){
                                    reqSize+=pinsCombination.get(0).getSize();
                                    freeSpace++;
                                    pinsCombination.remove(0);
                                }
                            }
                        }
                    }
                    if(reqSize==0){
                        break;
                    }
                }
            }
            if(reqSize==0){
                ArrayList<DataRecord> pinsToReturn = new ArrayList<>(pinsCombination);
                for (DataRecord pin : pinsCombination){
                    deletePinFromFS(pin,fs);
                }
                return pinsToReturn;
            } else {
                return null;
            }
        }
        private void deletePinFromFS(DataRecord pin, FileSystem fs){
            for(int i=fs.segments.size()-1; i>=0; i--){
                for (int j = fs.segments.get(i).dataRecords.size()-1; i>=0; i--){
                    DataRecord dataRecord = fs.segments.get(i).dataRecords.get(j);
                    if (dataRecord.getName()==pin.getName()&& dataRecord.isType()){
                        fs.segments.get(i).currentDataNum--;
                        dataRecord.setType(false);
                    }
                }
            }
        }
        //удаляем из сегментов дырки, которые больше не получится заткнуть
        //так как размер сегмента достиг допустимого значения
        public void clearGarbageFromSegments(FileSystem fs){
            for(Segment segment : segmentsToClean) {
                int indexOfSegment = fs.segments.indexOf(segment);
                fs.segments.get(indexOfSegment).dataRecords.removeIf(data -> !data.isType());
            }
            segmentsToClean.clear();
        }
        public void deleteHoles(FileSystem fs) {
            for (Segment segment : fs.segments) {
                segment.dataRecords.removeIf(dataRecord -> !dataRecord.isType());
            }
        }

        public void squeezeFS(FileSystem fs){
            int maxDataNum = fs.maxDataNum;
            int currentSegmentIndex=0;
            int lastSegmentIndex=fs.segments.size()-1;
            flag:
            for(Segment segment : fs.segments){
                while(segment.currentDataNum<maxDataNum){
                    if(currentSegmentIndex==lastSegmentIndex)
                        break flag;
                    while(fs.segments.get(lastSegmentIndex).currentDataNum==0){
                        lastSegmentIndex--;
                        if(lastSegmentIndex==currentSegmentIndex){
                            break flag;
                        }
                    }
                    int lastDataIndex = fs.segments.get(lastSegmentIndex).dataRecords.size()-1;
                    DataRecord dataRecordToReplace = fs.segments.get(lastSegmentIndex).dataRecords.get(lastDataIndex);
                    segment.dataRecords.add(dataRecordToReplace);
                    segment.currentDataNum++;
                    fs.segments.get(lastSegmentIndex).dataRecords.remove(lastDataIndex);
                    fs.segments.get(lastSegmentIndex).currentDataNum--;
                }
                currentSegmentIndex++;
            }
            fs.segments.removeIf(segment -> segment.currentDataNum==0);
        }
    }
}