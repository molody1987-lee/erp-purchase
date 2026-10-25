-- ERP 采购系统数据库表结构（参考 DDL，实际建表请按需执行）
CREATE TABLE IF NOT EXISTS t_purchase_order (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no      VARCHAR(64)  NOT NULL,
    supplier_name VARCHAR(128) NOT NULL,
    status        VARCHAR(32)  NOT NULL,
    created_at    DATETIME,
    updated_at    DATETIME,
    version       INT DEFAULT 0,
    deleted       TINYINT DEFAULT 0,
    UNIQUE KEY uk_order_no (order_no)
);

CREATE TABLE IF NOT EXISTS t_purchase_order_item (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id     BIGINT NOT NULL,
    product_id   BIGINT,
    product_name VARCHAR(128),
    quantity     INT,
    unit_price   DECIMAL(18, 2),
    KEY idx_order_id (order_id)
);