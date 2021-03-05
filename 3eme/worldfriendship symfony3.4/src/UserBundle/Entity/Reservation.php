<?php

namespace UserBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * ReservationForm
 *
 * @ORM\Table(name="reservation")
 * @ORM\Entity(repositoryClass="UserBundle\Repository\ReservationRepository")
 */
class Reservation
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_reservation", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $idReservation;

    /**
     *@ORM\ManyToOne (targetEntity="UserBundle\Entity\Evenement", inversedBy="reservations")
     *@ORM\JoinColumn(name="id_evenement", referencedColumnName="id")
     */
    private $idEvenement;

    /**
     *@ORM\ManyToOne (targetEntity="UserBundle\Entity\User")
     *@ORM\JoinColumn(name="id_user", referencedColumnName="id")
     */
    private $idUser;

    /**
     * @var string
     *
     * @ORM\Column(name="etat", type="string", length=255)
     */
    private $etat;

    /**
     * @var string
     *
     * @ORM\Column(name="type_reservation", type="string", length=255)
     */
    private $typeReservation;

    /**
     * @var int
     *
     * @ORM\Column(name="tarif", type="integer", nullable=true)
     */
    private $tarif;

    /**
     * @var string
     *
     * @ORM\Column(name="numero_ticket", type="string", length=255)
     */
    private $numeroTicket;


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->idReservation;
    }

    /**
     * Set idEvenement
     *
     * @param integer $idEvenement
     *
     * @return Reservation
     */
    public function setIdEvenement($idEvenement)
    {
        $this->idEvenement = $idEvenement;

        return $this;
    }

    /**
     * Get idEvenement
     *
     * @return int
     */
    public function getIdEvenement()
    {
        return $this->idEvenement;
    }

    /**
     * Set idUser
     *
     * @param integer $idUser
     *
     * @return Reservation
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
     * Set etat
     *
     * @param string $etat
     *
     * @return Reservation
     */
    public function setEtat($etat)
    {
        $this->etat = $etat;

        return $this;
    }

    /**
     * Get etat
     *
     * @return string
     */
    public function getEtat()
    {
        return $this->etat;
    }

    /**
     * Set typeReservation
     *
     * @param string $typeReservation
     *
     * @return Reservation
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
     * Set tarif
     *
     * @param integer $tarif
     *
     * @return Reservation
     */
    public function setTarif($tarif)
    {
        $this->tarif = $tarif;

        return $this;
    }

    /**
     * Get tarif
     *
     * @return int
     */
    public function getTarif()
    {
        return $this->tarif;
    }

    /**
     * Set numeroTicket
     *
     * @param string $numeroTicket
     *
     * @return Reservation
     */
    public function setNumeroTicket($numeroTicket)
    {
        $this->numeroTicket = $numeroTicket;

        return $this;
    }

    /**
     * Get numeroTicket
     *
     * @return string
     */
    public function getNumeroTicket()
    {
        return $this->numeroTicket;
    }

    /**
     * Get idReservation
     *
     * @return integer
     */
    public function getIdReservation()
    {
        return $this->idReservation;
    }
}
