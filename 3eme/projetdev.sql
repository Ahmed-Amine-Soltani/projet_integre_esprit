-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  sam. 25 mai 2019 à 15:48
-- Version du serveur :  5.7.21
-- Version de PHP :  7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `projetdev`
--

-- --------------------------------------------------------

--
-- Structure de la table `adresse`
--

DROP TABLE IF EXISTS `adresse`;
CREATE TABLE IF NOT EXISTS `adresse` (
  `id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `pays` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `adresse` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ville` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `etat` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `adresse`
--

INSERT INTO `adresse` (`id`, `user_id`, `pays`, `adresse`, `ville`, `etat`) VALUES
(4, 3, 'Tunisie', 'gzre', 'zteze', 'egze'),
(5, 1, 'Antarctica', 'Rue habib bourguiba', 'el alia', 'bizerte'),
(NULL, 74, 'Djibouti', 'zdadaz', 'dazdaz', 'dzadazd'),
(NULL, 86, 'Åland Islands', 'jik', 'ijp', 'ijp');

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
CREATE TABLE IF NOT EXISTS `categorie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`id`, `nom`) VALUES
(2, 'Cinema'),
(3, 'Sport'),
(4, 'Technologies');

-- --------------------------------------------------------

--
-- Structure de la table `categorie_produit`
--

DROP TABLE IF EXISTS `categorie_produit`;
CREATE TABLE IF NOT EXISTS `categorie_produit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `categorie_produit`
--

INSERT INTO `categorie_produit` (`id`, `libelle`, `image`, `updated_at`) VALUES
(1, 'CLIM', '23c0c00b36fdba812270fe705f934aa5.png', '2019-02-28 08:42:24'),
(2, 'TRAVEL', 'JGMH35M4BBD7T3NP6KTSCKN5X.jpg', '2019-02-28 08:42:48'),
(8, 'SKING', 'W4CMUFD44Q6EBOGW82UPHGCL0.jpg', '2019-02-27 21:54:31'),
(9, 'SPORT', '24E8WQWUJ9HIT8DKXMIXFP4IL.jpg', '2019-04-04 12:17:25'),
(12, 'CAMPING', 'X366M71PJBHLS3ACICFRPK9GL.jpg', '2019-04-04 12:17:25');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `id` int(11) DEFAULT NULL,
  `panier_id` int(11) DEFAULT NULL,
  `file` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created` date NOT NULL,
  `visit` int(11) NOT NULL,
  `total` double NOT NULL,
  `payer` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id`, `panier_id`, `file`, `created`, `visit`, `total`, `payer`) VALUES
(18, 25, 'Facture17.pdf', '2019-04-15', 1, 8675, 'chèque'),
(NULL, 21, 'Facture18.pdf', '2019-05-24', 1, 695, 'chèque'),
(NULL, 21, 'Facture18.pdf', '2019-05-24', 1, 815, 'chèque'),
(NULL, 21, 'Facture18.pdf', '2019-05-24', 1, 86, 'chèque');

-- --------------------------------------------------------

--
-- Structure de la table `comment`
--

DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `forum` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `contenu` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_9474526C852BBECD` (`forum`),
  KEY `IDX_9474526C8D93D649` (`user`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `comment`
--

INSERT INTO `comment` (`id`, `forum`, `user`, `contenu`, `date`) VALUES
(3, 7, NULL, 'commentaire', '2019-05-25 08:32:13'),
(4, 7, NULL, 'dddd', '2019-05-25 08:33:28');

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `experience` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `contenu_commentaire` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date_commentaire` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_67F068BC590C103` (`experience`),
  KEY `IDX_67F068BC8D93D649` (`user`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `commentaire`
--

INSERT INTO `commentaire` (`id`, `experience`, `user`, `contenu_commentaire`, `date_commentaire`) VALUES
(2, 1, NULL, '', '2019-05-24 20:32:31'),
(3, 1, NULL, '', '2019-05-24 20:32:35'),
(4, 7, NULL, 'Validation MOBILE', '2019-05-25 08:27:08'),
(5, 7, NULL, 'Validation MOBILE', '2019-05-25 08:27:15');

-- --------------------------------------------------------

--
-- Structure de la table `commentaireevenement`
--

DROP TABLE IF EXISTS `commentaireevenement`;
CREATE TABLE IF NOT EXISTS `commentaireevenement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) DEFAULT NULL,
  `id_evenement` int(11) DEFAULT NULL,
  `date_commentaire` date DEFAULT NULL,
  `contenu` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Etat_Commentaire` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_550667016B3CA4B` (`id_user`),
  KEY `IDX_550667018B13D439` (`id_evenement`)
) ENGINE=InnoDB AUTO_INCREMENT=192 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `commentaireevenement`
--

INSERT INTO `commentaireevenement` (`id`, `id_user`, `id_evenement`, `date_commentaire`, `contenu`, `Etat_Commentaire`) VALUES
(185, 74, 97222, '2019-05-24', 'utilisateur : \"test commentaire\"', NULL),
(186, 74, 97222, '2019-05-24', 'test commentaire web', 'OK'),
(187, 74, 97222, '2019-05-24', 'utilisateur : \"test commentaire mobile\"', NULL),
(188, 86, 97223, '2019-05-25', 'validationS web', 'OK'),
(189, 86, 97223, '2019-05-25', 'validationS : \"validationS D\"', NULL),
(190, 86, 97223, '2019-05-25', 'validationS : \"validationS M\"', NULL),
(191, 86, 97223, '2019-05-25', 'validationS web', 'OK');

-- --------------------------------------------------------

--
-- Structure de la table `evenement`
--

DROP TABLE IF EXISTS `evenement`;
CREATE TABLE IF NOT EXISTS `evenement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) DEFAULT NULL,
  `nom_evenement` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type_reservation` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `duree_evenement` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lieu_evenement` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `capacite_evenement` int(11) DEFAULT NULL,
  `description_evenement` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Affiche` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `etat_evenement` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prix_evenement` int(11) DEFAULT NULL,
  `date_debut_evenement` datetime DEFAULT NULL,
  `date_fin_evenement` datetime DEFAULT NULL,
  `test` blob,
  PRIMARY KEY (`id`),
  KEY `IDX_B26681E6B3CA4B` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=97224 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `evenement`
--

INSERT INTO `evenement` (`id`, `id_user`, `nom_evenement`, `Type`, `type_reservation`, `duree_evenement`, `lieu_evenement`, `capacite_evenement`, `description_evenement`, `Affiche`, `etat_evenement`, `prix_evenement`, `date_debut_evenement`, `date_fin_evenement`, `test`) VALUES
(71927, 81, 'randonnee de ouf', 'Culturel', NULL, NULL, 'Siliana', 29, 'randonnée dans siliana bienvenue tout le monde', 'randonnee de ouf.jpg', NULL, NULL, '2019-04-22 00:00:00', '2019-04-22 00:00:00', NULL),
(97159, 81, 'Concert a la fort', 'Autres', NULL, NULL, 'Tunis', 97, 'le meilleur groupe de jazz ! ne ratez pas l\'occasion', 'Concert a la fort.jpg', NULL, NULL, '2019-04-26 00:00:00', '2019-04-26 00:00:00', NULL),
(97222, 74, 'validation finall', 'Culturel', 'Gratuite', '0 mois et 2 jours ', 'Tunis', 34, 'validation final description', '1.jpg', 'Non', NULL, '2019-06-01 00:00:00', '2019-06-03 00:00:00', NULL),
(97223, 86, 'validationS', 'Culturel', 'Gratuite', '0 mois et 7 jours ', 'Tunis', 54, 'aaa', '4.jpg', 'Non', NULL, '2019-06-01 14:45:00', '2019-06-08 17:30:00', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `experience`
--

DROP TABLE IF EXISTS `experience`;
CREATE TABLE IF NOT EXISTS `experience` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `categorie` int(11) DEFAULT NULL,
  `nom_exp` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `desc_exp` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lieu_exp` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_exp` datetime DEFAULT NULL,
  `view_count_singular` int(11) NOT NULL DEFAULT '0',
  `view_count_plural` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `IDX_590C1038D93D649` (`user`),
  KEY `IDX_590C103497DD634` (`categorie`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `experience`
--

INSERT INTO `experience` (`id`, `user`, `categorie`, `nom_exp`, `desc_exp`, `lieu_exp`, `image_name`, `date_exp`, `view_count_singular`, `view_count_plural`) VALUES
(1, 80, 2, 'Meilleur Experience', 'test', 'test', 'img1.jpg', '2019-05-23 15:46:26', 3, 3),
(5, 74, 2, '', 'meilleur événement', 'manouba', '9.jpg', '2019-05-31 00:00:00', 0, 0),
(6, 74, 2, 'test', 'azazazaza', 'test', '11.jpg', '2019-05-09 00:00:00', 1, 1),
(7, 86, 2, 'ValidationS', 'desc', 'lieu', '4.jpg', '2019-05-25 08:24:01', 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `forum`
--

DROP TABLE IF EXISTS `forum`;
CREATE TABLE IF NOT EXISTS `forum` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categorie` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime NOT NULL,
  `view_count_singular` int(11) NOT NULL DEFAULT '0',
  `view_count_plural` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `IDX_852BBECD497DD634` (`categorie`),
  KEY `IDX_852BBECD8D93D649` (`user`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `forum`
--

INSERT INTO `forum` (`id`, `categorie`, `user`, `title`, `description`, `date`, `view_count_singular`, `view_count_plural`) VALUES
(3, 2, 74, 'camping', 'azaza', '2019-05-23 10:29:48', 0, 0),
(4, 2, 74, 'randonnée', 'zdafa', '2019-05-24 18:06:01', 1, 1),
(6, 2, 74, 'test', 'question test', '2019-05-25 06:34:39', 0, 0),
(7, 3, 86, 'validationS Question', 'description validation', '2019-05-25 08:30:49', 1, 2);

-- --------------------------------------------------------

--
-- Structure de la table `fos_user`
--

DROP TABLE IF EXISTS `fos_user`;
CREATE TABLE IF NOT EXISTS `fos_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `username_canonical` varchar(180) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email_canonical` varchar(180) COLLATE utf8_unicode_ci DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `confirmation_token` varchar(180) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_requested_at` datetime DEFAULT NULL,
  `roles` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:array)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_957A6479A0D96FBF` (`email_canonical`),
  UNIQUE KEY `UNIQ_957A647992FC23A8` (`username_canonical`),
  UNIQUE KEY `UNIQ_957A6479C05FB297` (`confirmation_token`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `fos_user`
--

INSERT INTO `fos_user` (`id`, `username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `confirmation_token`, `password_requested_at`, `roles`) VALUES
(74, 'utilisateur', 'utilisateur', 'aa', 'aa', 1, NULL, '$2y$13$b2H4RvRUAVVGYTY3Cq2ezu/nxc8oRoxvGXjaQnZyiusiHwX1j92Vq', '2019-05-24 20:00:12', NULL, NULL, 'a:0:{}'),
(80, 'admin', 'admin', 'admin', 'admin', 1, NULL, '$2y$13$HbgLShD1WpIDSNaN8PBimei5SqS.qY6IwuQm2KsojdfPGcK6FKYpO', '2019-05-25 08:34:35', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ADMIN\";}'),
(81, 'bb', 'bb', 'bb', NULL, 1, NULL, '$2y$13$Wni/mrTVxNfq46sO7i.Sl.ZDcKDaMSsui1VI5zBeKxKzMOIwqrqr.', NULL, NULL, NULL, 'a:0:{}'),
(85, 'testtawa', 'testtawa', 'testtawa', 'testtawa', 0, NULL, '$2y$10$Zci4qdM97CBx1gQGsmZphuxEAbUpQsC3Eyt1KlvXys9u.t2Xgrrz.', NULL, NULL, NULL, 'a:1:{i:0;s:0:\"\";}'),
(86, 'validationS', 'validations', 'testvalidation@esprit.tn', 'testvalidation@esprit.tn', 1, NULL, '$2y$13$w0ILYm7qcdJSsfSPLzf/.ODUDVy4MuT53mzj5y9X5H8QN8uwrIvFi', '2019-05-25 08:48:45', NULL, NULL, 'a:0:{}');

-- --------------------------------------------------------

--
-- Structure de la table `lignedepanier`
--

DROP TABLE IF EXISTS `lignedepanier`;
CREATE TABLE IF NOT EXISTS `lignedepanier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `produit_id` int(11) DEFAULT NULL,
  `panier_id` int(11) DEFAULT NULL,
  `quantite` int(11) NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_96D2617FF347EFB` (`produit_id`),
  KEY `IDX_96D2617FF77D927C` (`panier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `lignedepanier`
--

INSERT INTO `lignedepanier` (`id`, `produit_id`, `panier_id`, `quantite`, `created`) VALUES
(15, 4, 50, 1, '2019-05-25 08:46:58'),
(16, 10, 51, 1, '2019-05-25 09:47:52');

-- --------------------------------------------------------

--
-- Structure de la table `like_produit`
--

DROP TABLE IF EXISTS `like_produit`;
CREATE TABLE IF NOT EXISTS `like_produit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `produit_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_7A28A604F347EFB` (`produit_id`),
  KEY `IDX_7A28A604A76ED395` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `thread_id` int(11) DEFAULT NULL,
  `sender_id` int(11) DEFAULT NULL,
  `body` longtext COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_B6BD307FE2904019` (`thread_id`),
  KEY `IDX_B6BD307FF624B39D` (`sender_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `message_metadata`
--

DROP TABLE IF EXISTS `message_metadata`;
CREATE TABLE IF NOT EXISTS `message_metadata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL,
  `participant_id` int(11) DEFAULT NULL,
  `is_read` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_4632F005537A1329` (`message_id`),
  KEY `IDX_4632F0059D1C3019` (`participant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

DROP TABLE IF EXISTS `panier`;
CREATE TABLE IF NOT EXISTS `panier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `total` double NOT NULL,
  `nbr_produit` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_24CC0DF2A76ED395` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`id`, `user_id`, `total`, `nbr_produit`) VALUES
(21, 74, 7, 0),
(25, 81, 7, 0),
(50, 80, 266, 1),
(51, 86, 351, 1);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `souscategorie_id` int(11) DEFAULT NULL,
  `libelle` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `prix` double NOT NULL,
  `stock` int(11) NOT NULL,
  `firstimg` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `secondimg` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `thirdimg` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `updated_at` datetime NOT NULL,
  `taille` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `color` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created` datetime NOT NULL,
  `promotion` int(11) NOT NULL,
  `idpromo` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_29A5EC27A27126E0` (`souscategorie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `souscategorie_id`, `libelle`, `description`, `prix`, `stock`, `firstimg`, `secondimg`, `thirdimg`, `updated_at`, `taille`, `color`, `created`, `promotion`, `idpromo`) VALUES
(1, 4, 'Casque', 'Lorem ipsum dolor sit amet, consectetur adipisicing elitse do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercita tio ullamco laboris nisi ut aliquip.', 454, 0, 'img22.jpg', '890c18c8d5e69cbef45b75e5c2599747.jpeg', '20310b272d016f1b1f027f4ea3c994fd.jpeg', '2019-02-20 11:11:35', 'xl', '#000000', '2019-02-20 11:11:35', 1, 4),
(2, 2, '32-SHILOH-JACKET', 'Sed ac enim eu massa lacinia semper. Nam egestas consequat libero sit amet sodales. Phasellus faucibus quis turpis et iaculis. Sed dignissim erat ut aliquam egestas. Duis feugiat tincidunt lobortis. Fusce hendrerit lectus viverra quam euismod ullamcorper.', 230, 0, 'goggles.jpg', 'img44.jpg', 'img55.jpg', '2019-02-20 11:11:35', 'xl', '#f87654', '2019-02-26 09:53:00', 0, 4),
(3, 1, 'Lightweight-quilted- jacket', 'Sed ac enim eu massa lacinia semper. Nam egestas consequat libero sit amet sodales. Phasellus faucibus quis turpis et iaculis. Sed dignissim erat ut aliquam egestas. Duis feugiat tincidunt lobortis. Fusce hendrerit lectus viverra quam euismod ullamcorper.', 110, 0, 'YBTAY7NT14HO8TOHNBDJFD5P0.jpg', 'img44.jpg', 'img55.jpg', '2019-02-28 06:46:49', 'xl', '#f35654', '2019-02-26 09:53:00', 0, 5),
(4, 2, 'Jacket-with-wraparoun-collar', 'Sed ac enim eu massa lacinia semper. Nam egestas consequat libero sit amet sodales. Phasellus faucibus quis turpis et iaculis. Sed dignissim erat ut aliquam egestas. Duis feugiat tincidunt lobortis. Fusce hendrerit lectus viverra quam euismod ullamcorper.', 259, 8, 'img4.jpg', 'img44.jpg', 'img55.jpg', '2019-02-20 11:11:35', 'xl', '#f87654', '2019-02-26 09:53:00', 0, 5),
(5, 3, 'Pouch-pocket-jacket', 'Sed ac enim eu massa lacinia semper. Nam egestas consequat libero sit amet sodales. Phasellus faucibus quis turpis et iaculis. Sed dignissim erat ut aliquam egestas. Duis feugiat tincidunt lobortis. Fusce hendrerit lectus viverra quam euismod ullamcorper.', 119, 427, 'img4.jpg', 'img44.jpg', 'img55.jpg', '2019-02-20 11:11:35', 'xl', '#f87654', '2019-02-26 09:53:00', 1, 0),
(6, 4, '32-SHILOH-JACKET', 'Sed ac enim eu massa lacinia semper. Nam egestas consequat libero sit amet sodales. Phasellus faucibus quis turpis et iaculis. Sed dignissim erat ut aliquam egestas. Duis feugiat tincidunt lobortis. Fusce hendrerit lectus viverra quam euismod ullamcorper.', 179, 0, 'img4.jpg', 'img44.jpg', 'img55.jpg', '2019-02-20 11:11:35', 'xl', '#f87654', '2019-02-26 09:53:00', 0, 0),
(7, 5, 'Tie-dye-jacket', 'Sed ac enim eu massa lacinia semper. Nam egestas consequat libero sit amet sodales. Phasellus faucibus quis turpis et iaculis. Sed dignissim erat ut aliquam egestas. Duis feugiat tincidunt lobortis. Fusce hendrerit lectus viverra quam euismod ullamcorper.', 229, 0, 'img4.jpg', 'img44.jpg', 'img55.jpg', '2019-02-20 11:11:35', 'xl', '#f87654', '2019-02-26 09:53:00', 1, 0),
(8, 6, 'Stripe', 'Sed ac enim eu massa lacinia semper. Nam egestas consequat libero sit amet sodales. Phasellus faucibus quis turpis et iaculis. Sed dignissim erat ut aliquam egestas. Duis feugiat tincidunt lobortis. Fusce hendrerit lectus viverra quam euismod ullamcorper.', 79, 6, 'img4.jpg', 'stripe-toggle-front-stripe-long-cardigan (1).jpg', 'stripe-toggle-front-stripe-long-cardigan.jpg', '2019-02-27 22:13:37', 's', '#000000ff', '2019-02-21 00:00:00', 0, 0),
(9, 3, 'Backpack-with-flap', 'Sed ac enim eu massa lacinia semper. Nam egestas consequat libero sit amet sodales. Phasellus faucibus quis turpis et iaculis. Sed dignissim erat ut aliquam egestas. Duis feugiat tincidunt lobortis. Fusce hendrerit lectus viverra quam euismod ullamcorper.', 549, 0, 'img55.jpg', 'img1.jpg', 'U1CO9KUKJJOS4ZBGLD.jpg', '2019-02-27 22:18:09', 's', '#000000ff', '2019-02-21 00:00:00', 0, 0),
(10, 6, 'Utility-backpack', 'Sed ac enim eu massa lacinia semper. Nam egestas consequat libero sit amet sodales. Phasellus faucibus quis turpis et iaculis. Sed dignissim erat ut aliquam egestas. Duis feugiat tincidunt lobortis. Fusce hendrerit lectus viverra quam euismod ullamcorper.', 344, 17, 'goggles.jpg', 'PT18E1Q0PGUJSISEYYD4HYEQ1.jpg', '4AC4KOC3XCCNWT80ATGRX4AL4.jpg', '2019-04-16 11:25:47', 'none', '#283593ff', '2019-04-16 11:25:47', 0, 0),
(11, 1, 'Jogger-style-Bermuda-shorts', 'Sed ac enim eu massa lacinia semper. Nam egestas consequat libero sit amet sodales. Phasellus faucibus quis turpis et iaculis. Sed dignissim erat ut aliquam egestas. Duis feugiat tincidunt lobortis. Fusce hendrerit lectus viverra quam euismod ullamcorper.', 349, 314, 'PT18E1Q0PGUJSISEYYD4HYEQ1.jpg', 'YBTAY7NT14HO8TOHNBDJFD5P0.jpg', '3XXJ52EXJ34ZGUCU80DAEZO8N.jpg', '2019-04-16 11:30:56', 'none', '#7cb342ff', '2019-04-16 11:30:56', 0, 0);

-- --------------------------------------------------------

--
-- Structure de la table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
CREATE TABLE IF NOT EXISTS `promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom_Promotion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL,
  `pourcentage` double NOT NULL,
  `prix_initiale` double NOT NULL,
  `prix_promo` double NOT NULL,
  `IDProduit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_C11D7DD1429B6720` (`IDProduit`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `promotion`
--

INSERT INTO `promotion` (`id`, `nom_Promotion`, `date_debut`, `date_fin`, `pourcentage`, `prix_initiale`, `prix_promo`, `IDProduit`) VALUES
(17, 'Stripe', '2019-05-26', '2019-05-29', 50, 79, 39.5, NULL),
(18, '32-SHILOH-JACKET', '2019-05-26', '2019-05-28', 6, 230, 216.2, NULL),
(19, 'Backpack-with-flap', '2019-05-26', '2019-05-29', 99, 549, 5.48999, NULL),
(20, 'Casque', '2020-10-01', '2024-10-19', 10, 454, 408.6, 1);

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id_reservation` int(11) NOT NULL AUTO_INCREMENT,
  `id_evenement` int(11) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `etat` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `type_reservation` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tarif` int(11) DEFAULT NULL,
  `numero_ticket` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_reservation`),
  KEY `IDX_42C849558B13D439` (`id_evenement`),
  KEY `IDX_42C849556B3CA4B` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`id_reservation`, `id_evenement`, `id_user`, `etat`, `type_reservation`, `tarif`, `numero_ticket`) VALUES
(122, 71927, 74, 'Confirmé', NULL, NULL, '1'),
(123, 97222, 74, 'Confirmé', NULL, NULL, NULL),
(124, 97223, 86, 'Confirmé', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `sous_categorie_produit`
--

DROP TABLE IF EXISTS `sous_categorie_produit`;
CREATE TABLE IF NOT EXISTS `sous_categorie_produit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categorie_id` int(11) DEFAULT NULL,
  `libelle` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Created` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_B7039772BCF5E72D` (`categorie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `sous_categorie_produit`
--

INSERT INTO `sous_categorie_produit` (`id`, `categorie_id`, `libelle`, `Created`) VALUES
(1, 8, 'shoes', '2019-02-17 15:54:46'),
(2, 2, 'jeans', '2019-02-08 11:00:00'),
(3, 9, 'Bag', '2019-02-27 21:55:36'),
(4, 12, 'Bontrager', '2019-02-27 21:56:40'),
(5, 1, 'Blouson', '2019-04-04 12:56:29'),
(6, 9, 'Short', '2019-04-11 00:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `thread`
--

DROP TABLE IF EXISTS `thread`;
CREATE TABLE IF NOT EXISTS `thread` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by_id` int(11) DEFAULT NULL,
  `subject` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `is_spam` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_31204C83B03A8386` (`created_by_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `thread_metadata`
--

DROP TABLE IF EXISTS `thread_metadata`;
CREATE TABLE IF NOT EXISTS `thread_metadata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `thread_id` int(11) DEFAULT NULL,
  `participant_id` int(11) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `last_participant_message_date` datetime DEFAULT NULL,
  `last_message_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_40A577C8E2904019` (`thread_id`),
  KEY `IDX_40A577C89D1C3019` (`participant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `vente__produit`
--

DROP TABLE IF EXISTS `vente__produit`;
CREATE TABLE IF NOT EXISTS `vente__produit` (
  `id` int(11) DEFAULT NULL,
  `produit_id` int(11) DEFAULT NULL,
  `nbr_produit` int(11) NOT NULL,
  KEY `FK_FDB8E5BEF347EFB` (`produit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `vente__produit`
--

INSERT INTO `vente__produit` (`id`, `produit_id`, `nbr_produit`) VALUES
(1, 5, 10),
(2, 4, 4),
(3, 6, 3),
(4, 3, 3),
(5, 7, 6),
(6, 8, 2),
(7, 8, 2),
(8, 9, 1),
(9, 9, 2),
(10, 10, 3),
(11, 11, 1),
(12, 11, 3),
(13, 9, 3),
(14, 5, 4),
(15, 5, 2),
(16, 9, 3),
(17, 10, 1),
(NULL, 2, 5),
(NULL, 10, 1),
(NULL, 10, 2),
(NULL, 4, 1),
(NULL, 9, 1),
(NULL, 8, 1);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `FK_9474526C852BBECD` FOREIGN KEY (`forum`) REFERENCES `forum` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_9474526C8D93D649` FOREIGN KEY (`user`) REFERENCES `fos_user` (`id`);

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `FK_67F068BC590C103` FOREIGN KEY (`experience`) REFERENCES `experience` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_67F068BC8D93D649` FOREIGN KEY (`user`) REFERENCES `fos_user` (`id`);

--
-- Contraintes pour la table `commentaireevenement`
--
ALTER TABLE `commentaireevenement`
  ADD CONSTRAINT `FK_550667016B3CA4B` FOREIGN KEY (`id_user`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_550667018B13D439` FOREIGN KEY (`id_evenement`) REFERENCES `evenement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `evenement`
--
ALTER TABLE `evenement`
  ADD CONSTRAINT `FK_B26681E6B3CA4B` FOREIGN KEY (`id_user`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `experience`
--
ALTER TABLE `experience`
  ADD CONSTRAINT `FK_590C103497DD634` FOREIGN KEY (`categorie`) REFERENCES `categorie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_590C1038D93D649` FOREIGN KEY (`user`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `forum`
--
ALTER TABLE `forum`
  ADD CONSTRAINT `FK_852BBECD497DD634` FOREIGN KEY (`categorie`) REFERENCES `categorie` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_852BBECD8D93D649` FOREIGN KEY (`user`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `lignedepanier`
--
ALTER TABLE `lignedepanier`
  ADD CONSTRAINT `FK_96D2617FF347EFB` FOREIGN KEY (`produit_id`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_96D2617FF77D927C` FOREIGN KEY (`panier_id`) REFERENCES `panier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `like_produit`
--
ALTER TABLE `like_produit`
  ADD CONSTRAINT `FK_7A28A604A76ED395` FOREIGN KEY (`user_id`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_7A28A604F347EFB` FOREIGN KEY (`produit_id`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `FK_B6BD307FE2904019` FOREIGN KEY (`thread_id`) REFERENCES `thread` (`id`),
  ADD CONSTRAINT `FK_B6BD307FF624B39D` FOREIGN KEY (`sender_id`) REFERENCES `fos_user` (`id`);

--
-- Contraintes pour la table `message_metadata`
--
ALTER TABLE `message_metadata`
  ADD CONSTRAINT `FK_4632F005537A1329` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  ADD CONSTRAINT `FK_4632F0059D1C3019` FOREIGN KEY (`participant_id`) REFERENCES `fos_user` (`id`);

--
-- Contraintes pour la table `panier`
--
ALTER TABLE `panier`
  ADD CONSTRAINT `FK_24CC0DF2A76ED395` FOREIGN KEY (`user_id`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `FK_29A5EC27A27126E0` FOREIGN KEY (`souscategorie_id`) REFERENCES `sous_categorie_produit` (`id`);

--
-- Contraintes pour la table `promotion`
--
ALTER TABLE `promotion`
  ADD CONSTRAINT `FK_C11D7DD1429B6720` FOREIGN KEY (`IDProduit`) REFERENCES `produit` (`id`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `FK_42C849556B3CA4B` FOREIGN KEY (`id_user`) REFERENCES `fos_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_42C849558B13D439` FOREIGN KEY (`id_evenement`) REFERENCES `evenement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `sous_categorie_produit`
--
ALTER TABLE `sous_categorie_produit`
  ADD CONSTRAINT `FK_B7039772BCF5E72D` FOREIGN KEY (`categorie_id`) REFERENCES `categorie_produit` (`id`);

--
-- Contraintes pour la table `thread`
--
ALTER TABLE `thread`
  ADD CONSTRAINT `FK_31204C83B03A8386` FOREIGN KEY (`created_by_id`) REFERENCES `fos_user` (`id`);

--
-- Contraintes pour la table `thread_metadata`
--
ALTER TABLE `thread_metadata`
  ADD CONSTRAINT `FK_40A577C89D1C3019` FOREIGN KEY (`participant_id`) REFERENCES `fos_user` (`id`),
  ADD CONSTRAINT `FK_40A577C8E2904019` FOREIGN KEY (`thread_id`) REFERENCES `thread` (`id`);

--
-- Contraintes pour la table `vente__produit`
--
ALTER TABLE `vente__produit`
  ADD CONSTRAINT `FK_FDB8E5BEF347EFB` FOREIGN KEY (`produit_id`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
