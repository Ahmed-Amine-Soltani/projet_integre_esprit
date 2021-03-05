<?php

namespace ProduitBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\HttpFoundation\File\File;
use Vich\UploaderBundle\Mapping\Annotation as Vich;

/**
 * CategorieProduit
 *
 * @ORM\Table(name="categorie_produit")
 * @ORM\Entity(repositoryClass="ProduitBundle\Repository\CategorieProduitRepository")
 * @Vich\Uploadable
 */
class CategorieProduit
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
     *
     * @ORM\Column(name="libelle", type="string", length=255)
     */
    private $libelle;


    /**
     * @ORM\Column(name="image",type="string", length=255)
     * @var string
     */
    private $image;

    /**
     * @Vich\UploadableField(mapping="categorie_image", fileNameProperty="image")
     * @var File
     */
    private $imageFile;

    /**
     * @ORM\Column(type="datetime")
     * @var \DateTime
     */
    private $updatedAt;


    /**
     * @var Date
     *
     * @ORM\Column(name="created", type="datetime", length=255)
     */


    /**
     * @ORM\OneToMany(targetEntity="SousCategorieProduit",mappedBy="categorie")
     */
    private $souscategorieproduits;


    private $created;

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
     * Set libelle
     *
     * @param string $libelle
     *
     * @return CategorieProduit
     */
    public function setLibelle($libelle)
    {
        $this->libelle = $libelle;

        return $this;
    }

    /**
     * Get libelle
     *
     * @return string
     */
    public function getLibelle()
    {
        return $this->libelle;
    }

    public function setImageFile(File $image = null)
    {
        $this->imageFile = $image;

        // VERY IMPORTANT:
        // It is required that at least one field changes if you are using Doctrine,
        // otherwise the event listeners won't be called and the file is lost
        if ($image) {
            // if 'updatedAt' is not defined in your entity, use another property
            $this->updatedAt = new \DateTime('now');
        }
    }

    public function getImageFile()
    {
        return $this->imageFile;
    }

    public function setImage($image)
    {
        $this->image = $image;
    }


    public function getImage()
    {
        return $this->image;
    }

    /**
     * @return \DateTime
     */
    public function getUpdatedAt()
    {
        return $this->updatedAt;
    }

    /**
     * @param \DateTime $updatedAt
     */
    public function setUpdatedAt($updatedAt)
    {
        $this->updatedAt = $updatedAt;
    }

    /**
     * @return Date
     */
    public function getCreated()
    {
        return $this->created;
    }

    /**
     * @param Date $created
     */
    public function setCreated($created)
    {
        $this->created = $created;
    }

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->souscategorieproduits = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Add souscategorieproduit
     *
     * @param \ProduitBundle\Entity\SousCategorieProduit $souscategorieproduit
     *
     * @return CategorieProduit
     */
    public function addSouscategorieproduit(\ProduitBundle\Entity\SousCategorieProduit $souscategorieproduit)
    {
        $this->souscategorieproduits[] = $souscategorieproduit;

        return $this;
    }

    /**
     * Remove souscategorieproduit
     *
     * @param \ProduitBundle\Entity\SousCategorieProduit $souscategorieproduit
     */
    public function removeSouscategorieproduit(\ProduitBundle\Entity\SousCategorieProduit $souscategorieproduit)
    {
        $this->souscategorieproduits->removeElement($souscategorieproduit);
    }

    /**
     * Get souscategorieproduits
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getSouscategorieproduits()
    {
        return $this->souscategorieproduits;
    }
}
