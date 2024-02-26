package com.contract.process.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contract.base.service.BaseService;
import com.contract.common.exception.InvalidDataException;
import com.contract.common.exception.NotFoundException;
import com.contract.process.entity.ProcessEntity;
import com.contract.process.model.ProcessModel;
import com.contract.process.repository.ProcessRepository;

@Service
public class ProcessService extends BaseService {
    @Autowired
    protected ProcessRepository processRepository;

    private String rootFolder = Paths.get("process_data").toFile().getAbsolutePath();

    public ProcessEntity findById(Long id) {
        ProcessEntity processEntity = processRepository.findById(id).orElse(null);
        if (processEntity != null) {
            // setListError(processEntity);
        }
        return processEntity;
    }

    public ProcessModel create(ProcessModel processModel) {
        ProcessEntity toSave = new ProcessEntity();
        toSave = convertModelToEntity(toSave, processModel);

        return save(toSave);
    }

    public void flush() {
        processRepository.flush();
    }

    public ProcessModel update(ProcessModel processModel) {
        if (processModel == null || processModel.getId() == null) {
            throw new InvalidDataException();
        }
        ProcessEntity toSave = findById(processModel.getId());
        if (ObjectUtils.isEmpty(toSave)) {
            throw new NotFoundException();
        }
        toSave = convertModelToEntity(toSave, processModel);

        return save(toSave);
    }

    public void delete(Long id) {
        ProcessEntity entity = findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        processRepository.delete(entity);
        deleteFileProcess(id);
    }

    private void setListError(ProcessEntity processEntity) {
        String fileName = processEntity.getId().toString();
        Path path = Paths.get(rootFolder, fileName);
        List<String> listError = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                listError.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        processEntity.setListError(listError);
    }

    private void deleteFileProcess(Long id) {
        try {
            if (ObjectUtils.isEmpty(id)) {
                return;
            }
            String fileFolder = rootFolder;

            String fileName = id.toString();
            Path path = Paths.get(fileFolder, fileName);

            path.toFile().delete();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private ProcessModel save(ProcessEntity entity) {
        try {
            ProcessEntity saved = processRepository.save(entity);
            return saved;
        } catch (Exception e) {
            System.out.println(e);
            throw new InvalidDataException();
        }
    }

    private ProcessEntity convertModelToEntity(ProcessEntity entity, ProcessModel model) {
        if (model.getUserId() != null) {
            entity.setUserId(model.getUserId());
        }
        if (model.getModule() != null) {
            entity.setModule(model.getModule());
        }
        if (model.getAction() != null) {
            entity.setAction(model.getAction());
        }
        if (model.getTongSo() != null) {
            entity.setTongSo(model.getTongSo());
        }
        if (model.getHoanThanh() != null) {
            entity.setHoanThanh(model.getHoanThanh());
        }
        if (model.getKetThuc() != null) {
            entity.setKetThuc(model.getKetThuc());
        }
        if (model.getLoi() != null) {
            entity.setLoi(model.getLoi());
        }
        if (model.getSoLuongLoi() != null) {
            entity.setSoLuongLoi(model.getSoLuongLoi());
        }

        return entity;
    }
}
