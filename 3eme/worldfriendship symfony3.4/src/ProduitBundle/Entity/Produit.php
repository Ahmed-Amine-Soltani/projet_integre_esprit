<?php

namespace ProduitBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\HttpFoundation\File\File;
use Vich\UploaderBundle\Mapping\Annotation as Vich;

/**
 * Produit
 *
 * @ORM\Table(name="produit")
 * @ORM\Entity(repositoryClass="ProduitBundle\Repository\ProduitRepository")
 * @Vich\Uploadable
 */
class Produit
{

    /**
     * @var int
     *
     * @ORM\Column(name="promotion", type="integer")
     */
    private $promotion;

    /**
     * Set promotion
     *
     * @param integer $promotion
     *
     * @return Produit
     */
    public function setPromotion($promotion)
    {
        $this->promotion = $promotion;

        return $this;
    }

    /**
     * Get promotion
     *
     * @return int
     */
    public function getPromotion()
    {
        return $this->promotion;
    }

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
     * @var string
     *
     * @ORM\Column(name="description", type="text")
     */
    private $description;

    /**
     * @var float
     *
     * @ORM\Column(name="prix", type="float")
     */
    private $prix;

    /**
     * @var int
     *
     * @ORM\Column(name="stock", type="integer")
     */
    private $stock;

    /**
     * @var string
     *
     * @ORM\Column(name="firstimg", type="string", length=255)
     */
    private $firstimg;

    /**
     * @Vich\UploadableField(mapping="produit_image", fileNameProperty="firstimg")
     * @var File
     */
    private $imagefileFirst;

    /**
     * @var string
     *
     * @ORM\Column(name="secondimg", type="string", length=255)
     */
    private $secondimg;

    /**
     * @Vich\UploadableField(mapping="produit_image", fileNameProperty="secondimg")
     * @var File
     */
    private $imagefileSecond;

    /**
     * @var string
     *
     * @ORM\Column(name="thirdimg", type="string", length=255)
     */
    private $thirdimg;

    /**
     * @Vich\UploadableField(mapping="produit_image", fileNameProperty="thirdimg")
     * @var File
     */
    private $imagefileThird;


    /**
     * @ORM\Column(type="datetime")
     * @var \DateTime
     */
    private $updatedAt;



    /**
     * @var string
     *
     * @ORM\Column(name="taille", type="string", length=255)
     */
    private $taille;

    /**
     * @var string
     *
     * @ORM\Column(name="color", type="string", length=255)
     */
    private $color;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="created", type="datetime")
     */
    private $created;


    /**
     * @ORM\ManyToOne(targetEntity="SousCategorieProduit",inversedBy="produits")
     * @ORM\JoinColumn(name="souscategorie_id",referencedColumnName="id")
     */
    private $souscategorie;


    /**
     * @ORM\OneToMany(targetEntity="LikeProduit",mappedBy="produit")
     */

    private $likes;

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
     * @return Produit
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

    /**
     * Set description
     *
     * @param string $description
     *
     * @return Produit
     */
    public function setDescription($description)
    {
        $this->description = $description;

        return $this;
    }

    /**
     * Get description
     *
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * Set prix
     *
     * @param float $prix
     *
     * @return Produit
     */
    public function setPrix($prix)
    {
        $this->prix = $prix;

        return $this;
    }

    /**
     * Get prix
     *
     * @return float
     */
    public function getPrix()
    {
        return $this->prix;
    }

    /**
     * Set stock
     *
     * @param integer $stock
     *
     * @return Produit
     */
    public function setStock($stock)
    {
        $this->stock = $stock;

        return $this;
    }

    /**
     * Get stock
     *
     * @return int
     */
    public function getStock()
    {
        return $this->stock;
    }



    public function setImageFileFirst(File $image = null)
    {
        $this->imagefileFirst = $image;

        // VERY IMPORTANT:
        // It is required that at least one field changes if you are using Doctrine,
        // otherwise the event listeners won't be called and the file is lost
        if ($image) {
            // if 'updatedAt' is not defined in your entity, use another property
            $this->updatedAt = new \DateTime('now');
        }
    }

    public function getImageFileFirst()
    {
        return $this->imagefileFirst;
    }

    /**
     * @return string
     */
    public function getFirstimg()
    {
        return $this->firstimg;
    }

    /**
     * @param string $firstimg
     */
    public function setFirstimg($firstimg)
    {
        $this->firstimg = $firstimg;
    }


    public function setImageFileSecond(File $image = null)
    {
        $this->imagefileSecond = $image;

        // VERY IMPORTANT:
        // It is required that at least one field changes if you are using Doctrine,
        // otherwise the event listeners won't be called and the file is lost
        if ($image) {
            // if 'updatedAt' is not defined in your entity, use another property
            $this->updatedAt = new \DateTime('now');
        }
    }

    public function getImageFileSecond()
    {
        return $this->imagefileSecond;
    }

    /**
     * @return string
     */
    public function getSecondimg()
    {
        return $this->secondimg;
    }

    /**
     * @param string $secondimg
     */
    public function setSecondimg($secondimg)
    {
        $this->secondimg = $secondimg;
    }



    public function setImageFileThird(File $image = null)
    {
        $this->imagefileThird = $image;

        // VERY IMPORTANT:
        // It is required that at least one field changes if you are using Doctrine,
        // otherwise the event listeners won't be called and the file is lost
        if ($image) {
            // if 'updatedAt' is not defined in your entity, use another property
            $this->updatedAt = new \DateTime('now');
        }
    }

    public function getImageFileThird()
    {
        return $this->imagefileThird;
    }

    /**
     * @return string
     */
    public function getThirdimg()
    {
        return $this->thirdimg;
    }

    /**
     * @param string $thirdimg
     */
    public function setThirdimg($thirdimg)
    {
        $this->thirdimg = $thirdimg;
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
     * Set taille
     *
     * @param string $taille
     *
     * @return Produit
     */
    public function setTaille($taille)
    {
        $this->taille = $taille;

        return $this;
    }

    /**
     * Get taille
     *
     * @return string
     */
    public function getTaille()
    {
        return $this->taille;
    }

    /**
     * Set color
     *
     * @param string $color
     *
     * @return Produit
     */
    public function setColor($color)
    {
        $this->color = $color;

        return $this;
    }

    /**
     * Get color
     *
     * @return string
     */
    public function getColor()
    {
        return $this->color;
    }

    /**
     * Set created
     *
     * @param \DateTime $created
     *
     * @return Produit
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
    public function getSouscategorie()
    {
        return $this->souscategorie;
    }

    /**
     * @param mixed $souscategorie
     */
    public function setSouscategorie($souscategorie)
    {
        $this->souscategorie = $souscategorie;
    }



    /**
     * Constructor
     */
    public function __construct()
    {
        $this->likes = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Add like
     *
     * @param \ProduitBundle\Entity\LikeProduit $like
     *
     * @return Produit
     */
    public function addLike(\ProduitBundle\Entity\LikeProduit $like)
    {
        $this->likes[] = $like;

        return $this;
    }

    /**
     * Remove like
     *
     * @param \ProduitBundle\Entity\LikeProduit $like
     */
    public function removeLike(\ProduitBundle\Entity\LikeProduit $like)
    {
        $this->likes->removeElement($like);
    }

    /**
     * Get likes
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getLikes()
    {
        return $this->likes;
    }
}
