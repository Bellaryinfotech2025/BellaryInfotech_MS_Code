package com.bellaryinfotech.serviceimpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bellaryinfotech.DTO.InchMeterModuleDTO;
import com.bellaryinfotech.model.InchMeterModule;
import com.bellaryinfotech.repo.InchMeterModuleRepository;
import com.bellaryinfotech.service.InchMeterModuleService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InchMeterModuleServiceImpl implements InchMeterModuleService {

    @Autowired
    private InchMeterModuleRepository inchMeterModuleRepository;

    @Override
    public InchMeterModuleDTO createInchMeterModule(InchMeterModuleDTO inchMeterModuleDTO) {
        InchMeterModule inchMeterModule = new InchMeterModule();
        BeanUtils.copyProperties(inchMeterModuleDTO, inchMeterModule);
        InchMeterModule savedModule = inchMeterModuleRepository.save(inchMeterModule);
        InchMeterModuleDTO result = new InchMeterModuleDTO();
        BeanUtils.copyProperties(savedModule, result);
        return result;
    }

    @Override
    public InchMeterModuleDTO updateInchMeterModule(Long id, InchMeterModuleDTO inchMeterModuleDTO) {
        Optional<InchMeterModule> existingModule = inchMeterModuleRepository.findById(id);
        if (existingModule.isPresent()) {
            InchMeterModule inchMeterModule = existingModule.get();
            BeanUtils.copyProperties(inchMeterModuleDTO, inchMeterModule, "id");
            InchMeterModule updatedModule = inchMeterModuleRepository.save(inchMeterModule);
            InchMeterModuleDTO result = new InchMeterModuleDTO();
            BeanUtils.copyProperties(updatedModule, result);
            return result;
        }
        throw new RuntimeException("InchMeterModule not found with id: " + id);
    }

    @Override
    public void deleteInchMeterModule(Long id) {
        if (inchMeterModuleRepository.existsById(id)) {
            inchMeterModuleRepository.deleteById(id);
        } else {
            throw new RuntimeException("InchMeterModule not found with id: " + id);
        }
    }

    @Override
    public InchMeterModuleDTO getInchMeterModuleById(Long id) {
        Optional<InchMeterModule> inchMeterModule = inchMeterModuleRepository.findById(id);
        if (inchMeterModule.isPresent()) {
            InchMeterModuleDTO result = new InchMeterModuleDTO();
            BeanUtils.copyProperties(inchMeterModule.get(), result);
            return result;
        }
        throw new RuntimeException("InchMeterModule not found with id: " + id);
    }

    @Override
    public List<InchMeterModuleDTO> getAllInchMeterModules() {
        List<InchMeterModule> modules = inchMeterModuleRepository.findAll();
        return modules.stream()
                .map(module -> {
                    InchMeterModuleDTO dto = new InchMeterModuleDTO();
                    BeanUtils.copyProperties(module, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<InchMeterModuleDTO> getInchMeterModulesByMarkNo(String markNo) {
        List<InchMeterModule> modules = inchMeterModuleRepository.findByMarkNo(markNo);
        return modules.stream()
                .map(module -> {
                    InchMeterModuleDTO dto = new InchMeterModuleDTO();
                    BeanUtils.copyProperties(module, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<InchMeterModuleDTO> getInchMeterModulesByWorkOrder(String workOrder) {
        List<InchMeterModule> modules = inchMeterModuleRepository.findByWorkOrder(workOrder);
        return modules.stream()
                .map(module -> {
                    InchMeterModuleDTO dto = new InchMeterModuleDTO();
                    BeanUtils.copyProperties(module, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<InchMeterModuleDTO> getInchMeterModulesByWorkOrderAndBuildingName(String workOrder, String buildingName) {
        List<InchMeterModule> modules = inchMeterModuleRepository.findByWorkOrderAndBuildingName(workOrder, buildingName);
        return modules.stream()
                .map(module -> {
                    InchMeterModuleDTO dto = new InchMeterModuleDTO();
                    BeanUtils.copyProperties(module, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInchMeterModulesByMarkNo(String markNo) {
        inchMeterModuleRepository.deleteByMarkNo(markNo);
    }

    // Implementation of required methods for FabWrapper integration
    @Override
    public List<String> getDistinctWorkOrders() {
        return inchMeterModuleRepository.findDistinctWorkOrders();
    }

    @Override
    public List<String> getDistinctBuildingNamesByWorkOrder(String workOrder) {
        return inchMeterModuleRepository.findDistinctBuildingNamesByWorkOrder(workOrder);
    }

    @Override
    public List<String> getDistinctDrawingNosByWorkOrderAndBuildingName(String workOrder, String buildingName) {
        return inchMeterModuleRepository.findDistinctDrawingNosByWorkOrderAndBuildingName(workOrder, buildingName);
    }

    @Override
    public List<String> getDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(String workOrder, String buildingName, String drawingNo) {
        return inchMeterModuleRepository.findDistinctMarkNosByWorkOrderAndBuildingNameAndDrawingNo(workOrder, buildingName, drawingNo);
    }

    @Override
    public List<String> getDistinctServiceDescriptionsByWorkOrder(String workOrder) {
        return inchMeterModuleRepository.findDistinctServiceDescriptionsByWorkOrder(workOrder);
    }

    @Override
    public List<InchMeterModuleDTO> getInchMeterModulesByWorkOrderBuildingDrawingMark(String workOrder, String buildingName, String drawingNo, String markNo) {
        List<InchMeterModule> modules = inchMeterModuleRepository.findByWorkOrderAndBuildingNameAndDrawingNoAndMarkNo(workOrder, buildingName, drawingNo, markNo);
        return modules.stream()
                .map(module -> {
                    InchMeterModuleDTO dto = new InchMeterModuleDTO();
                    BeanUtils.copyProperties(module, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
