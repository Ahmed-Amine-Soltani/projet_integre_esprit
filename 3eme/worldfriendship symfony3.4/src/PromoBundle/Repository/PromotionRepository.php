<?php

namespace PromoBundle\Repository;

/**
 * PromotionRepository
 *
 * This class was generated by the Doctrine ORM. Add your own custom
 * repository methods below.
 */
class PromotionRepository extends \Doctrine\ORM\EntityRepository
{


    public function computeDQL($id){
        $qb=$this->getEntityManager()
            ->createQuery("DELETE FROM PromoBundle:Promotion p  WHERE p.dateFin < :date")
            ->setParameter('date',$id);
        return $qb->getResult();
    }
    public function computeDQLL(){
        $qb=$this->getEntityManager()
            ->createQuery("UPDATE ProduitBundle:Produit p  SET p.promotion=0 where p.id  NOT IN (SELECT (pp.Produit) FROM PromoBundle:Promotion pp )");
        return $qb->getResult();
    }
    public function  mail(){

        $qb=$this->getEntityManager()
            ->createQuery("Select(u.email) FROM UserBundle:User u ");
        return $qb->getResult();
    }



    public function countDQL()
    {
        $qb=$this->getEntityManager()
            ->createQuery("SELECT COUNT (e) as nb 
                            FROM UserBundle:User e ");
        return $qb->getResult();
    }

    public function countPDQL()
    {
        $qb=$this->getEntityManager()
            ->createQuery("SELECT COUNT (e) as nb 
                            FROM ProduitBundle:Produit e ");
        return $qb->getResult();
    }

    public function counntDQL($i)
    {
        $qb=$this->getEntityManager()
            ->createQuery("SELECT COUNT (e) as nb 
                            FROM PromoBundle:Promotion e  where (e.Produit)=:nom")
            ->setParameter('nom',$i);

        return $qb->getResult();

    }


    public function coounntDQL()
    {
        $qb=$this->getEntityManager()
            ->createQuery("SELECT  (e.id)
                            FROM ProduitBundle:Produit e  ");

        return $qb->getResult();

    }

    public function yearDQL()
    {
        $qb=$this->getEntityManager()
            ->createQuery(" Select  (u.libelle) from ProduitBundle:Produit u ")
        ;

        return $qb->getResult();

    }
    public function cxDQL($id)
    {
        $qb=$this->getEntityManager()
            ->createQuery("SELECT COUNT (e) as nb 
                            FROM PromoBundle:Promotion e where e.dateFin <:id")
            ->setParameter('id',$id);
        return $qb->getResult();
    }
}
