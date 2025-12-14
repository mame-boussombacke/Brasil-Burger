-- ============================================================
-- SCRIPT SQL COMPLET - Brasil Burger Database
-- Compatible PostgreSQL / MySQL / Neon
-- ============================================================

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS brasil_burger;
USE brasil_burger;

-- ============================================================
-- TABLE: utilisateur
-- ============================================================
CREATE TABLE utilisateur (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    motDePasse VARCHAR(255) NOT NULL,
    type ENUM('CLIENT', 'GESTIONNAIRE') NOT NULL DEFAULT 'CLIENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLE: client
-- ============================================================
CREATE TABLE client (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20) UNIQUE NOT NULL,
    adresse VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLE: zone (zones de livraison)
-- ============================================================
CREATE TABLE zone (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL UNIQUE,
    prixLivraison DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    statut ENUM('ACTIVE', 'INACTIVE', 'ARCHIVED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLE: burger
-- ============================================================
CREATE TABLE burger (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL UNIQUE,
    prix DECIMAL(10, 2) NOT NULL,
    imageUrl VARCHAR(500),
    statut ENUM('ACTIVE', 'INACTIVE', 'ARCHIVED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLE: complement
-- ============================================================
CREATE TABLE complement (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL UNIQUE,
    prix DECIMAL(10, 2) NOT NULL,
    imageUrl VARCHAR(500),
    type ENUM('SAUCE', 'BOISSON', 'DESSERT', 'FRITE') NOT NULL,
    statut ENUM('ACTIVE', 'INACTIVE', 'ARCHIVED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLE: menu
-- ============================================================
CREATE TABLE menu (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL UNIQUE,
    imageUrl VARCHAR(500),
    statut ENUM('ACTIVE', 'INACTIVE', 'ARCHIVED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLE: composition_menu (relation menu - burger/complement)
-- ============================================================
CREATE TABLE composition_menu (
    id INT PRIMARY KEY AUTO_INCREMENT,
    menuId INT NOT NULL,
    burgerId INT,
    complementId INT,
    type ENUM('burger', 'complement') NOT NULL,
    FOREIGN KEY (menuId) REFERENCES menu(id) ON DELETE CASCADE,
    FOREIGN KEY (burgerId) REFERENCES burger(id) ON DELETE SET NULL,
    FOREIGN KEY (complementId) REFERENCES complement(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLE: commande
-- ============================================================
CREATE TABLE commande (
    id INT PRIMARY KEY AUTO_INCREMENT,
    clientId INT NOT NULL,
    zoneId INT NOT NULL,
    dateCommande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    typeCommande VARCHAR(50),
    montantTotal DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    payee BOOLEAN DEFAULT FALSE,
    statut ENUM('EN_ATTENTE', 'CONFIRMEE', 'EN_PREPARATION', 'EN_LIVRAISON', 'LIVREE', 'ANNULEE') DEFAULT 'EN_ATTENTE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (clientId) REFERENCES client(id) ON DELETE RESTRICT,
    FOREIGN KEY (zoneId) REFERENCES zone(id) ON DELETE RESTRICT
);

-- ============================================================
-- TABLE: commande_burger (relation commande - burger)
-- ============================================================
CREATE TABLE commande_burger (
    id INT PRIMARY KEY AUTO_INCREMENT,
    commandeId INT NOT NULL,
    burgerId INT NOT NULL,
    quantite INT DEFAULT 1,
    FOREIGN KEY (commandeId) REFERENCES commande(id) ON DELETE CASCADE,
    FOREIGN KEY (burgerId) REFERENCES burger(id) ON DELETE RESTRICT
);

-- ============================================================
-- TABLE: commande_menu (relation commande - menu)
-- ============================================================
CREATE TABLE commande_menu (
    id INT PRIMARY KEY AUTO_INCREMENT,
    commandeId INT NOT NULL,
    menuId INT NOT NULL,
    quantite INT DEFAULT 1,
    FOREIGN KEY (commandeId) REFERENCES commande(id) ON DELETE CASCADE,
    FOREIGN KEY (menuId) REFERENCES menu(id) ON DELETE RESTRICT
);

-- ============================================================
-- DONNÉES DE TEST
-- ============================================================

-- Utilisateurs test
INSERT INTO utilisateur (nom, prenom, email, motDePasse, type) VALUES 
('Admin', 'Gestion', 'admin@burger.com', 'admin123', 'GESTIONNAIRE'),
('User', 'Test', 'user@burger.com', 'user123', 'CLIENT');

-- Clients test
INSERT INTO client (nom, prenom, telephone, adresse) VALUES 
('Dupont', 'Jean', '0612345678', '123 rue de la Paix, Paris'),
('Martin', 'Marie', '0698765432', '456 avenue des Champs, Lyon');

-- Zones de livraison
INSERT INTO zone (nom, prixLivraison, statut) VALUES 
('Zone Centre', 2.50, 'ACTIVE'),
('Zone Banlieue', 5.00, 'ACTIVE'),
('Zone Périphérie', 8.00, 'ACTIVE');

-- Burgers
INSERT INTO burger (nom, prix, imageUrl, statut) VALUES 
('Burger Classic', 8.99, 'https://example.com/burger-classic.jpg', 'ACTIVE'),
('Burger Deluxe', 12.99, 'https://example.com/burger-deluxe.jpg', 'ACTIVE'),
('Burger Végétarien', 9.99, 'https://example.com/burger-vege.jpg', 'ACTIVE');

-- Compléments
INSERT INTO complement (nom, prix, imageUrl, type, statut) VALUES 
('Frites Nature', 3.00, 'https://example.com/frites.jpg', 'FRITE', 'ACTIVE'),
('Sauce BBQ', 0.50, 'https://example.com/sauce-bbq.jpg', 'SAUCE', 'ACTIVE'),
('Coca Cola', 2.50, 'https://example.com/coca.jpg', 'BOISSON', 'ACTIVE'),
('Glace Vanille', 3.50, 'https://example.com/glace.jpg', 'DESSERT', 'ACTIVE');

-- Menus
INSERT INTO menu (nom, imageUrl, statut) VALUES 
('Menu Burger + Frites + Boisson', 'https://example.com/menu1.jpg', 'ACTIVE'),
('Menu Deluxe + Frites + Boisson + Dessert', 'https://example.com/menu2.jpg', 'ACTIVE');

-- Composition des menus
INSERT INTO composition_menu (menuId, burgerId, complementId, type) VALUES 
(1, 1, NULL, 'burger'),      -- Menu 1: Burger Classic
(1, NULL, 1, 'complement'),   -- Menu 1: Frites Nature
(1, NULL, 3, 'complement'),   -- Menu 1: Coca Cola
(2, 2, NULL, 'burger'),       -- Menu 2: Burger Deluxe
(2, NULL, 1, 'complement'),   -- Menu 2: Frites Nature
(2, NULL, 3, 'complement'),   -- Menu 2: Coca Cola
(2, NULL, 4, 'complement');   -- Menu 2: Glace Vanille

-- ============================================================
-- CRÉER DES INDEX POUR LES PERFORMANCES
-- ============================================================
CREATE INDEX idx_utilisateur_email ON utilisateur(email);
CREATE INDEX idx_client_telephone ON client(telephone);
CREATE INDEX idx_commande_client ON commande(clientId);
CREATE INDEX idx_commande_zone ON commande(zoneId);
CREATE INDEX idx_commande_burger ON commande_burger(commandeId);
CREATE INDEX idx_commande_menu ON commande_menu(commandeId);
CREATE INDEX idx_composition_menu ON composition_menu(menuId);

-- ============================================================
-- FIN DU SCRIPT
-- ============================================================
