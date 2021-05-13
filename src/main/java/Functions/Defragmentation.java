package Functions;

import Structure.struct.Data;
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
        if (MethodsForFunctions.checkDef(fs)){
            monitor.writeMessage("Степень фрагментации "
                    + MethodsForFunctions.defragExt(fs)+ ". Дефрагментация необходима.");
            defragmentation(fs);
            monitor.writeMessage(MethodsForFunctions.saveSystem(fs));
        }
        else{
            monitor.writeMessage("Степень фрагментации "
                    + MethodsForFunctions.defragExt(fs)+ ". Дефрагментация не нужна.");
        }

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
                //ищем подходящие файлы для второго шага
                //this.pinsSearch(fs);
                //второй шаг-затыкаем несколькими файлами одну дырку
                checkSecondStep = this.secondStepOfSwapping(fs);
                this.clearGarbageFromSegments(fs);
            }
        }

        private boolean firstStepOfSwapping(FileSystem fs){
            int swapCount=0;
            for(int i=0; i<fs.segments.size();i++){
                Segment segment = fs.segments.get(i);
                for (int j = 0; j < segment.datas.size(); j++) {
                    Data data = segment.datas.get(j);
                    if (!data.isType()) {
                        if(segment.currentDataNum < fs.maxDataNum) {
                            Data pin = findPin(fs, data, i, j);
                            if (pin != null) {
                                data.setName(pin.getName());
                                data.setType(true);
                                data.setName(pin.getName());
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
        private Data findPin(FileSystem fs, Data hole, int holeSegmentIndex, int holeDataIndex ){
            for(int i=fs.segments.size()-1; i>=holeSegmentIndex;i--){
                Segment segment = fs.segments.get(i);
                if (i==holeSegmentIndex){
                    for (int j = segment.datas.size() - 1; j > holeDataIndex; j--) {
                        Data data = segment.datas.get(j);
                        if (data.isType() && data.getSize() == hole.getSize()) {
                            Data tmp = new Data(data.getName(),data.getSize());
                            data.setType(false);
                            data.setName(hole.getName());
                            segment.currentDataNum--;
                            return tmp;
                        }
                    }
                }
                else if (i>holeSegmentIndex){
                    for (int j = segment.datas.size() - 1; j >= 0; j--) {
                        Data data = segment.datas.get(j);
                        if (data.isType() && data.getSize() == hole.getSize()) {
                            Data tmp = new Data(data.getName(),data.getSize());
                            data.setType(false);
                            data.setName(hole.getName());
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
                for (int j = 0; j < segment.datas.size(); j++) {
                    Data data = segment.datas.get(j);
                    if (!data.isType()){
                        int freeSpace = fs.maxDataNum - segment.currentDataNum;
                        if(freeSpace>0) {
                            if(data.getSize()>1){
                                ArrayList<Data> pins = findSeveralPins(fs, data.getSize(), i, j, freeSpace);
                                if(pins != null){
                                    segment.datas.remove(data);
                                    for(Data pin: pins){
                                        segment.datas.add(pin);
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
        private ArrayList<Data> findSeveralPins(FileSystem fs, int reqSize,int holeSegmentIndex, int holeDataIndex, int freeSpace){
            ArrayList<Data> pinsCombination = new ArrayList<>();
            for(int i=fs.segments.size()-1; i>=holeSegmentIndex;i--){
                Segment segment = fs.segments.get(i);
                if (i==holeSegmentIndex){
                    for (int j = segment.datas.size() - 1; j > holeDataIndex; j--) {
                        Data data = segment.datas.get(j);
                        if (data.isType()) {
                            if(data.getSize()<=reqSize) {
                                pinsCombination.add(data);
                                reqSize -= data.getSize();
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
                    for (int j = segment.datas.size() - 1; j >= 0; j--) {
                        Data data = segment.datas.get(j);
                        if (data.isType()) {
                            if(data.getSize()<=reqSize) {
                                pinsCombination.add(data);
                                reqSize -= data.getSize();
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
                ArrayList<Data> pinsToReturn = new ArrayList<>(pinsCombination);
                for (Data pin : pinsCombination){
                    deletePinFromFS(pin,fs);
                }
                return pinsToReturn;
            } else {
                return null;
            }
        }
        private void deletePinFromFS(Data pin, FileSystem fs){
            for(int i=fs.segments.size()-1; i>=0; i--){
                for (int j=fs.segments.get(i).datas.size()-1;i>=0;i--){
                    Data data = fs.segments.get(i).datas.get(j);
                    if (data.getName()==pin.getName()&&data.isType()){
                        fs.segments.get(i).currentDataNum--;
                        data.setType(false);
                    }
                }
            }
        }
        //удаляем из сегментов дырки, которые больше не получится заткнуть
        //так как размер сегмента достиг допустимого значения
        public void clearGarbageFromSegments(FileSystem fs){
            for(Segment segment : segmentsToClean) {
                int indexOfSegment = fs.segments.indexOf(segment);
                fs.segments.get(indexOfSegment).datas.removeIf(data -> !data.isType());
            }
            segmentsToClean.clear();
        }
        public void deleteHoles(FileSystem fs) {
            for(int i=0; i<fs.segments.size();i++) {
                Segment segment = fs.segments.get(i);
                for (int j = 0; j < segment.datas.size(); j++) {
                    Data data = segment.datas.get(j);
                    if (!data.isType()){
                        segment.datas.remove(data);
                    }
                }
            }
        }

        public void squeezeFS(FileSystem fs){
            int maxDataNum = fs.maxDataNum;
            int currentSegmentIndex=0;
            for(Segment segment : fs.segments){
                while(segment.currentDataNum<maxDataNum){
                    int lastSegmentIndex=fs.segments.size()-1;
                    if(currentSegmentIndex==lastSegmentIndex)
                        break;
                    int lastDataIndex = fs.segments.get(lastSegmentIndex).datas.size()-1;
                    Data dataToReplace = fs.segments.get(lastSegmentIndex).datas.get(lastDataIndex);
                    segment.datas.add(dataToReplace);
                    segment.currentDataNum++;
                    fs.segments.get(lastSegmentIndex).datas.remove(lastDataIndex);
                    fs.segments.get(lastSegmentIndex).currentDataNum--;
                    if(fs.segments.get(lastSegmentIndex).currentDataNum==0){
                        fs.segments.remove(lastSegmentIndex);
                    }
                }
                currentSegmentIndex++;
            }
        }
    }
}