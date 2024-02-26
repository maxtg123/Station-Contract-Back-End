package com.contract.hopdong.hopdong.model;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class TramImportDto {
  private Long id;
  private Long phongDaiId;
  private Long toId;
  private String maTram;
  private String maTramErp;
  private String ten;
  private Long tinhId;
  private Long huyenId;
  private Long xaId;
  private String diaChi;
  private String kinhDo;
  private String viDo;
  private Long khuVucId;
  private Date ngayPhatSong;
  private Long loaiCshtId;
  private Long loaiTramId;
  private Long loaiCotAngtenId;
  private Long doCaoAngten;
}
