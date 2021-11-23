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

INSERT INTO Suppliers(name, description) VALUES ('Bigfoot', 'Everything we have on Bigfoot');
INSERT INTO Suppliers(name, description) VALUES ('Nessie', 'Everything we have on the Loch Ness monster');
INSERT INTO Suppliers(name, description) VALUES ('Mothman', 'Everything we have on the Mothman');
INSERT INTO Suppliers(name, description) VALUES ('Alien', 'Everything we have on our extraterrestrial friends');


INSERT INTO ProductCategories(name, department, description) VALUES ('Miscellaneous', 'Household items', 'Any product that wouldnt fit other categories');
INSERT INTO ProductCategories(name, department, description) VALUES ('T-shirt', 'Clothing', 'Funny T-shirts with our favourite real-life monsters on them');
INSERT INTO ProductCategories(name, department, description) VALUES ('Mug', 'Household items', 'Mugs with funny labels');


INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Nessie NASA logo tee', 24.99, 'USD', 'Nessie goes to the moon', 2, 2, 'nessie_nasa_logo.png');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Bigfoot I dont believe in humans tee', 24.99, 'USD', 'Bigfoot doesnt believe in humans', 2, 1, 'bigfoot_dont_believe_in_humans.jpg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Bigfoot saw me tee', 24.99 , 'USD', 'Bigfoot is always looking!', 2, 1, 'bigfoot_saw_me.jpeg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Notorious B.I.G foot tee', 24.99, 'USD', '', 2, 1, 'bigfoot_notorious_big.jpg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Bigfoot abducted tee', 24.99, 'USD', 'What is this, a crossover episode?', 2, 1, 'bigfoot_ufo.jpg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Bigfoot hanging with his buddies', 24.99,'USD', 'Another crossover episode??', 2, 1, 'bigfoot_ufo_nessie.jpeg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Bigfoot hide & seek champion tee', 24.99, 'USD', 'He do be good', 2, 1, 'bigfoot_hidenseek_champion.jpeg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Bigfoot social distancing world champion tee', 24.99, 'USD', 'I wonder if hes vaccinated', 2, 1, 'bigfoot_social_distancing_champion.jpg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Bigfoot later haters mug', 14.99, 'USD', 'Bigfoot has had enough of yall', 3, 1, 'bigfoot_later_haters.jpg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Bigfoot face mug', 16.99, 'USD', 'Idk its kinda cursed ngl', 3, 1, 'bigfoot_face_mug.jpg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('The Man, the Moth, the Legend tee', 24.99, 'USD', '', 2, 3, 'mothman_man_moth_legend.jpeg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Alien get in loser tee', 24.99, 'USD', 'Get in loser, were going shopping', 2, 4, 'alien_get_in_loser.jpeg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Area 51 raid tee', 24.99, 'USD', 'Remember this meme?', 2, 4, 'alien_area_51_raid.jpg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Alien head mug', 16.99, 'USD', 'A bit less cursed than the bigfoot one', 3, 4, 'alien_head.jpeg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('UFO yeet mug', 14.99, 'USD', 'YEET!', 3, 4, 'alien_yeet.jpg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Go camping they said mug', 14.99, 'USD', 'Wrong place, wrong time', 3, 4, 'alien_go_camping.jpg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Loch Ness monster pasta spoon', 16.99, 'USD', 'A followup in the Nessie line of kitchen utensils, a nessie shaped pasta spoon.', 1, 2, 'nessie_pasta_spoon.jpg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Loch Ness monster ladle', 14.99, 'USD', 'A soup ladle made in the shape of our beloved Nessie.', 1, 2, 'nessie_ladle.jpg');
INSERT INTO Products(name, price, currency, description, categoryID, supplierID, imageRoute)  VALUES
    ('Believe in yourself tee', 24.99, 'USD', 'Nessie believes in you!', 2, 2, 'nessie_believe_in_yourself.png');


ALTER TABLE ONLY Products
    ADD CONSTRAINT fkProductsCategoryID FOREIGN KEY (categoryID) REFERENCES ProductCategories(id);

ALTER TABLE ONLY Products
    ADD CONSTRAINT fkProductsSupplierID FOREIGN KEY (supplierID) REFERENCES Suppliers(id);
