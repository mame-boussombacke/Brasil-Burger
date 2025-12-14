package com.brasilburger.services;

import java.util.List;

import com.brasilburger.exceptions.BusinessException;
import com.brasilburger.exceptions.DataAccessException;
import com.brasilburger.model.entities.Zone;
import com.brasilburger.repositories.ZoneRepository;

public class ZoneService {

    private final ZoneRepository zoneRepository;

    // Constructeur par défaut
    public ZoneService() {
        this.zoneRepository = new ZoneRepository();
    }

    // Constructeur avec repository (optionnel)
    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public void createZone(Zone zone) throws BusinessException {
        try {
            zoneRepository.create(zone);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la création de la zone: " + e.getMessage());
        }
    }

    public Zone getZoneById(int id) throws BusinessException {
        try {
            return zoneRepository.findById(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération de la zone: " + e.getMessage());
        }
    }

    public List<Zone> getAllZones() throws BusinessException {
        try {
            return zoneRepository.findAll();
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la récupération des zones: " + e.getMessage());
        }
    }

    public void updateZone(Zone zone) throws BusinessException {
        try {
            zoneRepository.update(zone);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la mise à jour de la zone: " + e.getMessage());
        }
    }

    public void deleteZone(int id) throws BusinessException {
        try {
            zoneRepository.delete(id);
        } catch (DataAccessException e) {
            throw new BusinessException("Erreur lors de la suppression de la zone: " + e.getMessage());
        }
    }
}
