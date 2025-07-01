package com.bellaryinfotech.DAO;

import com.bellaryinfotech.model.BitsDrawingEntry;
import com.bellaryinfotech.repo.BitsDrawingEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class BitsDrawingEntryDaoImpl implements BitsDrawingEntryDao {

    private static final Logger logger = LoggerFactory.getLogger(BitsDrawingEntryDaoImpl.class);

    @Autowired
    private BitsDrawingEntryRepository bitsDrawingEntryRepository;

    @Override
    public BitsDrawingEntry save(BitsDrawingEntry bitsDrawingEntry) {
        try {
            logger.info("Saving drawing entry with line ID: {}", bitsDrawingEntry.getLineId());
            BitsDrawingEntry savedEntry = bitsDrawingEntryRepository.save(bitsDrawingEntry);
            logger.info("Successfully saved drawing entry with line ID: {}", savedEntry.getLineId());
            return savedEntry;
        } catch (Exception e) {
            logger.error("Error saving drawing entry with line ID: {}", bitsDrawingEntry.getLineId(), e);
            throw new RuntimeException("Failed to save drawing entry", e);
        }
    }

    @Override
    public List<BitsDrawingEntry> saveAll(List<BitsDrawingEntry> bitsDrawingEntries) {
        try {
            logger.info("Saving {} drawing entries", bitsDrawingEntries.size());
            List<BitsDrawingEntry> savedEntries = bitsDrawingEntryRepository.saveAll(bitsDrawingEntries);
            logger.info("Successfully saved {} drawing entries", savedEntries.size());
            return savedEntries;
        } catch (Exception e) {
            logger.error("Error saving {} drawing entries", bitsDrawingEntries.size(), e);
            throw new RuntimeException("Failed to save drawing entries", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BitsDrawingEntry> findById(Long lineId) { // Changed from String to Long
        try {
            logger.debug("Finding drawing entry by line ID: {}", lineId);
            Optional<BitsDrawingEntry> entry = bitsDrawingEntryRepository.findById(lineId);
            if (entry.isPresent()) {
                logger.debug("Found drawing entry with line ID: {}", lineId);
            } else {
                logger.debug("No drawing entry found with line ID: {}", lineId);
            }
            return entry;
        } catch (Exception e) {
            logger.error("Error finding drawing entry by line ID: {}", lineId, e);
            throw new RuntimeException("Failed to find drawing entry", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BitsDrawingEntry> findAll(Pageable pageable) {
        try {
            logger.debug("Finding all drawing entries with pagination");
            Page<BitsDrawingEntry> entries = bitsDrawingEntryRepository.findAll(pageable);
            logger.debug("Found {} drawing entries", entries.getTotalElements());
            return entries;
        } catch (Exception e) {
            logger.error("Error finding all drawing entries with pagination", e);
            throw new RuntimeException("Failed to find drawing entries", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitsDrawingEntry> findAll() {
        try {
            logger.debug("Finding all drawing entries");
            List<BitsDrawingEntry> entries = bitsDrawingEntryRepository.findAll();
            logger.debug("Found {} drawing entries", entries.size());
            return entries;
        } catch (Exception e) {
            logger.error("Error finding all drawing entries", e);
            throw new RuntimeException("Failed to find drawing entries", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitsDrawingEntry> findByDrawingNo(String drawingNo) {
        try {
            logger.debug("Finding drawing entries by drawing number: {}", drawingNo);
            List<BitsDrawingEntry> entries = bitsDrawingEntryRepository.findByDrawingNoOrderByCreationDateDesc(drawingNo);
            logger.debug("Found {} drawing entries for drawing number: {}", entries.size(), drawingNo);
            return entries;
        } catch (Exception e) {
            logger.error("Error finding drawing entries by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to find drawing entries by drawing number", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitsDrawingEntry> findByMarkNo(String markNo) {
        try {
            logger.debug("Finding drawing entries by mark number: {}", markNo);
            List<BitsDrawingEntry> entries = bitsDrawingEntryRepository.findByMarkNoOrderByCreationDateDesc(markNo);
            logger.debug("Found {} drawing entries for mark number: {}", entries.size(), markNo);
            return entries;
        } catch (Exception e) {
            logger.error("Error finding drawing entries by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to find drawing entries by mark number", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitsDrawingEntry> findBySessionCode(String sessionCode) {
        try {
            logger.debug("Finding drawing entries by session code: {}", sessionCode);
            List<BitsDrawingEntry> entries = bitsDrawingEntryRepository.findBySessionCodeOrderByCreationDateDesc(sessionCode);
            logger.debug("Found {} drawing entries for session code: {}", entries.size(), sessionCode);
            return entries;
        } catch (Exception e) {
            logger.error("Error finding drawing entries by session code: {}", sessionCode, e);
            throw new RuntimeException("Failed to find drawing entries by session code", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BitsDrawingEntry> findByTenantId(String tenantId, Pageable pageable) {
        try {
            logger.debug("Finding drawing entries by tenant ID: {}", tenantId);
            Page<BitsDrawingEntry> entries = bitsDrawingEntryRepository.findByTenantIdOrderByCreationDateDesc(tenantId, pageable);
            logger.debug("Found {} drawing entries for tenant ID: {}", entries.getTotalElements(), tenantId);
            return entries;
        } catch (Exception e) {
            logger.error("Error finding drawing entries by tenant ID: {}", tenantId, e);
            throw new RuntimeException("Failed to find drawing entries by tenant ID", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BitsDrawingEntry> findByMultipleCriteria(String drawingNo, String markNo, 
                                                         String sessionCode, String tenantId, 
                                                         Pageable pageable) {
        try {
            logger.debug("Finding drawing entries by multiple criteria");
            Page<BitsDrawingEntry> entries = bitsDrawingEntryRepository.findByMultipleCriteria(
                    drawingNo, markNo, sessionCode, tenantId, null, pageable);
            logger.debug("Found {} drawing entries for multiple criteria", entries.getTotalElements());
            return entries;
        } catch (Exception e) {
            logger.error("Error finding drawing entries by multiple criteria", e);
            throw new RuntimeException("Failed to find drawing entries by multiple criteria", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitsDrawingEntry> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            logger.debug("Finding drawing entries between dates: {} and {}", startDate, endDate);
            List<BitsDrawingEntry> entries = bitsDrawingEntryRepository.findByCreationDateBetweenOrderByCreationDateDesc(startDate, endDate);
            logger.debug("Found {} drawing entries between dates", entries.size());
            return entries;
        } catch (Exception e) {
            logger.error("Error finding drawing entries between dates", e);
            throw new RuntimeException("Failed to find drawing entries between dates", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitsDrawingEntry> findByMarkedQtyGreaterThan(BigDecimal markedQty) {
        try {
            logger.debug("Finding drawing entries with marked quantity greater than: {}", markedQty);
            List<BitsDrawingEntry> entries = bitsDrawingEntryRepository.findByMarkedQtyGreaterThanOrderByMarkedQtyDesc(markedQty);
            logger.debug("Found {} drawing entries with marked quantity greater than: {}", entries.size(), markedQty);
            return entries;
        } catch (Exception e) {
            logger.error("Error finding drawing entries with marked quantity greater than: {}", markedQty, e);
            throw new RuntimeException("Failed to find drawing entries by marked quantity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByDrawingNo(String drawingNo) {
        try {
            logger.debug("Counting drawing entries by drawing number: {}", drawingNo);
            Long count = bitsDrawingEntryRepository.countByDrawingNo(drawingNo);
            logger.debug("Count of drawing entries for drawing number {}: {}", drawingNo, count);
            return count;
        } catch (Exception e) {
            logger.error("Error counting drawing entries by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to count drawing entries", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal sumMarkedQtyByDrawingNo(String drawingNo) {
        try {
            logger.debug("Summing marked quantities by drawing number: {}", drawingNo);
            BigDecimal sum = bitsDrawingEntryRepository.sumMarkedQtyByDrawingNo(drawingNo);
            logger.debug("Sum of marked quantities for drawing number {}: {}", drawingNo, sum);
            return sum != null ? sum : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.error("Error summing marked quantities by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to sum marked quantities", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal sumTotalMarkedWgtByDrawingNo(String drawingNo) {
        try {
            logger.debug("Summing total marked weights by drawing number: {}", drawingNo);
            BigDecimal sum = bitsDrawingEntryRepository.sumTotalMarkedWgtByDrawingNo(drawingNo);
            logger.debug("Sum of total marked weights for drawing number {}: {}", drawingNo, sum);
            return sum != null ? sum : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.error("Error summing total marked weights by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to sum total marked weights", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findDistinctDrawingNumbers() {
        try {
            logger.debug("Finding distinct drawing numbers");
            List<String> drawingNumbers = bitsDrawingEntryRepository.findDistinctDrawingNumbers();
            logger.debug("Found {} distinct drawing numbers", drawingNumbers.size());
            return drawingNumbers;
        } catch (Exception e) {
            logger.error("Error finding distinct drawing numbers", e);
            throw new RuntimeException("Failed to find distinct drawing numbers", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findDistinctMarkNumbers() {
        try {
            logger.debug("Finding distinct mark numbers");
            List<String> markNumbers = bitsDrawingEntryRepository.findDistinctMarkNumbers();
            logger.debug("Found {} distinct mark numbers", markNumbers.size());
            return markNumbers;
        } catch (Exception e) {
            logger.error("Error finding distinct mark numbers", e);
            throw new RuntimeException("Failed to find distinct mark numbers", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findDistinctSessionCodes() {
        try {
            logger.debug("Finding distinct session codes");
            List<String> sessionCodes = bitsDrawingEntryRepository.findDistinctSessionCodes();
            logger.debug("Found {} distinct session codes", sessionCodes.size());
            return sessionCodes;
        } catch (Exception e) {
            logger.error("Error finding distinct session codes", e);
            throw new RuntimeException("Failed to find distinct session codes", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long lineId) { // Changed from String to Long
        try {
            logger.debug("Checking if drawing entry exists by line ID: {}", lineId);
            boolean exists = bitsDrawingEntryRepository.existsById(lineId);
            logger.debug("Drawing entry exists for line ID {}: {}", lineId, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Error checking if drawing entry exists by line ID: {}", lineId, e);
            throw new RuntimeException("Failed to check if drawing entry exists", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByDrawingNoAndMarkNo(String drawingNo, String markNo) {
        try {
            logger.debug("Checking if drawing entry exists by drawing number: {} and mark number: {}", drawingNo, markNo);
            boolean exists = bitsDrawingEntryRepository.existsByDrawingNoAndMarkNo(drawingNo, markNo);
            logger.debug("Drawing entry exists for drawing number {} and mark number {}: {}", drawingNo, markNo, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Error checking if drawing entry exists by drawing number: {} and mark number: {}", drawingNo, markNo, e);
            throw new RuntimeException("Failed to check if drawing entry exists", e);
        }
    }

    @Override
    public BitsDrawingEntry update(BitsDrawingEntry bitsDrawingEntry) {
        try {
            logger.info("Updating drawing entry with line ID: {}", bitsDrawingEntry.getLineId());
            BitsDrawingEntry updatedEntry = bitsDrawingEntryRepository.save(bitsDrawingEntry);
            logger.info("Successfully updated drawing entry with line ID: {}", updatedEntry.getLineId());
            return updatedEntry;
        } catch (Exception e) {
            logger.error("Error updating drawing entry with line ID: {}", bitsDrawingEntry.getLineId(), e);
            throw new RuntimeException("Failed to update drawing entry", e);
        }
    }

    @Override
    public void deleteById(Long lineId) { // Changed from String to Long
        try {
            logger.info("Deleting drawing entry by line ID: {}", lineId);
            bitsDrawingEntryRepository.deleteById(lineId);
            logger.info("Successfully deleted drawing entry with line ID: {}", lineId);
        } catch (Exception e) {
            logger.error("Error deleting drawing entry by line ID: {}", lineId, e);
            throw new RuntimeException("Failed to delete drawing entry", e);
        }
    }

    @Override
    public void delete(BitsDrawingEntry bitsDrawingEntry) {
        try {
            logger.info("Deleting drawing entry with line ID: {}", bitsDrawingEntry.getLineId());
            bitsDrawingEntryRepository.delete(bitsDrawingEntry);
            logger.info("Successfully deleted drawing entry with line ID: {}", bitsDrawingEntry.getLineId());
        } catch (Exception e) {
            logger.error("Error deleting drawing entry with line ID: {}", bitsDrawingEntry.getLineId(), e);
            throw new RuntimeException("Failed to delete drawing entry", e);
        }
    }

    @Override
    public void deleteByDrawingNo(String drawingNo) {
        try {
            logger.info("Deleting all drawing entries by drawing number: {}", drawingNo);
            bitsDrawingEntryRepository.deleteByDrawingNo(drawingNo);
            logger.info("Successfully deleted all drawing entries for drawing number: {}", drawingNo);
        } catch (Exception e) {
            logger.error("Error deleting drawing entries by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to delete drawing entries by drawing number", e);
        }
    }

    @Override
    public void deleteByMarkNo(String markNo) {
        try {
            logger.info("Deleting all drawing entries by mark number: {}", markNo);
            bitsDrawingEntryRepository.deleteByMarkNo(markNo);
            logger.info("Successfully deleted all drawing entries for mark number: {}", markNo);
        } catch (Exception e) {
            logger.error("Error deleting drawing entries by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to delete drawing entries by mark number", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        try {
            logger.debug("Counting total drawing entries");
            long count = bitsDrawingEntryRepository.count();
            logger.debug("Total count of drawing entries: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Error counting total drawing entries", e);
            throw new RuntimeException("Failed to count drawing entries", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BitsDrawingEntry> findLatestByDrawingNo(String drawingNo) {
        try {
            logger.debug("Finding latest drawing entry by drawing number: {}", drawingNo);
            Optional<BitsDrawingEntry> entry = bitsDrawingEntryRepository.findTopByDrawingNoOrderByCreationDateDesc(drawingNo);
            if (entry.isPresent()) {
                logger.debug("Found latest drawing entry for drawing number: {}", drawingNo);
            } else {
                logger.debug("No drawing entry found for drawing number: {}", drawingNo);
            }
            return entry;
        } catch (Exception e) {
            logger.error("Error finding latest drawing entry by drawing number: {}", drawingNo, e);
            throw new RuntimeException("Failed to find latest drawing entry", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BitsDrawingEntry> findLatestByMarkNo(String markNo) {
        try {
            logger.debug("Finding latest drawing entry by mark number: {}", markNo);
            Optional<BitsDrawingEntry> entry = bitsDrawingEntryRepository.findTopByMarkNoOrderByCreationDateDesc(markNo);
            if (entry.isPresent()) {
                logger.debug("Found latest drawing entry for mark number: {}", markNo);
            } else {
                logger.debug("No drawing entry found for mark number: {}", markNo);
            }
            return entry;
        } catch (Exception e) {
            logger.error("Error finding latest drawing entry by mark number: {}", markNo, e);
            throw new RuntimeException("Failed to find latest drawing entry", e);
        }
    }
    
    
}
