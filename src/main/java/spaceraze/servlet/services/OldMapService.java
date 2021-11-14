package spaceraze.servlet.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import spaceraze.servlet.repositories.MapRepository;
import spaceraze.util.general.Logger;
import spaceraze.world.Map;
import spaceraze.world.MapStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@AllArgsConstructor
@Service
public class OldMapService {
    private final MapRepository mapRepository;

    //@Value("${old.datapath}")
    //private String dataPath;

    private Environment env;
    private ApplicationContext context;


    public List<Map> createMapsFromOldFile() {

        List<Map> allMaps = getAllMaps();
        return mapRepository.saveAll(allMaps);
    }

    private List<Map> getAllMaps(){
        String dataPath = env.getProperty("old.datapath");

        List<Map> allMaps = new ArrayList<>();
        Logger.finer("getAllMaps() called");
        // read maps from file and create allMaps List
        /*if (dataPath == null){
            dataPath = PropertiesHandler.getProperty("datapath");
        }*/
        Logger.finer("basePath: " + dataPath);
        String completePath = dataPath + "maps\\";
        List<String> allMapNames = getProps(completePath);
        //TODO läs även in drafts
        allMaps.addAll(loadAllMapsFromFolder(completePath));
        /*
        for (String mapName : allMapNames) {
            Properties prop = new Properties();

            try {
                File resource = null;
                resource = new ClassPathResource("\\maps\\" + mapName + ".properties").getFile();
                if(resource.isFile()) {
                    Reader reader = Files.newBufferedReader(Paths.get(resource.getPath()));
                    BufferedReader br = new BufferedReader(reader);
                    String aLine = br.readLine();
                    while (aLine != null) {
//				System.out.println(aLine);
                        aLine = br.readLine();
                        if (isKeyValueRow(aLine)) {
                            String key = getKey(aLine);
                            String value = getValue(aLine);
//					System.out.println("Key: " + key + " Value: " + value);
                            prop.put(key, value);
                        }
                    }
                    br.close();
                    Map map = new Map();
                    map.initMap(mapName, prop);
                    map.setStatus(MapStatus.PUBLISHED);
                    allMaps.add(map);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            //allMaps.add(new Map(mapName));
        }*/
        return allMaps;
    }

    private List<Map> loadAllMapsFromFolder(String completePath){
        List<Map> allMaps = new ArrayList<>();
        List<String> allMapNames = getProps(completePath);
        for (String mapName : allMapNames) {
            Properties prop = new Properties();

            try {
                File resource = null;
                resource = new ClassPathResource("\\maps\\" + mapName + ".properties").getFile();
                if (resource.isFile()) {
                    Reader reader = Files.newBufferedReader(Paths.get(resource.getPath()));
                    BufferedReader br = new BufferedReader(reader);
                    String aLine = br.readLine();
                    while (aLine != null) {
//				System.out.println(aLine);
                        aLine = br.readLine();
                        if (isKeyValueRow(aLine)) {
                            String key = getKey(aLine);
                            String value = getValue(aLine);
//					System.out.println("Key: " + key + " Value: " + value);
                            prop.put(key, value);
                        }
                    }
                    br.close();
                    Map map = new Map();
                    map.initMap(mapName, prop);
                    map.setStatus(MapStatus.PUBLISHED);
                    allMaps.add(map);
                }else{
                    allMaps.addAll(loadAllMapsFromFolder(resource.getPath()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allMaps;
    }

    private List<Map> getMapDrafts(String playerLogin){
        String dataPath = env.getProperty("old.datapath");
        List<Map> allMaps = new ArrayList<>();
        Logger.finer("getMapDrafts() called");
        // read maps from file and create allMaps List
        /*if (dataPath == null){
            dataPath = PropertiesHandler.getProperty("datapath");
        }*/
        Logger.finer("dataPath: " + dataPath);
        String completePath = dataPath + "maps\\" + playerLogin + "\\";
        List<String> allMapNames = getProps(completePath);
        for (String mapName : allMapNames) {
            allMaps.add(new Map(playerLogin + "." + mapName));
        }
        return allMaps;
    }
    private List<String> getProps(String folderPath){
        Logger.fine("MapHandler.getProps: folderPath=" + folderPath);
        List<String> allMapNames = new LinkedList<String>();
        File propFolder;
        try {
            propFolder = context.getResource("classpath:" + folderPath).getFile();
            //File propFolder = new File(folderPath);
            if (propFolder.exists()){
                File[] propFiles = propFolder.listFiles(new MapFileNameFilter());
                for (int i = 0; i < propFiles.length; i++) {
                    File file = propFiles[i];
                    String mapName = extractMapName(file.getName());
                    allMapNames.add(mapName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return allMapNames;
    }

    private static String extractMapName(String propFileName){
//		int index1 = propFileName.indexOf(".");
        int index2 = propFileName.lastIndexOf(".");
//		String mapName = propFileName.substring(index1+1,index2);
        String mapName = propFileName.substring(0,index2);
        return mapName;
    }

    private static boolean isKeyValueRow(String aLine){
        return !(aLine == null || aLine.startsWith("#") || !aLine.contains("="));
    }

    private static String getKey(String aLine){
        int indexEqualSign = aLine.indexOf("=");
        String keyPart = aLine.substring(0,indexEqualSign);
        keyPart = keyPart.trim();
        return keyPart;
    }

    private static String getValue(String aLine){
        int indexEqualSign = aLine.indexOf("=");
        String valuePart = aLine.substring(indexEqualSign + 1);
        valuePart = valuePart.trim();
        return valuePart;
    }

}
