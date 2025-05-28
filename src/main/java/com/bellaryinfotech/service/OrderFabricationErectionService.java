package com.bellaryinfotech.service;

import com.bellaryinfotech.model.OrderFabricationErection;
import java.util.List;

public interface OrderFabricationErectionService {
    List<String> getAllErectionMkds();
    List<String> getAllErectionMkdsFromImport();
    List<OrderFabricationErection> saveErectionDetails(List<OrderFabricationErection> erectionDetails);
    List<OrderFabricationErection> storeErectionMkdsFromImport(List<String> erectionMkds);
    List<OrderFabricationErection> storeErectionMkdsFromDetails(List<String> erectionMkds);
    void updateStatusByErectionMkd(String erectionMkd, String status);
    void updateImportStatusByErectionMkd(String erectionMkd, String status);
    List<OrderFabricationErection> getStoredErectionRecords();

    
}
