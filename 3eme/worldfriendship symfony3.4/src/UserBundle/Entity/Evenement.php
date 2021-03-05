<?php

namespace UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Validator\Constraints as Assert;
use Vich\UploaderBundle\Mapping\Annotation as Vich;

/**
 * Evenement
 *
 * @ORM\Entity(repositoryClass="UserBundle\Entity\EvenementRepository")
 * @ORM\Table(name="evenement")
 * @Vich\Uploadable
 */
class Evenement
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var int
     *
     * @ORM\ManyToOne (targetEntity="UserBundle\Entity\User")
     * @ORM\JoinColumn(name="id_user", referencedColumnName="id")
     */
    private $idUser;

    /**
     * @ORM\OneToMany (targetEntity="UserBundle\Entity\Reservation", mappedBy="idEvenement")
     */
    private $reservations;

    /**
     * @ORM\OneToMany (targetEntity="CommentaireEvenement", mappedBy="idEvenement")
     */
    private $commentaires;

    /**
     * @var string
     *
     * @ORM\Column(name="nom_evenement", type="string", length=255)
     */
    private $nomEvenement;

    /**
     * @var string
     * @Assert\Choice(
     *     choices = { "Associatif", "Culturel", "Autres" },
     *     message = "Veuillez choisir un type pour votre événement."
     * )
     * @ORM\Column(name="Type", type="string", length=255, nullable=false)
     */
    private $typeEvenement;

    /**
     * @var string
     * @Assert\Choice(
     *     choices = { "Gratuite", "Payante" },
     *     message = "Veuillez choisir un type de réservation."
     * )
     * @ORM\Column(name="type_reservation", type="string", length=255, nullable=false)
     */
    private $typeReservation;

    /**
     * @var string

     * @ORM\Column(name="test", type="string", length=255, nullable=true)
     */
    private $test;

    /**
     * @var \DateTime
     * @Assert\GreaterThanOrEqual(
     *     value = "now",
     *     message = "Veuillez choisir une date valide."
     * )
     * @ORM\Column(name="date_debut_evenement", type="datetime", nullable=false)
     */
    private $dateDebutEvenement;


    /**
     * @var \DateTime
     * @Assert\GreaterThanOrEqual(
     *     value = "now",
     *     message = "Veuillez choisir une date valide."
     * )
     * @ORM\Column(name="date_fin_evenement", type="datetime", nullable=true)
     */
    private $dateFinEvenement;


    /**
     * @var string
     * @Assert\Regex(
     *     pattern     = "/^[a-zA-Z0-9 ]+$/",
     *     match=true,
     *     message="Veuillez un choisir une durée valide."
     * )
     * @ORM\Column(name="duree_evenement", type="string", length=255, nullable=false)
     */
    private $dureeEvenement;

    /**
     * @var string
     * @Assert\Regex(
     *     pattern     = "/^[a-zA-Z0-9 ]+$/",
     *     match=true,
     *     message="Veuillez un choisir une localisation valide"
     * )
     * @ORM\Column(name="lieu_evenement", type="string", length=255, nullable=false)
     */
    private $lieuEvenement;

    /**
     * @var int
     *
     * @ORM\Column(name="capacite_evenement", type="integer")
     */
    private $capaciteEvenement;

    /**
     * @var string
     * @Assert\Length(max=255)
     * @ORM\Column(name="description_evenement", type="string", length=255, nullable=false)
     */
    private $descriptionEvenement;
    /**
     * @var string
     *
     * @ORM\Column(name="Affiche", type="string", length=255, nullable=false)
     */
    private $affiche;

    /**
     * @Assert\Image(
     *     maxSize="5M",
     *     mimeTypesMessage = "Veuillez uploader un fichier de type Image"
     * )
     * @Vich\UploadableField(mapping="event_affiche", fileNameProperty="affiche")
     */
    private $afficheFile;

    private $updatedAt;


    /**
     * @var string
     *
     * @ORM\Column(name="etat_evenement", type="string", length=255)
     */
    private $etatEvenement;


    /**
     * @var integer
     * @Assert\GreaterThanOrEqual(
     *     value = "1",
     *     message = "Veuillez choisir un entier valide."
     * )
     * @ORM\Column(name="prix_evenement", type="integer", nullable=true)
     */
    private $prixEvenement;


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set idUser
     *
     * @param integer $idUser
     *
     * @return Evenement
     */
    public function setIdUser($idUser)
    {
        $this->idUser = $idUser;

        return $this;
    }

    /**
     * Get idUser
     *
     * @return int
     */
    public function getIdUser()
    {
        return $this->idUser;
    }


    /**
     * Set nomEvenement
     *
     * @param string $nomEvenement
     *
     * @return Evenement
     */
    public function setNomEvenement($nomEvenement)
    {
        $this->nomEvenement = $nomEvenement;

        return $this;
    }

    /**
     * Get nomEvenement
     *
     * @return string
     */
    public function getNomEvenement()
    {
        return $this->nomEvenement;
    }

    /**
     * Set typeEvenement
     *
     * @param string $typeEvenement
     *
     * @return Evenement
     */
    public function setTypeEvenement($typeEvenement)
    {
        $this->typeEvenement = $typeEvenement;

        return $this;
    }

    /**
     * Get typeEvenement
     *
     * @return string
     */
    public function getTypeEvenement()
    {
        return $this->typeEvenement;
    }

    /**
     * Set typeReservation
     *
     * @param string $typeReservation
     *
     * @return Evenement
     */
    public function setTypeReservation($typeReservation)
    {
        $this->typeReservation = $typeReservation;

        return $this;
    }

    /**
     * Get typeReservation
     *
     * @return string
     */
    public function getTypeReservation()
    {
        return $this->typeReservation;
    }


    /**
     * Set lieuEvenement
     *
     * @param string $lieuEvenement
     *
     * @return Evenement
     */
    public function setLieuEvenement($lieuEvenement)
    {
        $this->lieuEvenement = $lieuEvenement;

        return $this;
    }

    /**
     * Get lieuEvenement
     *
     * @return string
     */
    public function getLieuEvenement()
    {
        return $this->lieuEvenement;
    }


    /**
     * Set descriptionEvenement
     *
     * @param string $descriptionEvenement
     *
     * @return Evenement
     */
    public function setDescriptionEvenement($descriptionEvenement)
    {
        $this->descriptionEvenement = $descriptionEvenement;

        return $this;
    }

    /**
     * Get descriptionEvenement
     *
     * @return string
     */
    public function getDescriptionEvenement()
    {
        return $this->descriptionEvenement;
    }

    /**
     * Set etatEvenement
     *
     * @param string $etatEvenement
     *
     * @return Evenement
     */
    public function setEtatEvenement($etatEvenement)
    {
        $this->etatEvenement = $etatEvenement;

        return $this;
    }

    /**
     * Get etatEvenement
     *
     * @return string
     */
    public function getEtatEvenement()
    {
        return $this->etatEvenement;
    }

    /**
     * Set prixEvenement
     *
     * @param integer $prixEvenement
     *
     * @return Evenement
     */
    public function setPrixEvenement($prixEvenement)
    {
        $this->prixEvenement = $prixEvenement;

        return $this;
    }

    /**
     * Get prixEvenement
     *
     * @return int
     */
    public function getPrixEvenement()
    {
        return $this->prixEvenement;
    }

    /**
     * Set dureeEvenement
     *
     * @param string $dureeEvenement
     *
     * @return Evenement
     */
    public function setDureeEvenement($dureeEvenement)
    {
        $this->dureeEvenement = $dureeEvenement;

        return $this;
    }

    /**
     * Get dureeEvenement
     *
     * @return string
     */
    public function getDureeEvenement()
    {
        return $this->dureeEvenement;
    }

    /**
     * Set capaciteEvenement
     *
     * @param integer $capaciteEvenement
     *
     * @return Evenement
     */
    public function setCapaciteEvenement($capaciteEvenement)
    {
        $this->capaciteEvenement = $capaciteEvenement;

        return $this;
    }

    /**
     * Get capaciteEvenement
     *
     * @return integer
     */
    public function getCapaciteEvenement()
    {
        return $this->capaciteEvenement;
    }

    /**
     * Set affiche
     *
     * @param string $affiche
     *
     * @return Evenement
     */
    public function setAffiche($affiche)
    {
        $this->affiche = $affiche;

        return $this;
    }

    /**
     * Get affiche
     *
     * @return string
     */
    public function getAffiche()
    {
        return $this->affiche;
    }


    public function setAfficheFile(File $affiche = null)
    {
        $this->afficheFile = $affiche;

        if ($affiche) {
            // It is required that at least one field changes if you are using doctrine
            // otherwise the event listeners won't be called and the file is lost
            try {
                $this->updatedAt = new \DateTimeImmutable();
            } catch (\Exception $e) {
            }
        }

    }

    public function getAfficheFile()
    {
        return $this->afficheFile;
    }


    /**
     * Set dateDebutEvenement
     *
     * @param \DateTime $dateDebutEvenement
     *
     * @return Evenement
     */
    public function setDateDebutEvenement($dateDebutEvenement)
    {
        $this->dateDebutEvenement = $dateDebutEvenement;

        return $this;
    }

    /**
     * Get dateDebutEvenement
     *
     * @return \DateTime
     */
    public function getDateDebutEvenement()
    {
        return $this->dateDebutEvenement;
    }


    /**
     * Set dateFinEvenement
     *
     * @param \DateTime $dateFinEvenement
     *
     * @return Evenement
     */
    public function setDateFinEvenement($dateFinEvenement)
    {
        $this->dateFinEvenement = $dateFinEvenement;

        return $this;
    }

    /**
     * Get dateFinEvenement
     *
     * @return \DateTime
     */
    public function getDateFinEvenement()
    {
        return $this->dateFinEvenement;
    }

    /**
     * Add reservation
     *
     * @param \UserBundle\Entity\Reservation $reservation
     *
     * @return Evenement
     */
    public function addReservation(\UserBundle\Entity\Reservation $reservation)
    {
        $this->reservations[] = $reservation;

        return $this;
    }

    /**
     * Remove reservation
     *
     * @param \UserBundle\Entity\Reservation $reservation
     */
    public function removeReservation(\UserBundle\Entity\Reservation $reservation)
    {
        $this->reservations->removeElement($reservation);
    }


    /**
     * Get reservations
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getReservations()
    {
        return $this->reservations;
    }

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->commentaires = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Add commentaire
     *
     * @param \UserBundle\Entity\CommentaireEvenement $commentaire
     *
     * @return Evenement
     */
    public function addCommentaire(\UserBundle\Entity\CommentaireEvenement $commentaire)
    {
        $this->commentaires[] = $commentaire;

        return $this;
    }

    /**
     * Remove commentaire
     *
     * @param \UserBundle\Entity\CommentaireEvenement $commentaire
     */
    public function removeCommentaire(\UserBundle\Entity\CommentaireEvenement $commentaire)
    {
        $this->commentaires->removeElement($commentaire);
    }

    /**
     * Get commentaires
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getCommentaires()
    {
        return $this->commentaires;
    }








    /**
     * Set test
     *
     * @param string $test
     *
     * @return Evenement
     */
    public function setTest($test)
    {
        $this->test = $test;

        return $this;
    }

    /**
     * Get test
     *
     * @return string
     */
    public function getTest()
    {
        return $this->test;
    }
}
