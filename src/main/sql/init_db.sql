DROP TABLE IF EXISTS Products CASCADE;
DROP TABLE IF EXISTS ProductCategories CASCADE;
DROP TABLE IF EXISTS Suppliers CASCADE;


CREATE TABLE Products(
    id          SERIAL PRIMARY KEY  NOT NULL,
    name        VARCHAR(100)        NOT NULL,
    price       DECIMAL             NOT NULL,
    currency    VARCHAR(5)          NOT NULL,
    description VARCHAR(200)        NOT NULL,
    categoryID  INTEGER             NOT NULL,
    supplierID  INTEGER             NOT NULL,
    imageRoute  VARCHAR(100)        NOT NULL
);

CREATE TABLE ProductCategories(
    id          SERIAL PRIMARY KEY  NOT NULL,
    name        VARCHAR(50)         NOT NULL,
    department  VARCHAR(50)         NOT NULL,
    description VARCHAR(200)        NOT NULL
);

CREATE TABLE Suppliers(
    id          SERIAL PRIMARY KEY  NOT NULL,
    name        VARCHAR(50)         NOT NULL,
    description VARCHAR(200)        NOT NULL
);

ALTER TABLE ONLY Products
    ADD CONSTRAINT fkProductsCategoryID FOREIGN KEY (categoryID) REFERENCES ProductCategories(id);

ALTER TABLE ONLY Products
    ADD CONSTRAINT fkProductsSupplierID FOREIGN KEY (supplierID) REFERENCES Suppliers(id);
