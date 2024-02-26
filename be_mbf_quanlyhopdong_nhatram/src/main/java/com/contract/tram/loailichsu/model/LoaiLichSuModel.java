package com.contract.tram.loailichsu.model;

import com.contract.common.entity.AuditEntity;
import com.contract.tram.loailichsu.entity.LoaiLichSuEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
public class LoaiLichSuModel extends AuditEntity<String> {
    public static LoaiLichSuModel fromEntity(LoaiLichSuEntity entity) {
        LoaiLichSuModel model = new LoaiLichSuModel();

        try {
            model.setId(entity.getId());
            model.setLoai(entity.getLoai());
            model.setMa(entity.getMa());
            model.setType(entity.getType());
        } catch (Exception e) {
            System.out.println(e);
        }

        return model;
    }

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = 0L;

    @Column(name = "loai", nullable = false)
    protected String loai;

    @Column(name = "ma", nullable = false)
    protected String ma;

    @Column(name = "type", nullable = false)
    protected String type;
}
