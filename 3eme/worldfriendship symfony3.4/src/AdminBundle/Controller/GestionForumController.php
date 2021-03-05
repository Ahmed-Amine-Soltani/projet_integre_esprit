<?php

namespace AdminBundle\Controller;


use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Request;
use UserBundle\Entity\categorie;
use UserBundle\Form\categorieType;

class GestionForumController extends Controller
{

    public function ajoutercategorieAction(Request $request)
    {
        $categorie= new categorie();
        $cat = $this->getDoctrine()
            ->getRepository('UserBundle:categorie')
            ->findAll();
        $form= $this->createForm(categorieType::class, $categorie);
        $form->handleRequest($request);
        if ($form->isSubmitted()){
            $em= $this->getDoctrine()->getManager();
            $em->persist($categorie);
            $em->flush();
            return $this->redirectToRoute("admin_forumpage");
        }
        return $this->render('@Admin/gestion_forum/forum.html.twig',
            array("form"=>$form->createView(),
                'cat'=> $cat));
    }

    public function supprimercatAction($id)
    {

            $em = $this->getDoctrine()->getManager();
            $categorie = $em->getRepository("UserBundle:categorie")->find($id);
            $em->remove($categorie);
            $em->flush();
            return $this->redirectToRoute('admin_forumpage');


    }
}
