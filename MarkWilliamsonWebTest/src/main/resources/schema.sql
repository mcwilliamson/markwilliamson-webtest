CREATE TABLE PROD_PACKAGES (
  id			VARCHAR(30) PRIMARY KEY,
  name			VARCHAR(30) NOT NULL,
  description 	VARCHAR(255)
);

CREATE TABLE PROD_PKG_PRODUCT_LNK (
  product_package_id	VARCHAR(30) NOT NULL,
  product_id			VARCHAR(30) NOT NULL,
  primary key (product_package_id, product_id),
  foreign key (product_package_id) references PROD_PACKAGES(id)
);

