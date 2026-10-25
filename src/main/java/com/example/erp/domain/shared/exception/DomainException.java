package com.example.erp.domain.shared.exception;

/**
 * 领域异常基类，所有业务异常均继承自它。
 * Domain 层不依赖 Spring，故不直接使用 Spring 的异常体系。
 */
public abstract class DomainException extends RuntimeException {

    protected DomainException(String message) {
        super(message);
    }
}