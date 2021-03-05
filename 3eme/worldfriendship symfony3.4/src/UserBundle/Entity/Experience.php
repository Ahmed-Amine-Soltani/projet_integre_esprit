<?php

namespace UserBundle\Entity;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Vich\UploaderBundle\Entity\File as EmbeddedFile;
use Vich\UploaderBundle\Mapping\Annotation as Vich;
use Cengizhan\ViewsCounterBundle\Model\VisitableInterface;
use Cengizhan\ViewsCounterBundle\Traits\VisitableEntityTrait;



use Doctrine\ORM\Mapping as ORM;

/**
 * Experience
 * @Vich\Uploadable
 * @ORM\Table(name="experience")
 * @ORM\Entity(repositoryClass="UserBundle\Repository\ExperienceRepository")
 */
class Experience implements VisitableInterface
{
    use VisitableEntityTrait;
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
     * @ORM\Column(name="nom_exp", type="string", length=255)
     */
    private $nomExp;

    /**
     * @var string
     *
     * @ORM\Column(name="desc_exp", type="string", length=255)
     */
    private $descExp;

    /**
     * @var string
     *
     * @ORM\Column(name="lieu_exp", type="string", length=255)
     */
    private $lieuExp;

    /**
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumn(name="user", referencedColumnName="id")
     */
    private $user;

    /**
     * @ORM\ManyToOne(targetEntity="categorie")
     * @ORM\JoinColumn(name="categorie", referencedColumnName="id")
     */
    private $categorie;

    /**
     * NOTE: This is not a mapped field of entity metadata, just a simple property.
     *
     * @Vich\UploadableField(mapping="experience_image" ,fileNameProperty="imageName")
     *
     * @var File
     */
    private $imageFile;

    /**
     * @ORM\Column(type="string", length=255)
     *
     * @var string
     */
    private $imageName;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_exp", type="datetimetz")
     */
    private $dateExp;

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
     * Set nomExp
     *
     * @param string $nomExp
     *
     * @return Experience
     */

    public function setNomExp($nomExp)
    {
        $this->nomExp = $nomExp;

        return $this;
    }

    /**
     * Get nomExp
     *
     * @return string
     */
    public function getNomExp()
    {
        return $this->nomExp;
    }

    /**
     * Set descExp
     *
     * @param string $descExp
     *
     * @return Experience
     */
    public function setDescExp($descExp)
    {
        $this->descExp = $descExp;

        return $this;
    }

    /**
     * Get descExp
     *
     * @return string
     */
    public function getDescExp()
    {
        return $this->descExp;
    }



    /**
     * Set lieuExp
     *
     * @param string $lieuExp
     *
     * @return Experience
     */
    public function setLieuExp($lieuExp)
    {
        $this->lieuExp = $lieuExp;

        return $this;
    }

    /**
     * Get lieuExp
     *
     * @return string
     */
    public function getLieuExp()
    {
        return $this->lieuExp;
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
     * @return mixed
     */
    public function getCategorie()
    {
        return $this->categorie;
    }

    /**
     * @param mixed $categorie
     */
    public function setCategorie($categorie)
    {
        $this->categorie = $categorie;
    }

    /**
     * @return mixed
     */
    public function __toString()
    {
        return (string) $this->id;
    }


    public function setImageFile($imageFile)
    {
        $this->imageFile = $imageFile;
    }

    public function getImageFile()
    {
        return $this->imageFile;
    }

    public function setImageName($imageName)
    {
        $this->imageName = $imageName;
    }

    public function getImageName()
    {
        return $this->imageName;
    }

    public function __construct()
    {
        $this->image = new EmbeddedFile();
    }

    /**
     * @return \DateTime
     */
    public function getDateExp()
    {
        return $this->dateExp;
    }

    /**
     * @param \DateTime $dateExp
     */
    public function setDateExp($dateExp)
    {
        $this->dateExp = $dateExp;
    }


}

