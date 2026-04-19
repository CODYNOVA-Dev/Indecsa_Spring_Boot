package com.indecsa.service;

import com.indecsa.dto.auth.LoginRequest;
import com.indecsa.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
