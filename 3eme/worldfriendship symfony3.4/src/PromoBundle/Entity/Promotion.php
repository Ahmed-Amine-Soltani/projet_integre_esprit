<?php

namespace PromoBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
/**
 * Promotion
 *
 * @ORM\Table(name="promotion")
 * @ORM\Entity(repositoryClass="PromoBundle\Repository\PromotionRepository")
 */
class Promotion
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
     * @var string
     * @Assert\NotBlank
     * @ORM\Column(name="nom_Promotion", type="string", length=255)
     */
    private $nomPromotion;

    /**
     * @var \DateTime
     * @Assert\EqualTo("today")
     * @ORM\Column(name="date_debut", type="date")
     */
    private $dateDebut;

    /**
     * @var \DateTime
     * @Assert\GreaterThan(propertyPath="dateDebut")
     * @ORM\Column(name="date_fin", type="date")
     */
    private $dateFin;

    /**
     * @var float
     * @Assert\LessThan(100)
     * @Assert\NotBlank
     * @ORM\Column(name="pourcentage", type="float")
     */
    private $pourcentage;

    /**
     * @var float
     * @ORM\Column(name="prix_initiale", type="float")
     */
    private $prixInitiale;

    /**
     * @var float
     *
     * @ORM\Column(name="prix_promo", type="float")
     */
    private $prixPromo;


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
     * Set nomPromotion
     *
     * @param string $nomPromotion
     *
     * @return Promotion
     */
    public function setNomPromotion($nomPromotion)
    {
        $this->nomPromotion = $nomPromotion;

        return $this;
    }

    /**
     * Get nomPromotion
     *
     * @return string
     */
    public function getNomPromotion()
    {
        return $this->nomPromotion;
    }

    /**
     * Set dateDebut
     *
     * @param \DateTime $dateDebut
     *
     * @return Promotion
     */
    public function setDateDebut($dateDebut)
    {
        $this->dateDebut = $dateDebut;

        return $this;
    }
    /**
     * @ORM\ManyToOne(targetEntity="ProduitBundle\Entity\Produit")
     * @ORM\JoinColumn(name="IDProduit",referencedColumnName="id")
     */
    private $Produit ;
    /**
     * Get dateDebut
     *
     * @return \DateTime
     */
    public function getDateDebut()
    {
        return $this->dateDebut;
    }

    /**
     * Set dateFin
     *
     * @param \DateTime $dateFin
     *
     * @return Promotion
     */
    public function setDateFin($dateFin)
    {
        $this->dateFin = $dateFin;

        return $this;
    }

    /**
     * Get dateFin
     *
     * @return \DateTime
     */
    public function getDateFin()
    {
        return $this->dateFin;
    }

    /**
     * Set pourcentage
     *
     * @param float $pourcentage
     *
     * @return Promotion
     */
    public function setPourcentage($pourcentage)
    {
        $this->pourcentage = $pourcentage;

        return $this;
    }

    /**
     * Get pourcentage
     *
     * @return float
     */
    public function getPourcentage()
    {
        return $this->pourcentage;
    }

    /**
     * Set prixInitiale
     *
     * @param float $prixInitiale
     *
     * @return Promotion
     */
    public function setPrixInitiale($prixInitiale)
    {
        $this->prixInitiale = $prixInitiale;

        return $this;
    }

    /**
     * Get prixInitiale
     *
     * @return float
     */
    public function getPrixInitiale()
    {
        return $this->prixInitiale;
    }

    /**
     * Set prixPromo
     *
     * @param float $prixPromo
     *
     * @return Promotion
     */
    public function setPrixPromo($prixPromo)
    {
        $this->prixPromo = $prixPromo;

        return $this;
    }

    /**
     * Get prixPromo
     *
     * @return float
     */
    public function getPrixPromo()
    {
        return $this->prixPromo;
    }

    /**
     * Set produit
     *
     * @param \ProduitBundle\Entity\Produit $produit
     *
     * @return Promotion
     */
    public function setProduit(\ProduitBundle\Entity\Produit $produit = null)
    {
        $this->Produit = $produit;

        return $this;
    }

    /**
     * Get produit
     *
     * @return \ProduitBundle\Entity\Produit
     */
    public function getProduit()
    {
        return $this->Produit;
    }
}
