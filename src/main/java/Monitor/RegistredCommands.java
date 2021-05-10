package Monitor;

import Functions.*;

import java.util.HashMap;
import java.util.Map;

public class RegistredCommands {

    public static Map<String, String> registredCommands = new HashMap<>();

    public static void init() {
        registredCommands.put("ИЗМЕНИТЬ РАЗМЕР", ChangeFileSize.class.getName());
        registredCommands.put("СОЗДАТЬ ФАЙЛ", CreateFile.class.getName());
        registredCommands.put("СОЗДАТЬ СИСТЕМУ", CreateSystem.class.getName());
        registredCommands.put("ДЕФРАГМЕНТАЦИЯ", Defragmentation.class.getName());
        registredCommands.put("УДАЛИТЬ ФАЙЛ", DeleteFile.class.getName());
        registredCommands.put("ЗАГРУЗИТЬ СИСТЕМУ", DownloadSystem.class.getName());
        registredCommands.put("НАПЕЧАТАТЬ", Print.class.getName());
        registredCommands.put("НАПЕЧАТАТЬ В АЛФАВИТНОМ ПОРЯДКЕ", PrintInAlphabetOrder.class.getName());
        registredCommands.put("ПОМОГИТЕ", Help.class.getName());
        registredCommands.put("ИНФОРМАЦИЯ", GetSysInfo.class.getName());
    }

}
