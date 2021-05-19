package Monitor;

import Functions.*;

import java.util.HashMap;
import java.util.Map;

public class RegistredCommands {

    public static Map<String, String> registeredCommands = new HashMap<>();

    public static void init() {
        registeredCommands.put("ИЗМЕНИТЬ РАЗМЕР", ChangeFileSize.class.getName());
        registeredCommands.put("СОЗДАТЬ ФАЙЛ", CreateFile.class.getName());
        registeredCommands.put("СОЗДАТЬ СИСТЕМУ", CreateSystem.class.getName());
        registeredCommands.put("ДЕФРАГМЕНТАЦИЯ", Defragmentation.class.getName());
        registeredCommands.put("УДАЛИТЬ ФАЙЛ", DeleteFile.class.getName());
        registeredCommands.put("ЗАГРУЗИТЬ СИСТЕМУ", DownloadSystem.class.getName());
        registeredCommands.put("НАПЕЧАТАТЬ", Print.class.getName());
        registeredCommands.put("НАПЕЧАТАТЬ В АЛФАВИТНОМ ПОРЯДКЕ", PrintInAlphabetOrder.class.getName());
        registeredCommands.put("ПОМОГИТЕ", Help.class.getName());
        registeredCommands.put("ИНФОРМАЦИЯ", GetSysInfo.class.getName());
    }

}
