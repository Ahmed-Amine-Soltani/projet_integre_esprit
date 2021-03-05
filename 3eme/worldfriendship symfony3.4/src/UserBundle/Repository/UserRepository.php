<?php
/**
 * Created by PhpStorm.
 * User: solta
 * Date: 17/02/2019
 * Time: 13:13
 */

namespace UserBundle\Repository;

use Doctrine\ORM\EntityRepository;

class UserRepository extends EntityRepository
{
    public function getUsers(){
        $query=$this->getEntityManager()->createQuery("Select u from UserBundle:User u ");

        return $query->getResult();
    }

}