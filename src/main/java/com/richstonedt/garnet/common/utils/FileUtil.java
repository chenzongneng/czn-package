package com.richstonedt.garnet.common.utils;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.filter.helper.GsonUtil;
import com.richstonedt.garnet.model.view.ReturnTenantIdView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static void writeJsonFile(String content) throws IOException {
        FileWriter fw = new FileWriter(GarnetContants.JSON_FILE_PATH);
        PrintWriter out = new PrintWriter(fw);
        out.write(content);
        out.println();
        fw.close();
        out.close();
    }

    public static void main(String[] args) {

        boolean b = true;
        boolean b1 = false;

       if (!b || (b &&!b1)) {
           System.out.println("true");
       } else {
           System.out.println("false");
       }
    }
}


