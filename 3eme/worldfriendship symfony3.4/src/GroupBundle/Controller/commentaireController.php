<?php

namespace GroupBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Request;
use UserBundle\Entity\comment;
use UserBundle\Entity\forum;
use UserBundle\Form\commentType;

class commentaireController extends Controller
{
    public function ajoutercomAction(Request $request, $id, forum $fr)
    {
        $this->get('views_counter.views_counter')->count($fr);
        $user = $this->getUser();
        $comment = new comment();
        $form = $this->createForm(commentType::class, $comment);
        $form->handleRequest($request);
        $forum = $this->getDoctrine()->getRepository('UserBundle:forum')
            ->findforumParameter($id);
        $comm = $this->getDoctrine()
            ->getRepository('UserBundle:comment')
            ->findcommParameter($id);

        if ($user !== null) {
            $comment->setDate(new \DateTime());

            if ($form->isValid()) {
                $comment->setUser($user);

                $em = $this->getDoctrine()->getManager();
                $comment->setForum($em->getRepository("UserBundle:forum")->find($id));


                $em->persist($comment);
                $em->flush();
                return $this->redirectToRoute('forums_commentpage',array('id'=>$comment->getForum()));
            }
            return $this->render('@Group/comment.html.twig',
                array("form" => $form->createView(),
                    'forum' => $forum,
                    'comment' => $comm,
                    'user'=> $user));
        } else {
            return $this->redirectToRoute('fos_user_security_login');
        }
    }

    public function modifiercommAction(Request $request, $id, comment $com)
    {
        $user = $this->get('security.token_storage')->getToken()->getUser();
        if ($com->getUser()== $user) {
            $comment = $this->getDoctrine()->getRepository(Reponse::class)
                ->find($id);
            $form = $this->createForm(commentType::class, $comment);
            $form->handleRequest($request);
            if ($user !== null) {
                $comment->setDate(new \DateTime());
                if ($form->isSubmitted()) {
                    $comment->setUser($user);
                    $em = $this->getDoctrine()->getManager();
                    $em->flush();
                    return $this->redirectToRoute('forums_commentpage', array('id' => $comment->getForum()));
                }

                return $this->render('@Group/testmodcomm.html.twig',
                    array("form" => $form->createView()));
            } else {
                return $this->redirectToRoute('fos_user_security_login');
            }
        }
        return $this->redirectToRoute('forums_commentpage', array('id' => $com->getForum()));


    }
    public function supprimercommAction($id, comment $com)
    {
        $user = $this->get('security.token_storage')->getToken()->getUser();
        if ($com->getUser()== $user) {
            $em = $this->getDoctrine()->getManager();
            $comment = $em->getRepository("UserBundle:comment")->find($id);
            $em->remove($comment);
            $em->flush();
            return $this->redirectToRoute('forums_commentpage',array('id'=>$comment->getForum()));
        }
        return $this->redirectToRoute('forums_commentpage',array('id'=>$com->getForum()));
    }
}
