<?php

namespace GroupBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use UserBundle\Entity\comment;
use UserBundle\Entity\forum;
use UserBundle\Entity\User;
use UserBundle\Form\commentType;
use UserBundle\Form\forumType;


class GroupController extends Controller
{



    public function listAction($id)
    {
        $user = $this->getUser();
        $forum = $this->getDoctrine()
            ->getRepository('UserBundle:forum')
            ->findCategorieParameter($id);
        $cat = $this->getDoctrine()
            ->getRepository('UserBundle:categorie')
            ->findNomParameter($id);
        return $this->render('@Group/list.html.twig', array('forum' => $forum , 'categorie'=> $cat , 'user' => $user));
    }

    public function ajouterAction(Request $request)
    {

        $user = $this->getUser();
        $forum = new forum();
        $cat = $this->getDoctrine()
            ->getRepository('UserBundle:categorie')
            ->findAll();
        $lim = $this->getDoctrine()
            ->getRepository('UserBundle:forum')
            ->findBy(array(),array('id'=>'DESC'),8);
        $form = $this->createForm(forumType::class, $forum);
        $form->handleRequest($request);
        if ($user !== null) {
            $forum->setDate(new \DateTime());

            if ($form->isSubmitted()) {
                $forum->setUser($user);
                $em = $this->getDoctrine()->getManager();
                $em->persist($forum);
                $em->flush();
                return $this->redirectToRoute('group_homepage');
            }
            return $this->render('@Group/group.html.twig',
                array("form" => $form->createView(),
                    'categorie' => $cat,
                    'lim'=> $lim));
        } else {
            return $this->redirectToRoute('fos_user_security_login');
        }
    }


    public function modifiertestAction(Request $request, $id, forum $fo)
    {



        $user = $this->get('security.token_storage')->getToken()->getUser();
        if ($fo->getUser()== $user) {
            $forum = $this->getDoctrine()->getRepository(forum::class)
                ->find($id);

            $form = $this->createForm(forumType::class, $forum);
            $form->handleRequest($request);


            if ($user !== null) {
                //$forum->setDate(new \DateTime());
                if ($form->isSubmitted()) {
                    $forum->setUser($user);
                    $em = $this->getDoctrine()->getManager();
                    $em->flush();
                    return $this->redirectToRoute('forums_listpage', array('id' => $forum->getCategorie()->getId()));
                }

                return $this->render('@Group/testmod.html.twig',
                    array("form" => $form->createView()));
            } else {
                return $this->redirectToRoute('fos_user_security_login');
            }
        }
        return $this->redirectToRoute('forums_listpage', array('id' => $fo->getCategorie()->getId()));


    }

    public function supprimerArAction($id, forum $fo)
    {
        $user = $this->get('security.token_storage')->getToken()->getUser();
        if ($fo->getUser()== $user) {
            $em = $this->getDoctrine()->getManager();
            $forum = $em->getRepository("UserBundle:forum")->find($id);
            $em->remove($forum);
            $em->flush();
            return $this->redirectToRoute('forums_listpage',array('id'=>$forum->getCategorie()->getId()));
        }
        return $this->redirectToRoute('forums_listpage',array('id'=>$fo->getCategorie()->getId()));
    }

}
