<?php

namespace GroupBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use UserBundle\Entity\comment;
use UserBundle\Entity\forum;
use UserBundle\Entity\User;
use Symfony\Component\Serializer\Encoder\JsonEncoder;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

class GroupeMobileController extends Controller
{
    public function listAction()
    {

        $forum = $this->getDoctrine()->getManager()
            ->getRepository('UserBundle:forum')
            ->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($forum);
        return new JsonResponse($formatted);
    }

    public function newAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $idUtilisateur = $request->get("user");
        $Utilisateur = $em->getRepository("UserBundle:User")->find($idUtilisateur);
        $forum = new forum();
        $forum->setUser($Utilisateur);
        $forum->setTitle($request->get('title'));
        $forum->setDescription($request->get('description'));
        $forum->setDate(new \DateTime());
        $em->persist($forum);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($forum);
        return new JsonResponse($formatted);
    }

    public function myQuesAction($id)
    {
        $exp = $this->getDoctrine()->getManager()->getRepository
        (forum::class)->findQuesByUserParameter($id);
        $serializer = new Serializer ([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($exp);
        return new JsonResponse($formatted);
    }

    public function updateQuesAction(Request $request, $id)
    {
        $ques = $this->getDoctrine()->getRepository(forum::class)
            ->find($id);
        $em = $this->getDoctrine()->getManager();
        $ques->setTitle($request->get('title'));
        $ques->setDescription($request->get('description'));
        $ques->setDate(new \DateTime());

        $em->persist($ques);
        $em->flush();

        $serializer = new Serializer ([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($ques);
        return new JsonResponse($formatted);

    }
    Public function SuppQuesAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $ques = $em->getRepository(forum::class)->find($id);
        $em->remove($ques);
        $em->flush();
        $serializer = new Serializer ([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($ques);
        return new JsonResponse($formatted);
    }
    /////////////////////////////////

    public function affCommentAction($id)
    {

        $comm = $this->getDoctrine()
            ->getRepository('UserBundle:comment')
            ->findcommParameter($id);

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($comm);
        return new JsonResponse($formatted);


    }

    public function ajCommentAction(Request $request)
    {


        $em = $this->getDoctrine()->getManager();
        $comment = new comment();
        $question= $em->getRepository('UserBundle:forum')->find($request->query->get("id"));
        $comment->setContenu($request->get('contenu'));
        $comment->setDate(new \DateTime());

        $comment->setForum($question);

        $em->persist($comment);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize([$comment]);
        return new JsonResponse($formatted);

    }







////////////////////////////////////////

}
