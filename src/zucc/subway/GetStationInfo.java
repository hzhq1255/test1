package zucc.subway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetStationInfo {
    //读取json文件
	public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	//将json文件中的信息保存
    public static List getStation(String path){
        StationManager stationManager = new StationManager();
        LineManger lineManager = new LineManger();
        JSONObject jobj = JSON.parseObject(readJsonFile(path));
        JSONArray Jlines = jobj.getJSONArray("lines");
        for (int i=0; i<Jlines.size();i++){
            JSONObject Jline = Jlines.getJSONObject(i);
            String line,station;
            line = Jline.getString("lineNo");
            station = Jline.getString("stationName");
            JSONArray Jstations = Jline.getJSONArray("stations");
            for (int j = 0; j < Jstations.size();j++) {
            	JSONObject Jstation = Jstations.getJSONObject(j);
            	station = Jstation.getString("stationName");
                stationManager.addStation(station,line);
                lineManager.addLine(line,station);
            } 
        }
        List list = new ArrayList();
        list.add(stationManager);
        list.add(lineManager);
        return list;
    }
}
