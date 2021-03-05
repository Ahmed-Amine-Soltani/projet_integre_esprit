<?php

namespace ProduitBundle\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\Mapping as ORM;

/**
 * lignedepanier
 *
 * @ORM\Table(name="lignedepanier")
 * @ORM\Entity(repositoryClass="ProduitBundle\Repository\lignedepanierRepository")
 */
class lignedepanier
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
     * @ORM\Column(name="quantite", type="integer")
     */
    private $quantite;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="created", type="datetime")
     */
    private $created;


    /**
     * @ORM\ManyToOne(targetEntity="Produit")
     * @ORM\JoinColumn(name="produit_id",referencedColumnName="id")
     */
    private $produit;


    /**
     * @ORM\ManyToOne(targetEntity="Panier",inversedBy="lignedepaniers")
     * @ORM\JoinColumn(name="panier_id",referencedColumnName="id")
     */
    private $panier;


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
     * @return int
     */
    public function getQuantite()
    {
        return $this->quantite;
    }

    /**
     * @param int $quantite
     */
    public function setQuantite($quantite)
    {
        $this->quantite = $quantite;
    }


    /**
     * Set created
     *
     * @param \DateTime $created
     *
     * @return lignedepanier
     */
    public function setCreated($created)
    {
        $this->created = $created;

        return $this;
    }

    /**
     * Get created
     *
     * @return \DateTime
     */
    public function getCreated()
    {
        return $this->created;
    }


    /**
     * @return mixed
     */
    public function getProduit()
    {
        return $this->produit;
    }

    /**
     * @param mixed $produit
     */
    public function setProduit($produit)
    {
        $this->produit = $produit;
    }




    /**
     * Set panier
     *
     * @param \ProduitBundle\Entity\Panier $panier
     *
     * @return lignedepanier
     */
    public function setPanier(\ProduitBundle\Entity\Panier $panier = null)
    {
        $this->panier = $panier;

        return $this;
    }

    /**
     * Get panier
     *
     * @return \ProduitBundle\Entity\Panier
     */
    public function getPanier()
    {
        return $this->panier;
    }
}
