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

    @Override
    public void execute(FileSystem fs) {
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
    public void readParameters() {

    }

    private static class FSDProcessor{
        //сегменты, которые в процессе обработки заполнились файлами до конца и их нужно очистить от дырок
        private ArrayList<Segment> segmentsToClean = new ArrayList<>();
        //контейнер с блоками,пригодными к обработке
        private HashMap<Integer,LinkedList<Block>> pinsContainer = new HashMap<>();
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
                this.pinsSearch(fs);
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
        //каждый блок содержит в себе информацию о размере конкретного файла и его положении в фс
        private static class Block{
            private final int size;
            private final int segmentIndex;
            private final int dataIndex;
            private Block(int segmentIndex, int dataIndex, int size){
                this.size = size;
                this.dataIndex = dataIndex;
                this.segmentIndex = segmentIndex;
            }

            public int getSegmentIndex(){
                return this.segmentIndex;
            }
            public int getDataIndex(){
                return this.dataIndex;
            }
            public int getSize(){
                return this.size;
            }
        }
        //подбирем файлы к обработке дл второго шага
        //файл подходит к обработке, если он распологается дальше в фс, чем самая первая дырка
        public void pinsSearch(FileSystem fs){
            boolean firstHoleFound = false;
            for(int i=0; i<fs.segments.size();i++){
                Segment segment = fs.segments.get(i);
                for(int j=0; j<segment.datas.size();j++){
                    Data data = segment.datas.get(j);
                    if(data.isType()&&firstHoleFound){
                        //добавляем файл в контейнер
                        addToPinsContainer(data.getSize(),i,j);
                    }
                    if(!data.isType()&&!firstHoleFound){
                        firstHoleFound = true;
                    }
                }
            }
        }

        private void addToPinsContainer(int size, int segmentIndex, int dataIndex){
            if(!this.pinsContainer.containsKey(size)){
                LinkedList<Block> tmpList = new LinkedList<>();
                tmpList.addLast(new Block(segmentIndex,dataIndex,size));
                this.pinsContainer.put(size, tmpList);
            } else{
                Block tmpBlock = new Block(segmentIndex,dataIndex,size);
                this.pinsContainer.computeIfPresent(size, (k,v)->returnExtendedList(v,tmpBlock));
            }
        }

        private LinkedList<Block> returnExtendedList(LinkedList<Block> list, Block block){
            LinkedList<Block> extendedList = new LinkedList<>(list);
            extendedList.addLast(block);
            return extendedList;
        }

        private void removeFromPinsContainer(Block block){
            this.pinsContainer.computeIfPresent(block.getSize(),(k,v)->returnReducedList(v,block));
        }

        private LinkedList<Block> returnReducedList(LinkedList<Block> list, Block block){
            LinkedList<Block> reducedList = new LinkedList<>(list);
            reducedList.remove(block);
            return reducedList;
        }
        //ищем файл подходящего размера, начиная с конца фс и до дырки, которую планируем этим фалйом заткнуть
        private Data findPin(FileSystem fs, Data hole, int segmentBorder, int dataBorder ){
            for(int i=fs.segments.size()-1; i>=segmentBorder;i--){
                Segment segment = fs.segments.get(i);
                if (i==segmentBorder){
                    for (int j = segment.datas.size() - 1; j > dataBorder; j--) {
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
                else {
                    for (int j = segment.datas.size() - 1; j >= 0; j--) {
                        Data data = segment.datas.get(j);
                        if (data.isType() && data.getSize() == hole.getSize()) {
                            Data tmp = data;
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
        //удаляем из сегментов дырки, которые больше не получится заткнуть
        //так как размер сегмента достиг допустимого значения
        public void clearGarbageFromSegments(FileSystem fs){
            for(Segment segment : segmentsToClean) {
                int indexOfSegment = fs.segments.indexOf(segment);
                fs.segments.get(indexOfSegment).datas.removeIf(data -> !data.isType());
                segmentsToClean.remove(segment);
            }
        }
        //ищем комбинацию нескольких файлов, суммарный размер которых будет равен размеру дырки
        //а длина размер комбинации не будет превышать свободного места в сегменте дырки
        private ArrayList<Data> findSeveralPins(FileSystem fs, int reqSize,int holeSegmentIndex, int holeDataIndex, int freeSpace){
            Set<Integer> sizesOfPins = this.pinsContainer.keySet();
            int maxSizeOfPins=Collections.max(sizesOfPins);
            if(maxSizeOfPins*freeSpace<reqSize){
                return null;
            }
            HashMap<Integer, LinkedList<Block>> rearrangedPinsContainer;
            rearrangedPinsContainer = rearrangePinsContainer(reqSize,holeSegmentIndex,holeDataIndex);
            ArrayList<Block> blocksCombination;
            blocksCombination = findBlocksCombination(reqSize,freeSpace, rearrangedPinsContainer);
            if (blocksCombination!=null) {
                ArrayList<Data> pinsToReturn = convertBlocksToDatas(fs, blocksCombination);
                for (Block block : blocksCombination) {
                    removeFromPinsContainer(block);
                    fs.segments.get(block.getSegmentIndex()).datas.get(block.getDataIndex()).setType(false);
                    fs.segments.get(block.getSegmentIndex()).currentDataNum--;
                }
                return pinsToReturn;
            } else{
                return null;
            }
        }
        //подбираем комбинацию блоков прямым перебором
        private ArrayList<Block> findBlocksCombination(int reqSize, int freeSpace, HashMap<Integer, LinkedList<Block>> container) {
            ArrayList<Integer> sizesCombination = new ArrayList<>();
            ArrayList<Integer> sizesOfPins = new ArrayList<>(container.keySet());
            Collections.sort(sizesOfPins);
            HashMap<Integer,Integer> sizesToUse = convertBlocksToSizes(container);
            int sum=0;
            for(int size : sizesOfPins){
                sum+=size*sizesToUse.get(size);
            }
            if(sum<reqSize){
                return null;
            }
            int indexOfSizesArray=sizesOfPins.size()-1;
            int currentSize = sizesOfPins.get(indexOfSizesArray);
            while(true) {
                while (reqSize > currentSize) {
                    if(sizesToUse.get(currentSize)!=0){
                        sizesCombination.add(currentSize);
                        reqSize -= currentSize;
                        freeSpace--;
                        if(freeSpace==0){
                            return null;
                        }
                        sizesToUse.computeIfPresent(currentSize,(k,v)->v-1);
                    } else{
                        break;
                    }
                    if (sizesToUse.containsKey(reqSize)&&sizesToUse.get(reqSize)!=0) {
                        sizesCombination.add(reqSize);
                        sizesToUse.computeIfPresent(reqSize,(k,v)->v-1);
                        freeSpace--;
                        reqSize=0;
                        break;
                    }
                }
                if(reqSize==0){
                    break;
                }
                if(indexOfSizesArray==0){
                    int lastAddedSize = sizesCombination.get(sizesCombination.size()-1);
                    if(lastAddedSize==sizesOfPins.get(0)){
                        while(lastAddedSize==sizesOfPins.get(0)){
                            sizesCombination.remove(sizesCombination.size()-1);
                            reqSize+=lastAddedSize;
                            freeSpace++;
                            sizesToUse.computeIfPresent(lastAddedSize,(k,v)->v+1);
                            lastAddedSize = sizesCombination.get(sizesCombination.size()-1);
                        }
                        if(sizesCombination.size()==0){
                            return null;
                        }
                    }
                    sizesCombination.remove(sizesCombination.size()-1);
                    reqSize+=lastAddedSize;
                    freeSpace++;
                    sizesToUse.computeIfPresent(lastAddedSize,(k,v)->v+1);
                    indexOfSizesArray = sizesOfPins.indexOf(lastAddedSize);
                }
                indexOfSizesArray--;
                currentSize = sizesOfPins.get(indexOfSizesArray);
            }
            return convertSizesToBlocks(sizesCombination,container);
        }
        private HashMap<Integer,Integer> convertBlocksToSizes(HashMap<Integer,LinkedList<Block>> container){
            ArrayList<Integer> sizesOfPins = new ArrayList<>(container.keySet());
            Collections.sort(sizesOfPins);
            HashMap<Integer,Integer> sizesToUse = new HashMap<>();
            for(int size : sizesOfPins){
                sizesToUse.put(size, container.get(size).size());
            }
            return  sizesToUse;
        }
        private ArrayList<Block> convertSizesToBlocks(ArrayList<Integer> sizesCombination,HashMap<Integer,LinkedList<Block>> container){
            ArrayList<Block> blocksCombination = new ArrayList<>();
            for(int size : sizesCombination){
                Block block = container.get(size).getLast();
                blocksCombination.add(block);
            }
            return blocksCombination;
        }
        //из контейнера с блоками выбираем те, что лежат дальше в файловой системе, чем заданная дырка
        private HashMap<Integer, LinkedList<Block>> rearrangePinsContainer( int reqSize,int holeSegmentIndex, int holeDataIndex){
            HashMap<Integer, LinkedList<Block>> rearrangedContainer = new HashMap<>();
            ArrayList<Integer> sizesOfPins = new ArrayList<>( this.pinsContainer.keySet());
            Collections.sort(sizesOfPins);
            for(int size : sizesOfPins){
                if(size<reqSize){
                    LinkedList<Block> tmpList = this.pinsContainer.get(size);
                    LinkedList<Block> rearrangedList = new LinkedList<>();
                    for (Block block : tmpList) {
                        int blockDataIndex = block.getDataIndex();
                        int blockSegmentIndex = block.getSegmentIndex();
                        if (blockSegmentIndex == holeSegmentIndex) {
                            if (blockDataIndex > holeDataIndex) {
                                rearrangedList.add(block);
                            }
                        } else if (blockSegmentIndex > holeSegmentIndex) {
                            rearrangedList.add(block);
                        }
                    }
                    rearrangedContainer.put(size,rearrangedList);
                }
            }
            return rearrangedContainer;
        }
        private ArrayList<Data> convertBlocksToDatas(FileSystem fs, ArrayList<Block> blocks){
            ArrayList<Data> dataToReturn = new ArrayList<>();
            for(Block block : blocks){
                int dataIndex = block.getDataIndex();
                int segmentIndex = block.getSegmentIndex();
                dataToReturn.add(fs.segments.get(segmentIndex).datas.get(dataIndex));
            }
            return dataToReturn;
        }

    }
}
