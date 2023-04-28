package com.jrtp.officeportal.binding;

import lombok.Data;

@Data
public class ResetForm {
	private String email;
    private String code;
    private String newPwd;
    private String confirmPwd;
}
