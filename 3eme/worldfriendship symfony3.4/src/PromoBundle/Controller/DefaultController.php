<?php

namespace PromoBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('PromoBundle:Default:index.html.twig');
    }
}
