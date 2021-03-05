<?php

namespace ProduitBundle\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * Panier
 *
 * @ORM\Table(name="panier")
 * @ORM\Entity(repositoryClass="ProduitBundle\Repository\PanierRepository")
 */
class Panier
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
     * @var float
     *
     * @ORM\Column(name="total", type="float")
     */
    private $total;

    /**
     * @var int
     *
     * @ORM\Column(name="nbr_produit", type="integer")
     */
    private $nbrProduit;

    /**
     * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User")
     * @ORM\JoinColumn(name="user_id",referencedColumnName="id")
     */
    private $user;

    /**
     * @ORM\OneToMany(targetEntity="lignedepanier",mappedBy="panier")
     */
    private $lignedepaniers;


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
     * Set total
     *
     * @param float $total
     *
     * @return Panier
     */
    public function setTotal($total)
    {
        $this->total = $total;

        return $this;
    }

    /**
     * Get total
     *
     * @return float
     */
    public function getTotal()
    {
        return $this->total;
    }

    /**
     * Set nbrProduit
     *
     * @param integer $nbrProduit
     *
     * @return Panier
     */
    public function setNbrProduit($nbrProduit)
    {
        $this->nbrProduit = $nbrProduit;

        return $this;
    }

    /**
     * Get nbrProduit
     *
     * @return int
     */
    public function getNbrProduit()
    {
        return $this->nbrProduit;
    }

    /**
     * @return mixed
     */
    public function getUser()
    {
        return $this->user;
    }

    /**
     * @param mixed $user
     */
    public function setUser($user)
    {
        $this->user = $user;
    }




    /**
     * Constructor
     */
    public function __construct()
    {
        $this->lignedepaniers = new ArrayCollection();
    }

    /**
     * Add lignedepanier
     *
     * @param lignedepanier $lignedepanier
     *
     * @return Panier
     */
    public function addLignedepanier(lignedepanier $lignedepanier)
    {
        $this->lignedepaniers[] = $lignedepanier;

        return $this;
    }

    /**
     * Remove lignedepanier
     *
     * @param lignedepanier $lignedepanier
     */
    public function removeLignedepanier(lignedepanier $lignedepanier)
    {
        $this->lignedepaniers->removeElement($lignedepanier);
    }

    /**
     * Get lignedepaniers
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getLignedepaniers()
    {
        return $this->lignedepaniers;
    }
}
