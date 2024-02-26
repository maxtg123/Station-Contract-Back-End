# be_mbf_quanlyhopdong_nhatram

## Swagger:

- Swagger UI: {host}:{port}/swagger-ui/index.html
- Swagger docs: {host}:{port}/v3/api-docs

## Install

- Thiết lập môi trường

* Cài đặt biến môi trường cho maven nếu chưa có. Tham khảo tại: https://devopsify.co/cai-dat-maven-tren-windows/
* Kiểm tra maven được thiết lập bằng cách sử dụng Command Prompt -> Gõ "mvn --version"
* Sử dụng Command Prompt sau đó cd trỏ vào thư mục dự án hoặc vào thư mục dự án mở Command Prompt

## Lệnh chạy spring boot với một profile được chỉ định

```
mvn spring-boot:run -Dspring.profiles.active=prod
```

## Seed data

- Seed superadmin

```
mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=superadmin
```

- Seed danh mục

```
  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=doi_tuong_ky_hop_dong

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=hinh_thuc_dau_tu

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=hinh_thuc_ky_hop_dong

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=loai_cot_angten

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=loai_csht

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=loai_thiet_bi_ran

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=loai_tram

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=tram_khu_vuc

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=phong_dai

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=to

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=tinh

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=huyen

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=xa

  mvn spring-boot:run -Dspring-boot.run.arguments=--seeder.module=hinh_thuc_thanh_toan

```

NOTE: seed phòng đài thiêú tên viết tắt nên khi deploy xong vào giao diện để thêm tên viết tát cho phòng đài
