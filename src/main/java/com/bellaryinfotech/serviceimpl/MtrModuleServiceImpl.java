package com.bellaryinfotech.serviceimpl;
 
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bellaryinfotech.DTO.MtrModuleDTO;
import com.bellaryinfotech.model.MtrModule;
import com.bellaryinfotech.repo.MtrModuleRepository;
import com.bellaryinfotech.service.MtrModuleService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MtrModuleServiceImpl implements MtrModuleService {

    @Autowired
    private MtrModuleRepository mtrModuleRepository;

    @Override
    public MtrModuleDTO createMeterModule(MtrModuleDTO mtrModuleDTO) {
        MtrModule mtrModule = new MtrModule();
        BeanUtils.copyProperties(mtrModuleDTO, mtrModule);
        MtrModule savedModule = mtrModuleRepository.save(mtrModule);
        MtrModuleDTO result = new MtrModuleDTO();
        BeanUtils.copyProperties(savedModule, result);
        return result;
    }

    @Override
    public MtrModuleDTO updateMeterModule(Long id, MtrModuleDTO mtrModuleDTO) {
        Optional<MtrModule> existingModule = mtrModuleRepository.findById(id);
        if (existingModule.isPresent()) {
            MtrModule mtrModule = existingModule.get();
            BeanUtils.copyProperties(mtrModuleDTO, mtrModule, "id");
            MtrModule updatedModule = mtrModuleRepository.save(mtrModule);
            MtrModuleDTO result = new MtrModuleDTO();
            BeanUtils.copyProperties(updatedModule, result);
            return result;
        }
        throw new RuntimeException("MtrModule not found with id: " + id);
    }

    @Override
    public void deleteMeterModule(Long id) {
        if (mtrModuleRepository.existsById(id)) {
            mtrModuleRepository.deleteById(id);
        } else {
            throw new RuntimeException("MtrModule not found with id: " + id);
        }
    }

    @Override
    public MtrModuleDTO getMeterModuleById(Long id) {
        Optional<MtrModule> mtrModule = mtrModuleRepository.findById(id);
        if (mtrModule.isPresent()) {
            MtrModuleDTO result = new MtrModuleDTO();
            BeanUtils.copyProperties(mtrModule.get(), result);
            return result;
        }
        throw new RuntimeException("MtrModule not found with id: " + id);
    }

    @Override
    public List<MtrModuleDTO> getAllMeterModules() {
        List<MtrModule> modules = mtrModuleRepository.findAll();
        return modules.stream()
                .map(module -> {
                    MtrModuleDTO dto = new MtrModuleDTO();
                    BeanUtils.copyProperties(module, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MtrModuleDTO> getMeterModulesByMarkNo(String markNo) {
        List<MtrModule> modules = mtrModuleRepository.findByMarkNo(markNo);
        return modules.stream()
                .map(module -> {
                    MtrModuleDTO dto = new MtrModuleDTO();
                    BeanUtils.copyProperties(module, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MtrModuleDTO> getMeterModulesByWorkOrder(String workOrder) {
        List<MtrModule> modules = mtrModuleRepository.findByWorkOrder(workOrder);
        return modules.stream()
                .map(module -> {
                    MtrModuleDTO dto = new MtrModuleDTO();
                    BeanUtils.copyProperties(module, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MtrModuleDTO> getMeterModulesByWorkOrderAndBuildingName(String workOrder, String buildingName) {
        List<MtrModule> modules = mtrModuleRepository.findByWorkOrderAndBuildingName(workOrder, buildingName);
        return modules.stream()
                .map(module -> {
                    MtrModuleDTO dto = new MtrModuleDTO();
                    BeanUtils.copyProperties(module, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getDistinctWorkOrders() {
        return mtrModuleRepository.findDistinctWorkOrders();
    }

    @Override
    public List<String> getDistinctBuildingNamesByWorkOrder(String workOrder) {
        return mtrModuleRepository.findDistinctBuildingNamesByWorkOrder(workOrder);
    }

    @Override
    public void deleteMeterModulesByMarkNo(String markNo) {
        mtrModuleRepository.deleteByMarkNo(markNo);
    }
    
    
    
}
