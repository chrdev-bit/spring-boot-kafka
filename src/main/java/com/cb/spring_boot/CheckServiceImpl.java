package com.cb.spring_boot;

import org.springframework.stereotype.Service;


@Service
public class CheckServiceImpl implements CheckService {
    @Override
    public Check isValid(Check check) {
        check.setValid(check.getId().charAt(0)=='0');
        return check;
    }
}

