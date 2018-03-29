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

        ReturnTenantIdView returnTenantIdView = new ReturnTenantIdView();
        List<Long> tenantIds = new ArrayList<>();
        tenantIds.add(11l);
        tenantIds.add(12l);
        tenantIds.add(13l);
        tenantIds.add(14l);
        tenantIds.add(15l);
        returnTenantIdView.setTenantIds(tenantIds);
        returnTenantIdView.setSuperAdmin(true);

        System.out.println(GsonUtil.GsonString(returnTenantIdView));

//        JSONObject jsonObject = new JSONObject();
//        JSONObject jsonObject1 = new JSONObject();
//        JSONObject jsonObject2 = new JSONObject();
//        JSONArray jsonArray1 = new JSONArray();
//        jsonObject.put("aaa", "aaa");
//        jsonObject.put("bbb", "bbb");
//        jsonObject.put("ccc", "ccc");
//        jsonObject1.put("ddd", "ddd");
//        jsonObject1.put("eee", "eee");
//        jsonObject2.put("fff", "fff");
//        jsonObject2.put("ggg", "ggg");
//        jsonObject2.put("www", "www");
//        jsonObject2.put("qqq", "qqq");
//        jsonObject2.put("ttt", "ttt");
//        jsonArray1.add(jsonObject1);
//        jsonObject.put("list", jsonArray1.toString());
//
//
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(jsonObject);
//        jsonArray.add(jsonObject2);
//        System.out.println("jsonArray == \n" + jsonArray.toString());
    }
}


