package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.OrderFabricationAlignmentDTO;
import java.util.List;

public interface OrderFabricationAlignmentService {
    List<OrderFabricationAlignmentDTO> getAllAlignments();
    List<OrderFabricationAlignmentDTO> storeSelectedErectionMkds(List<String> erectionMkds);
	List<OrderFabricationAlignmentDTO> getLatestStoredAlignments();
	List<OrderFabricationAlignmentDTO> getAlignmentsByErectionMkds(List<String> erectionMkds);
	void clearLatestStoredFlag();
	List<String> getAllDistinctErectionMkds();
}
