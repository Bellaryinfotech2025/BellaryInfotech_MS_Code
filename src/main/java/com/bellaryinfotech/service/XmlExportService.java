package com.bellaryinfotech.service;

import com.bellaryinfotech.DTO.BitsHeaderDto;
import com.bellaryinfotech.DTO.BitsLinesDto;
import com.bellaryinfotech.DTO.XmlExportDto;
import com.bellaryinfotech.DTO.XmlExportDto.PurchaseOrderXml;
import com.bellaryinfotech.DTO.XmlExportDto.OrderLineXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlExportService {
    
    private static final Logger LOG = LoggerFactory.getLogger(XmlExportService.class);
    
    @Autowired
    private BitsHeaderService bitsHeaderService;
    
    @Autowired
    private BitsLinesService bitsLinesService;
    
    /**
     * Export all purchase orders to XML format
     */
    public String exportAllPurchaseOrdersToXml() throws Exception {
        LOG.info("Starting XML export for all purchase orders");
        
        try {
            // Check if service is injected
            if (bitsHeaderService == null) {
                throw new Exception("BitsHeaderService is not injected properly");
            }
            
            // Get all headers using the correct method name
            List<BitsHeaderDto> headers = null;
            try {
                LOG.info("Calling bitsHeaderService.getAllHeaders()...");
                headers = bitsHeaderService.getAllHeaders();
                LOG.info("BitsHeaderService.getAllHeaders() returned: {}", 
                    headers == null ? "NULL" : "List with " + headers.size() + " items");
            } catch (Exception e) {
                LOG.error("Error calling bitsHeaderService.getAllHeaders()", e);
                
                // Check if it's a tenant_id issue
                if (e.getMessage().contains("tenant_id") || e.getMessage().contains("Bad value for type int")) {
                    throw new Exception("Database data integrity issue with tenant_id column. " +
                        "Please run the SQL script to fix tenant_id values. Error: " + e.getMessage(), e);
                }
                
                throw new Exception("Failed to get headers from service: " + e.getMessage(), e);
            }
            
            // Handle null response
            if (headers == null) {
                LOG.warn("BitsHeaderService returned null, creating empty list");
                headers = new ArrayList<>();
            }
            
            LOG.info("Found {} headers to export", headers.size());
            
            // If no data, create a sample XML structure
            if (headers.isEmpty()) {
                LOG.warn("No headers found in database, creating empty XML structure");
                return createEmptyXmlStructure();
            }
            
            // Convert to XML format
            XmlExportDto xmlExportDto = convertToXmlFormat(headers);
            
            // Generate XML string
            String xmlString = generateXmlString(xmlExportDto);
            
            LOG.info("Successfully generated XML for {} purchase orders", headers.size());
            return xmlString;
            
        } catch (Exception e) {
            LOG.error("Error exporting purchase orders to XML", e);
            throw new Exception("Failed to export to XML: " + e.getMessage(), e);
        }
    }
    
    /**
     * Create empty XML structure when no data is found
     */
    private String createEmptyXmlStructure() throws JAXBException {
        XmlExportDto emptyDto = new XmlExportDto(new ArrayList<>());
        return generateXmlString(emptyDto);
    }
    
    /**
     * Export specific purchase order by work order to XML format
     */
    public String exportPurchaseOrderByWorkOrderToXml(String workOrder) throws Exception {
        LOG.info("Starting XML export for work order: {}", workOrder);
        
        try {
            // Check if service is injected
            if (bitsHeaderService == null) {
                throw new Exception("BitsHeaderService is not injected properly");
            }
            
            // Get headers by work order using the correct method name
            List<BitsHeaderDto> headers = null;
            try {
                headers = bitsHeaderService.searchByWorkOrder(workOrder);
            } catch (Exception e) {
                LOG.error("Error calling bitsHeaderService.searchByWorkOrder({})", workOrder, e);
                
                // Check if it's a tenant_id issue
                if (e.getMessage().contains("tenant_id") || e.getMessage().contains("Bad value for type int")) {
                    throw new Exception("Database data integrity issue with tenant_id column. " +
                        "Please run the SQL script to fix tenant_id values. Error: " + e.getMessage(), e);
                }
                
                throw new Exception("Failed to get headers by work order: " + e.getMessage(), e);
            }
            
            if (headers == null) {
                headers = new ArrayList<>();
            }
            
            if (headers.isEmpty()) {
                throw new Exception("No purchase order found for work order: " + workOrder);
            }
            
            LOG.info("Found {} headers for work order: {}", headers.size(), workOrder);
            
            // Convert to XML format
            XmlExportDto xmlExportDto = convertToXmlFormat(headers);
            
            // Generate XML string
            String xmlString = generateXmlString(xmlExportDto);
            
            LOG.info("Successfully generated XML for work order: {}", workOrder);
            return xmlString;
            
        } catch (Exception e) {
            LOG.error("Error exporting work order {} to XML", workOrder, e);
            throw new Exception("Failed to export work order to XML: " + e.getMessage(), e);
        }
    }
    
    /**
     * Convert BitsHeaderDto list to XML format
     */
    private XmlExportDto convertToXmlFormat(List<BitsHeaderDto> headers) {
        List<PurchaseOrderXml> purchaseOrders = new ArrayList<>();
        
        for (BitsHeaderDto header : headers) {
            try {
                PurchaseOrderXml purchaseOrder = new PurchaseOrderXml();
                
                // Set header information - ONLY EXISTING FIELDS
                purchaseOrder.setOrderId(header.getOrderId());
                purchaseOrder.setWorkOrder(header.getWorkOrder());
                purchaseOrder.setPlantLocation(header.getPlantLocation());
                purchaseOrder.setDepartment(header.getDepartment());
                purchaseOrder.setWorkLocation(header.getWorkLocation());
                purchaseOrder.setWorkOrderDate(formatDate(header.getWorkOrderDate()));
                purchaseOrder.setCompletionDate(formatDate(header.getCompletionDate()));
                purchaseOrder.setLdApplicable(header.getLdApplicable());
                purchaseOrder.setScrapAllowanceVisiblePercent(header.getScrapAllowanceVisiblePercent());
                purchaseOrder.setScrapAllowanceInvisiblePercent(header.getScrapAllowanceInvisiblePercent());
                purchaseOrder.setMaterialIssueType(header.getMaterialIssueType());
                
                // Get and set lines information
                List<OrderLineXml> orderLines = new ArrayList<>();
                try {
                    if (bitsLinesService != null && header.getOrderId() != null) {
                        List<BitsLinesDto> lines = bitsLinesService.getLinesByOrderId(header.getOrderId());
                        if (lines != null) {
                            orderLines = convertLinesToXml(lines);
                            LOG.debug("Added {} lines for order ID: {}", orderLines.size(), header.getOrderId());
                        }
                    }
                } catch (Exception e) {
                    LOG.warn("Error getting lines for order ID: {}", header.getOrderId(), e);
                }
                
                purchaseOrder.setOrderLines(orderLines);
                purchaseOrders.add(purchaseOrder);
                
            } catch (Exception e) {
                LOG.error("Error processing header with order ID: {}", header.getOrderId(), e);
                // Continue with next header instead of failing completely
            }
        }
        
        return new XmlExportDto(purchaseOrders);
    }
    
    /**
     * Convert BitsLinesDto list to XML format
     */
    private List<OrderLineXml> convertLinesToXml(List<BitsLinesDto> lines) {
        List<OrderLineXml> orderLines = new ArrayList<>();
        
        for (BitsLinesDto line : lines) {
            try {
                OrderLineXml orderLine = new OrderLineXml();
                
                // Set line information - ONLY EXISTING FIELDS
                orderLine.setLineId(line.getLineId());
                orderLine.setOrderId(line.getOrderId());
                orderLine.setLineNumber(formatAmount(line.getLineNumber()));
                orderLine.setSerNo(line.getSerNo());
                orderLine.setServiceCode(line.getServiceCode());
                orderLine.setServiceDesc(line.getServiceDesc());
                orderLine.setQty(formatAmount(line.getQty()));
                orderLine.setUom(line.getUom());
                orderLine.setUnitPrice(formatAmount(line.getUnitPrice()));
                orderLine.setTotalPrice(formatAmount(line.getTotalPrice()));
                
                orderLines.add(orderLine);
                
            } catch (Exception e) {
                LOG.error("Error processing line with ID: {}", line.getLineId(), e);
                // Continue with next line instead of failing completely
            }
        }
        
        return orderLines;
    }
    
    /**
     * Generate XML string from XmlExportDto
     */
    private String generateXmlString(XmlExportDto xmlExportDto) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlExportDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        
        // Format the XML output
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(xmlExportDto, stringWriter);
        
        return stringWriter.toString();
    }
    
    /**
     * Format date for XML
     */
    private String formatDate(Object date) {
        if (date == null) return null;
        return date.toString();
    }
    
    /**
     * Format amount for XML
     */
    private String formatAmount(Object amount) {
        if (amount == null) return "0.00";
        return amount.toString();
    }
}
