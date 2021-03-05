<?php
/**
 * Created by PhpStorm.
 * User: selim
 * Date: 06/04/2018
 * Time: 17:31
 */

namespace UserBundle\Entity;


use Doctrine\ORM\EntityRepository;
use UserBundle\Entity\Evenement;

class UserRepository extends EntityRepository
{
    public function getUsers(){
        $query=$this->getEntityManager()->createQuery("Select u from UserBundle:User u ");

        return $query->getResult();
    }

    public function getMailUser($id){
        $query=$this->getEntityManager()->createQuery("Select u.email from UserBundle:User u where u.id=:id ")
            ->setParameter('id',$id);
        ;

        return $query->getResult();
    }
}