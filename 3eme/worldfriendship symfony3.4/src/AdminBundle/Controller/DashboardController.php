<?php

namespace AdminBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DashboardController extends Controller
{
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $produits= $em->getRepository('ProduitBundle:Produit')->findAll();

        return $this->render('AdminBundle:Dashboard:index.html.twig',array('produits'=>$produits));
    }

}
