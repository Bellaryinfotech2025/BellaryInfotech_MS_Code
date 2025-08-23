package com.bellaryinfotech.service;

import java.util.List;
import com.bellaryinfotech.DTO.FabricationDrawingEntryDto;

public interface FabricationDrawingEntryService {

	List<FabricationDrawingEntryDto> createFabricationDrawingEntries(
			List<FabricationDrawingEntryDto> fabricationEntries);

	List<FabricationDrawingEntryDto> getFabricationEntriesByWorkOrderAndBuilding(String workOrder, String buildingName);

	List<FabricationDrawingEntryDto> getFabricationEntriesByDrawingAndMark(String drawingNo, String markNo);

	List<FabricationDrawingEntryDto> getFabricationEntriesByOrderId(Long orderId);

	List<FabricationDrawingEntryDto> getAllFabricationEntries();

	FabricationDrawingEntryDto getFabricationEntryById(Long id);

	void deleteFabricationEntry(Long id);

	List<FabricationDrawingEntryDto> getFabricationEntriesByMultipleFilters(String workOrder, String buildingName,
			String drawingNo, String markNo);

	Long getCountByWorkOrder(String workOrder);

	Long getCountByBuildingName(String buildingName);

	Long getCountByDrawingAndMark(String drawingNo, String markNo);

	boolean existsByLineId(Long lineId);

	void deleteByLineId(Long lineId);

	// NEW: Method to get distinct RA numbers
	List<String> getDistinctRaNumbers();

	// NEW: Methods to get distinct drawing and mark numbers by order ID
	List<String> getDistinctDrawingNumbersByOrderId(Long orderId);

	List<String> getDistinctMarkNumbersByOrderId(Long orderId);

	// NEW: Method to get total marked weight by drawing number and mark number
	Double getTotalMarkedWeightByDrawingAndMark(Long orderId, String drawingNo, String markNo);

	List<String> getDistinctItemNumbers();

	List<FabricationDrawingEntryDto> getFabricationEntriesByWorkOrderAndRaNo(String workOrder, String raNo);
}
