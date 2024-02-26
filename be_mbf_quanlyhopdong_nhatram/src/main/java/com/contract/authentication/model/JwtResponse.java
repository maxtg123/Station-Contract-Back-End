package com.contract.authentication.model;

import com.contract.nguoidung.nguoidung.model.ProfileModel;
import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	private final String token;
	private final ProfileModel profile;
}
